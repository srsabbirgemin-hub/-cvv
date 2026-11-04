package com.example.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import com.example.AetherApp
import com.example.MainActivity
import com.example.R
import com.example.agent.ExecutionResult
import com.example.data.model.ChatMessage
import com.example.ui.overlay.FloatingBubbleOverlay
import com.example.ui.overlay.FloatingChatWindow
import com.example.ui.overlay.FloatingVoiceModeOverlay
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NeonPink
import com.example.voice.OrbVoiceState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.hypot

enum class OverlayMode {
    BUBBLE,
    EXPANDED_CHAT,
    VOICE_MODE,
    PURE_BACKGROUND
}

class FloatingAgentService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private lateinit var windowManager: WindowManager

    private val app by lazy { applicationContext as AetherApp }
    private val overlayLifecycleOwner = OverlayLifecycleOwner()

    // Overlay State
    private val currentMode = MutableStateFlow(OverlayMode.BUBBLE)
    private val isThinking = MutableStateFlow(false)
    private val voiceStatusText = MutableStateFlow("")
    private val chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())

    // Views
    private var bubbleComposeView: ComposeView? = null
    private var bubbleLayoutParams: WindowManager.LayoutParams? = null

    private var dismissTargetView: ComposeView? = null
    private var dismissLayoutParams: WindowManager.LayoutParams? = null

    private var chatComposeView: ComposeView? = null
    private var chatLayoutParams: WindowManager.LayoutParams? = null

    private var voiceComposeView: ComposeView? = null
    private var voiceLayoutParams: WindowManager.LayoutParams? = null

    private var screenWidth = 1080
    private var screenHeight = 2400

    private var activeSessionId = 0L

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        isRunning = true
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        updateScreenDimensions()

        setupVoiceManager()
        setupActiveSession()
        showNotification()

        createDismissTargetView()
        showBubble()
    }

    private fun updateScreenDimensions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            screenWidth = metrics.bounds.width()
            screenHeight = metrics.bounds.height()
        } else {
            val size = Point()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getSize(size)
            screenWidth = size.x
            screenHeight = size.y
        }
    }

    private fun setupActiveSession() {
        serviceScope.launch {
            activeSessionId = app.preferences.getActiveSessionId()
            if (activeSessionId == 0L) {
                activeSessionId = app.chatRepository.createNewSession("Floating Assistant")
                app.preferences.setActiveSessionId(activeSessionId)
            }
            app.chatRepository.getMessagesForSession(activeSessionId).collect { msgs ->
                chatMessages.value = msgs
            }
        }
    }

    private fun setupVoiceManager() {
        app.voiceManager.onSpeechRecognized = { recognizedText ->
            voiceStatusText.value = "Recognized: \"$recognizedText\""
            handleUserInput(recognizedText, isVoice = true)
        }

        app.voiceManager.onSpeechError = { errorMsg ->
            voiceStatusText.value = errorMsg
        }

        app.voiceManager.onContinuousListenRequest = {
            if ((currentMode.value == OverlayMode.VOICE_MODE || currentMode.value == OverlayMode.PURE_BACKGROUND) && isRunning) {
                app.voiceManager.startListening()
            }
        }
    }

    private fun showNotification(isPureBackgroundMode: Boolean = false) {
        val stopIntent = Intent(this, FloatingAgentService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPending = PendingIntent.getService(
            this, 101, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val actionIntent = Intent(this, FloatingAgentService::class.java).apply {
            action = if (isPureBackgroundMode) ACTION_BACKGROUND_LISTEN else ACTION_VOICE_MODE
        }
        val actionPending = PendingIntent.getService(
            this, 102, actionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val openAppIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val openAppPending = PendingIntent.getActivity(
            this, 100, openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = if (isPureBackgroundMode) "🎙️ শুদ্ধ ব্যাকগ্রাউন্ড এআই সক্রিয়" else "SRS AI Active"
        val subtitle = if (isPureBackgroundMode) "ব্যাকগ্রাউন্ডে কথা বলা ও কাজ করতে প্রস্তুত" else "Floating AI assistant ready over all apps"
        val actionLabel = if (isPureBackgroundMode) "কথা বলুন (Speak)" else "Voice Mode"

        val notification: Notification = NotificationCompat.Builder(this, AetherApp.CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(subtitle)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(openAppPending)
            .addAction(R.drawable.ic_launcher_foreground, actionLabel, actionPending)
            .addAction(R.drawable.ic_launcher_foreground, "Stop", stopPending)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val fgsType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE or ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            } else {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE
            }
            startForeground(NOTIFICATION_ID, notification, fgsType)
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_STOP -> stopSelf()
            ACTION_VOICE_MODE -> switchToVoiceMode()
            ACTION_EXPAND_CHAT -> switchToChatMode()
            ACTION_PURE_BACKGROUND -> switchToPureBackgroundMode()
            ACTION_BACKGROUND_LISTEN -> triggerBackgroundListen()
        }
        return START_STICKY
    }

    private fun switchToPureBackgroundMode() {
        currentMode.value = OverlayMode.PURE_BACKGROUND
        isPureBackground = true
        removeBubble()
        removeChatWindow()
        removeVoiceMode()
        dismissTargetView?.let {
            try {
                windowManager.removeView(it)
            } catch (_: Exception) {}
        }
        dismissTargetView = null
        app.preferences.setVoiceReplyEnabled(true)
        showNotification(isPureBackgroundMode = true)
        app.voiceManager.speak("শুদ্ধ ব্যাকগ্রাউন্ড এআই সক্রিয় হয়েছে। আমি ব্যাকগ্রাউন্ডে কথা বলব এবং কাজ করব।") {
            app.voiceManager.startListening()
        }
    }

    private fun triggerBackgroundListen() {
        app.voiceManager.speak("বলুন, আমি শুনছি।") {
            app.voiceManager.startListening()
        }
    }

    // ------------------------------------------------------------------------
    // Bubble Overlay
    // ------------------------------------------------------------------------
    @SuppressLint("ClickableViewAccessibility")
    private fun showBubble() {
        if (bubbleComposeView != null) return

        var currentBubblePixelSize = (app.preferences.getBubbleSize() * resources.displayMetrics.density).toInt()
        val savedX = app.preferences.getBubbleLastX()
        val savedY = app.preferences.getBubbleLastY()

        val params = WindowManager.LayoutParams(
            currentBubblePixelSize,
            currentBubblePixelSize,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            if (savedX != -1 && savedY != -1) {
                x = savedX.coerceIn(0, (screenWidth - currentBubblePixelSize).coerceAtLeast(0))
                y = savedY.coerceIn(40, (screenHeight - currentBubblePixelSize - 80).coerceAtLeast(40))
            } else {
                x = screenWidth - currentBubblePixelSize - 20
                y = screenHeight / 3
            }
        }
        bubbleLayoutParams = params

        val composeView = ComposeView(this).apply {
            overlayLifecycleOwner.attach(this)
            setContent {
                MyApplicationTheme {
                    val voiceState by app.voiceManager.voiceState.collectAsState()
                    val audioRms by app.voiceManager.audioRms.collectAsState()
                    val opacity by app.preferences.bubbleOpacityFlow.collectAsState()
                    val bubbleSizeDp by app.preferences.bubbleSizeFlow.collectAsState()

                    FloatingBubbleOverlay(
                        state = voiceState,
                        audioRms = audioRms,
                        opacity = opacity,
                        sizeDp = bubbleSizeDp,
                        onTap = { switchToChatMode() }
                    )
                }
            }
        }

        // Live Dynamic Resize Observer
        serviceScope.launch {
            app.preferences.bubbleSizeFlow.collect { sizeDp ->
                currentBubblePixelSize = (sizeDp * resources.displayMetrics.density).toInt()
                bubbleLayoutParams?.let { lp ->
                    lp.width = currentBubblePixelSize
                    lp.height = currentBubblePixelSize
                    bubbleComposeView?.let { cv ->
                        try {
                            windowManager.updateViewLayout(cv, lp)
                        } catch (_: Exception) {}
                    }
                }
            }
        }

        // Handle Touch & Drag (নড়াচড়া ও নিজের মতো প্লেসমেন্ট)
        var initialX = 0
        var initialY = 0
        var initialTouchX = 0f
        var initialTouchY = 0f
        var isDragging = false

        composeView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    isDragging = false
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val dx = (event.rawX - initialTouchX).toInt()
                    val dy = (event.rawY - initialTouchY).toInt()

                    if (hypot(dx.toDouble(), dy.toDouble()) > 15) {
                        isDragging = true
                        showDismissTarget(true)
                    }

                    if (isDragging) {
                        params.x = initialX + dx
                        params.y = initialY + dy
                        windowManager.updateViewLayout(composeView, params)

                        // Check proximity to bottom dismiss target
                        val dismissCenterY = screenHeight - 120
                        val dismissCenterX = screenWidth / 2
                        val currentBubbleCenterY = params.y + currentBubblePixelSize / 2
                        val currentBubbleCenterX = params.x + currentBubblePixelSize / 2

                        val dist = hypot(
                            (currentBubbleCenterX - dismissCenterX).toDouble(),
                            (currentBubbleCenterY - dismissCenterY).toDouble()
                        )
                        highlightDismissTarget(dist < 180)
                    }
                    true
                }
                MotionEvent.ACTION_UP -> {
                    showDismissTarget(false)
                    if (isDragging) {
                        val dismissCenterY = screenHeight - 120
                        val dismissCenterX = screenWidth / 2
                        val currentBubbleCenterY = params.y + currentBubblePixelSize / 2
                        val currentBubbleCenterX = params.x + currentBubblePixelSize / 2
                        val dist = hypot(
                            (currentBubbleCenterX - dismissCenterX).toDouble(),
                            (currentBubbleCenterY - dismissCenterY).toDouble()
                        )

                        if (dist < 180) {
                            // Dropped on dismiss target -> close service!
                            stopSelf()
                            return@setOnTouchListener true
                        }

                        // Free movement anywhere on screen as per user's wish (নিজের মতো যেকোনো জায়গায় রাখা)
                        if (app.preferences.isFreeMovementEnabled()) {
                            params.x = params.x.coerceIn(8, screenWidth - currentBubblePixelSize - 8)
                            params.y = params.y.coerceIn(40, screenHeight - currentBubblePixelSize - 80)
                        } else {
                            val middle = screenWidth / 2
                            params.x = if (params.x + currentBubblePixelSize / 2 < middle) 16 else screenWidth - currentBubblePixelSize - 16
                            params.y = params.y.coerceIn(80, screenHeight - currentBubblePixelSize - 120)
                        }
                        app.preferences.setBubbleLastX(params.x)
                        app.preferences.setBubbleLastY(params.y)
                        windowManager.updateViewLayout(composeView, params)
                    } else {
                        // Clicked -> Expand chat
                        switchToChatMode()
                    }
                    true
                }
                else -> false
            }
        }

        bubbleComposeView = composeView
        windowManager.addView(composeView, params)
        currentMode.value = OverlayMode.BUBBLE
    }

    private fun removeBubble() {
        bubbleComposeView?.let {
            try {
                windowManager.removeView(it)
            } catch (_: Exception) {}
        }
        bubbleComposeView = null
    }

    // ------------------------------------------------------------------------
    // Bottom Dismiss Target ("X" at bottom center)
    // ------------------------------------------------------------------------
    private val isDismissHighlighted = mutableStateOf(false)

    private fun createDismissTargetView() {
        val targetSize = (72 * resources.displayMetrics.density).toInt()
        val params = WindowManager.LayoutParams(
            targetSize,
            targetSize,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
            y = 60
        }
        dismissLayoutParams = params

        val composeView = ComposeView(this).apply {
            overlayLifecycleOwner.attach(this)
            setContent {
                val highlighted by isDismissHighlighted
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(if (highlighted) NeonPink else Color(0x992B0818)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Dismiss Orb",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }

        composeView.visibility = View.GONE
        dismissTargetView = composeView
        windowManager.addView(composeView, params)
    }

    private fun showDismissTarget(show: Boolean) {
        dismissTargetView?.visibility = if (show) View.VISIBLE else View.GONE
        if (!show) {
            isDismissHighlighted.value = false
        }
    }

    private fun highlightDismissTarget(highlight: Boolean) {
        isDismissHighlighted.value = highlight
    }

    // ------------------------------------------------------------------------
    // Expanded Chat Window
    // ------------------------------------------------------------------------
    @SuppressLint("ClickableViewAccessibility")
    private fun switchToChatMode() {
        removeBubble()
        removeVoiceMode()
        if (chatComposeView != null) return

        val initialWidth = (screenWidth * 0.92f).toInt().coerceAtMost(560)
        val initialHeight = (screenHeight * 0.65f).toInt().coerceAtMost(800)

        val params = WindowManager.LayoutParams(
            initialWidth,
            initialHeight,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.CENTER
            x = 0
            y = 0
            softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        }
        chatLayoutParams = params

        val composeView = ComposeView(this).apply {
            overlayLifecycleOwner.attach(this)
            setContent {
                MyApplicationTheme {
                    val messages by chatMessages.collectAsState()
                    val thinking by isThinking.collectAsState()
                    val voiceState by app.voiceManager.voiceState.collectAsState()
                    val audioRms by app.voiceManager.audioRms.collectAsState()
                    val opacity by app.preferences.bubbleOpacityFlow.collectAsState()
                    val currentBubbleSize by app.preferences.bubbleSizeFlow.collectAsState()

                    FloatingChatWindow(
                        messages = messages,
                        isThinking = thinking,
                        voiceState = voiceState,
                        audioRms = audioRms,
                        initialOpacity = opacity,
                        currentBubbleSize = currentBubbleSize,
                        screenWidthPx = screenWidth,
                        screenHeightPx = screenHeight,
                        onSendMessage = { text -> handleUserInput(text, isVoice = false) },
                        onStartVoice = { switchToVoiceMode() },
                        onMinimize = {
                            removeChatWindow()
                            showBubble()
                        },
                        onClose = {
                            removeChatWindow()
                            showBubble()
                        },
                        onFocusChanged = { focused ->
                            if (focused) {
                                params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                            } else {
                                params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            }
                            windowManager.updateViewLayout(this, params)
                        },
                        onResizeDelta = { dx, dy ->
                            params.width = (params.width + dx).coerceIn(280, screenWidth)
                            params.height = (params.height + dy).coerceIn(340, screenHeight - 100)
                            windowManager.updateViewLayout(this, params)
                        },
                        onDragDelta = { dx, dy ->
                            params.x = (params.x + dx).coerceIn(-screenWidth / 2, screenWidth / 2)
                            params.y = (params.y + dy).coerceIn(-screenHeight / 2, screenHeight / 2)
                            windowManager.updateViewLayout(this, params)
                        },
                        onSetPresetSize = { targetWidth, targetHeight ->
                            params.width = targetWidth.coerceIn(280, screenWidth)
                            params.height = targetHeight.coerceIn(340, screenHeight - 100)
                            windowManager.updateViewLayout(this, params)
                        },
                        onBubbleSizeChange = { newSize ->
                            app.preferences.setBubbleSize(newSize)
                        }
                    )
                }
            }
        }

        chatComposeView = composeView
        windowManager.addView(composeView, params)
        currentMode.value = OverlayMode.EXPANDED_CHAT
    }

    private fun removeChatWindow() {
        chatComposeView?.let {
            try {
                windowManager.removeView(it)
            } catch (_: Exception) {}
        }
        chatComposeView = null
    }

    // ------------------------------------------------------------------------
    // Floating Voice Mode
    // ------------------------------------------------------------------------
    private fun switchToVoiceMode() {
        removeBubble()
        removeChatWindow()
        if (voiceComposeView != null) return

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            y = 120
        }
        voiceLayoutParams = params

        val composeView = ComposeView(this).apply {
            overlayLifecycleOwner.attach(this)
            setContent {
                MyApplicationTheme {
                    val voiceState by app.voiceManager.voiceState.collectAsState()
                    val audioRms by app.voiceManager.audioRms.collectAsState()
                    val statusText by voiceStatusText.collectAsState()

                    FloatingVoiceModeOverlay(
                        state = voiceState,
                        audioRms = audioRms,
                        statusText = statusText,
                        onToggleMute = {
                            if (voiceState == OrbVoiceState.LISTENING) {
                                app.voiceManager.stopListening()
                            } else {
                                app.voiceManager.startListening()
                            }
                        },
                        onMinimize = {
                            app.voiceManager.stopListening()
                            removeVoiceMode()
                            showBubble()
                        },
                        onClose = {
                            app.voiceManager.stopListening()
                            removeVoiceMode()
                            showBubble()
                        },
                        onDragDelta = { dx, dy ->
                            params.x = (params.x + dx).coerceIn(-screenWidth / 2, screenWidth / 2)
                            params.y = (params.y + dy).coerceIn(0, screenHeight - 200)
                            windowManager.updateViewLayout(this, params)
                        }
                    )
                }
            }
        }

        voiceComposeView = composeView
        windowManager.addView(composeView, params)
        currentMode.value = OverlayMode.VOICE_MODE

        // Start listening immediately in voice mode
        app.voiceManager.startListening()
    }

    private fun removeVoiceMode() {
        voiceComposeView?.let {
            try {
                windowManager.removeView(it)
            } catch (_: Exception) {}
        }
        voiceComposeView = null
    }

    // ------------------------------------------------------------------------
    // AI & Action Processing
    // ------------------------------------------------------------------------
    private fun handleUserInput(inputText: String, isVoice: Boolean) {
        if (inputText.isBlank()) return

        serviceScope.launch {
            // 1. Save user message to database
            val userMsg = ChatMessage(
                sessionId = activeSessionId,
                role = "user",
                content = inputText,
                timestamp = System.currentTimeMillis(),
                isVoice = isVoice
            )
            app.chatRepository.saveMessage(userMsg)

            isThinking.value = true
            app.voiceManager.setThinking()

            // 2. Check if screen context is needed
            var screenContext: String? = null
            if (inputText.lowercase().contains("screen") || inputText.lowercase().contains("what's on")) {
                val screenResult = app.actionExecutor.execute("read_screen", null, null)
                if (screenResult is ExecutionResult.ScreenText) {
                    screenContext = screenResult.text
                }
            }

            // 3. Get recent history
            val recent = app.chatRepository.getRecentMessages(activeSessionId, 6).reversed()
            val historyPairs = recent.map { it.role to it.content }

            // 4. Query AI Service
            val aiResult = app.aiService.sendMessage(inputText, historyPairs, screenContext)

            isThinking.value = false

            aiResult.onSuccess { response ->
                var executedStatus: String? = null
                var executedPayload: String? = null

                // 5. If action is present, execute on device!
                if (!response.actionType.isNullOrBlank()) {
                    val execResult = app.actionExecutor.execute(
                        response.actionType,
                        response.actionTarget,
                        response.actionExtra
                    )
                    when (execResult) {
                        is ExecutionResult.Success -> {
                            executedStatus = execResult.message
                            executedPayload = listOfNotNull(response.actionTarget, response.actionExtra).joinToString(" • ")
                        }
                        is ExecutionResult.Failure -> {
                            executedStatus = "Failed: ${execResult.reason}"
                            executedPayload = response.actionTarget
                        }
                        is ExecutionResult.ScreenText -> {
                            executedStatus = "Screen Read Success"
                            executedPayload = execResult.text.take(80) + "..."
                        }
                    }
                }

                // 6. Save assistant message
                val assistantMsg = ChatMessage(
                    sessionId = activeSessionId,
                    role = "assistant",
                    content = response.replyText,
                    timestamp = System.currentTimeMillis(),
                    actionType = response.actionType,
                    actionPayload = executedPayload,
                    actionStatus = executedStatus,
                    isVoice = isVoice
                )
                app.chatRepository.saveMessage(assistantMsg)

                // 7. Voice Reply (কথা বলবে এবং কাজ সম্পন্ন করবে)
                if (isVoice || app.preferences.isVoiceReplyEnabled() || currentMode.value == OverlayMode.PURE_BACKGROUND) {
                    app.voiceManager.speak(response.replyText) {
                        // After speaking completes, stay ready to listen
                        if (currentMode.value == OverlayMode.VOICE_MODE || currentMode.value == OverlayMode.PURE_BACKGROUND) {
                            app.voiceManager.startListening()
                        }
                    }
                } else {
                    app.voiceManager.setIdle()
                }

            }.onFailure { error ->
                val errorMsg = ChatMessage(
                    sessionId = activeSessionId,
                    role = "assistant",
                    content = "Sorry, I encountered an issue: ${error.localizedMessage ?: "Unknown error"}. Check your API keys in Settings.",
                    timestamp = System.currentTimeMillis(),
                    isVoice = isVoice
                )
                app.chatRepository.saveMessage(errorMsg)

                if (isVoice || currentMode.value == OverlayMode.PURE_BACKGROUND) {
                    app.voiceManager.speak("Sorry, I encountered an error connecting to the AI service.")
                } else {
                    app.voiceManager.setIdle()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        isPureBackground = false
        serviceScope.cancel()
        removeBubble()
        removeChatWindow()
        removeVoiceMode()
        dismissTargetView?.let {
            try {
                windowManager.removeView(it)
            } catch (_: Exception) {}
        }
        overlayLifecycleOwner.destroy()
        app.voiceManager.stopListening()
        app.voiceManager.stopSpeaking()
    }

    companion object {
        const val NOTIFICATION_ID = 9001
        const val ACTION_START = "com.example.action.START_FLOATING"
        const val ACTION_STOP = "com.example.action.STOP_FLOATING"
        const val ACTION_VOICE_MODE = "com.example.action.VOICE_MODE"
        const val ACTION_EXPAND_CHAT = "com.example.action.EXPAND_CHAT"
        const val ACTION_PURE_BACKGROUND = "com.example.action.PURE_BACKGROUND"
        const val ACTION_BACKGROUND_LISTEN = "com.example.action.BACKGROUND_LISTEN"

        var isRunning = false
            private set

        var isPureBackground = false
            private set
    }
}
