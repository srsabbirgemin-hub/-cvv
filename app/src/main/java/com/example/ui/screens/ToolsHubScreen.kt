package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VolumeDown
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agent.ExecutionResult
import com.example.data.model.FeatureCategory
import com.example.data.model.SrsFeature
import com.example.data.model.SrsFeatureCatalog
import com.example.ui.MainViewModel
import com.example.ui.theme.CardBorderGradient
import com.example.ui.theme.DarkNavyBackground
import com.example.ui.theme.ElectricCyan
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.NeonPink
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.SurfaceCard
import com.example.ui.theme.SurfaceNavy
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ToolsHubScreen(
    viewModel: MainViewModel,
    onExecutePrompt: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    var activeFeatureForDialog by remember { mutableStateOf<SrsFeature?>(null) }
    var toolResultText by remember { mutableStateOf<String?>(null) }

    val categoryTabs = listOf("🌟 All 100+ Features") + FeatureCategory.entries.map { "${it.icon} ${it.title}" }

    val filteredFeatures = remember(searchQuery, selectedCategoryIndex) {
        if (searchQuery.isNotBlank()) {
            SrsFeatureCatalog.searchFeatures(searchQuery)
        } else if (selectedCategoryIndex == 0) {
            SrsFeatureCatalog.allFeatures
        } else {
            val selectedCategory = FeatureCategory.entries[selectedCategoryIndex - 1]
            SrsFeatureCatalog.getCategoryFeatures(selectedCategory)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkNavyBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "SRS AI",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        color = ElectricCyan
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(NeonGreen.copy(alpha = 0.2f))
                            .border(1.dp, NeonGreen, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "${SrsFeatureCatalog.allFeatures.size}+ FEATURES",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = NeonGreen
                        )
                    }
                }
                Text(
                    text = "স্মার্ট এজেন্ট, ১০০+ এআই টুলস ও স্বয়ংক্রিয় ডিভাইস কন্ট্রোল",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("features_search_input"),
            placeholder = { Text("Search 100+ tools (e.g. bug, python, কবিতা, translation, reels)...", fontSize = 12.sp, color = TextSecondary) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = ElectricCyan) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextSecondary)
                    }
                }
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                focusedBorderColor = ElectricCyan,
                unfocusedBorderColor = GlassBorder,
                focusedContainerColor = SurfaceNavy,
                unfocusedContainerColor = SurfaceNavy
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Category Horizontal Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedCategoryIndex,
            containerColor = SurfaceNavy,
            contentColor = ElectricCyan,
            edgePadding = 8.dp,
            indicator = { tabPositions ->
                if (selectedCategoryIndex < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedCategoryIndex]),
                        color = NeonPurple
                    )
                }
            },
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
            categoryTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedCategoryIndex == index,
                    onClick = {
                        selectedCategoryIndex = index
                        searchQuery = ""
                    },
                    text = {
                        Text(
                            text = title,
                            fontSize = 11.sp,
                            fontWeight = if (selectedCategoryIndex == index) FontWeight.Bold else FontWeight.Normal,
                            color = if (selectedCategoryIndex == index) Color.White else TextSecondary
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Result Banner if active
        toolResultText?.let { res ->
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, NeonGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = NeonGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = res, color = TextPrimary, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    Text(
                        text = "Dismiss",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { toolResultText = null }
                    )
                }
            }
        }

        // Feature List View
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Quick Autonomous Agent Header (shown if at All tab and not searching)
            if (searchQuery.isBlank() && selectedCategoryIndex == 0) {
                item {
                    AgentRoutinesSummaryCard(
                        onSelectRoutine = { routine ->
                            when (routine) {
                                "morning" -> {
                                    viewModel.runAgentRoutine("morning")
                                    toolResultText = "Executing Morning Briefing Routine..."
                                }
                                "night" -> {
                                    viewModel.runAgentRoutine("night")
                                    toolResultText = "Executing Bedtime Routine..."
                                }
                                "focus" -> {
                                    viewModel.runAgentRoutine("focus")
                                    toolResultText = "Starting 45m Deep Focus Mode..."
                                }
                                "pure_bg" -> {
                                    viewModel.launchPureBackgroundMode()
                                    toolResultText = "Pure Background Voice & Action Mode Active!"
                                }
                            }
                        }
                    )
                }

                // Official Developer Hub
                item {
                    DeveloperProfileCard(viewModel = viewModel)
                }
            }

            // Results count banner
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (searchQuery.isNotBlank()) "Search Results (${filteredFeatures.size})" else "Features (${filteredFeatures.size} Tools)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricCyan
                    )
                    Text(
                        text = "Tap to Launch / Customize",
                        fontSize = 10.sp,
                        color = TextSecondary
                    )
                }
            }

            // Items in Catalog
            items(filteredFeatures, key = { it.id }) { feature ->
                FeatureItemCard(
                    feature = feature,
                    onLaunch = {
                        if (feature.isDeviceAction && feature.actionCommand != null) {
                            when (feature.actionCommand) {
                                "pure_background" -> {
                                    viewModel.launchPureBackgroundMode()
                                    toolResultText = "Pure Background Voice & Action Agent Active!"
                                }
                                "flashlight" -> {
                                    val res = viewModel.actionExecutor.toggleFlashlight()
                                    if (res is ExecutionResult.Success) toolResultText = res.message
                                }
                                "battery" -> {
                                    val res = viewModel.actionExecutor.getBatteryInfo()
                                    if (res is ExecutionResult.Success) toolResultText = res.message
                                }
                                else -> {
                                    viewModel.actionExecutor.execute(feature.actionCommand, null, null)
                                    toolResultText = "Executed: ${feature.title}"
                                }
                            }
                        } else {
                            activeFeatureForDialog = feature
                        }
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    // Interactive Launcher Modal Dialog for selected Feature
    activeFeatureForDialog?.let { feature ->
        var editablePrompt by remember { mutableStateOf(feature.promptTemplate) }

        AlertDialog(
            onDismissRequest = { activeFeatureForDialog = null },
            containerColor = SurfaceNavy,
            shape = RoundedCornerShape(20.dp),
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = feature.iconEmoji, fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(text = feature.title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        Text(text = feature.titleBn, fontSize = 12.sp, color = ElectricCyan)
                    }
                }
            },
            text = {
                Column {
                    Text(
                        text = feature.description,
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Customize Prompt (প্রম্পট সম্পাদনা করুন):",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElectricCyan
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = editablePrompt,
                        onValueChange = { editablePrompt = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary,
                            focusedBorderColor = NeonPurple,
                            unfocusedBorderColor = GlassBorder,
                            focusedContainerColor = DarkNavyBackground,
                            unfocusedContainerColor = DarkNavyBackground
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val promptToRun = editablePrompt.ifBlank { feature.promptTemplate }
                        activeFeatureForDialog = null
                        onExecutePrompt(promptToRun)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Execute in Chat", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(editablePrompt))
                            Toast.makeText(context, "Copied prompt to clipboard!", Toast.LENGTH_SHORT).show()
                            activeFeatureForDialog = null
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ElectricCyan),
                        border = androidx.compose.foundation.BorderStroke(1.dp, ElectricCyan),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Copy", fontSize = 11.sp)
                    }
                    Button(
                        onClick = { activeFeatureForDialog = null },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0x3364748B)),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Close", fontSize = 11.sp, color = TextSecondary)
                    }
                }
            }
        )
    }
}

@Composable
fun FeatureItemCard(
    feature: SrsFeature,
    onLaunch: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLaunch() },
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderGradient)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(SurfaceNavy)
                    .border(1.dp, ElectricCyan.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = feature.iconEmoji, fontSize = 22.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = feature.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    if (feature.isDeviceAction) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(NeonGreen.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("DEVICE", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = NeonGreen)
                        }
                    }
                }
                Text(
                    text = feature.titleBn,
                    fontSize = 11.sp,
                    color = ElectricCyan,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = feature.description,
                    fontSize = 11.sp,
                    color = TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(NeonPurple.copy(alpha = 0.2f))
                    .border(1.dp, NeonPurple.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Run",
                    tint = ElectricCyan,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun AgentRoutinesSummaryCard(
    onSelectRoutine: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, NeonPurple)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "AUTONOMOUS MULTI-TASK AGENT",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = NeonPurple
                    )
                    Text(
                        text = "এক ট্যাপে স্বয়ংক্রিয় ধারাবাহিক অ্যাকশন রুটিনসমূহ",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onSelectRoutine("morning") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFBBF24).copy(alpha = 0.2f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFBBF24)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("☀️ সকালের রুটিন", fontSize = 11.sp, color = TextPrimary)
                }
                Button(
                    onClick = { onSelectRoutine("night") },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonPurple.copy(alpha = 0.2f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, NeonPurple),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("🌙 ঘুমের রুটিন", fontSize = 11.sp, color = TextPrimary)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onSelectRoutine("focus") },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonGreen.copy(alpha = 0.2f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, NeonGreen),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("🎯 ৪৫ মিনিট ফোকাস", fontSize = 11.sp, color = TextPrimary)
                }
                Button(
                    onClick = { onSelectRoutine("pure_bg") },
                    colors = ButtonDefaults.buttonColors(containerColor = ElectricCyan.copy(alpha = 0.2f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ElectricCyan),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("🎙️ ব্যাকগ্রাউন্ড এজেন্ট", fontSize = 11.sp, color = TextPrimary)
                }
            }
        }
    }
}

@Composable
fun DeveloperProfileCard(viewModel: MainViewModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, NeonGreen.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "SRS AI DEVELOPER & OFFICIAL CHANNELS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = NeonGreen
            )
            Text(
                text = "অফিসিয়াল ফেসবুক আইডি, পেজ, টেলিগ্রাম ও ইউটিউব চ্যানেল",
                fontSize = 11.sp,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.actionExecutor.openFacebookProfile() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2)),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("FB Profile", fontSize = 11.sp, color = Color.White)
                }
                Button(
                    onClick = { viewModel.actionExecutor.openFacebookPage() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D8BF0)),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("FB Page", fontSize = 11.sp, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.actionExecutor.openTelegramChannel() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0088CC)),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Telegram", fontSize = 11.sp, color = Color.White)
                }
                Button(
                    onClick = { viewModel.actionExecutor.openYouTubeChannel() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000)),
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("YouTube", fontSize = 11.sp, color = Color.White)
                }
            }
        }
    }
}
