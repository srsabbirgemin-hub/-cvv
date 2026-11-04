package com.example.data.model

data class SrsFeature(
    val id: String,
    val title: String,
    val titleBn: String,
    val category: FeatureCategory,
    val iconEmoji: String,
    val description: String,
    val promptTemplate: String,
    val isDeviceAction: Boolean = false,
    val actionCommand: String? = null,
    val tags: List<String> = emptyList()
)

enum class FeatureCategory(val title: String, val titleBn: String, val icon: String) {
    CODING("Coding & Dev", "কোডিং ও প্রোগ্রামিং", "💻"),
    WRITING("Writing & Content", "লেখা ও কনটেন্ট স্টুডিও", "✍️"),
    CAREER("Career & Business", "ক্যারিয়ার ও বিজনেস", "💼"),
    EDUCATION("Education & Math", "শিক্ষা, গণিত ও বিজ্ঞান", "📚"),
    LANGUAGE("Language & Translation", "ভাষা ও অনুবাদ", "🌐"),
    PRODUCTIVITY("Productivity & Life", "দৈনন্দিন লাইফ হ্যাকস", "⚡"),
    HEALTH("Health & Wellness", "স্বাস্থ্য ও ফিটনেস", "🧘"),
    SOCIAL("Social Media & Marketing", "সোশ্যাল মিডিয়া ও মার্কেটিং", "📱"),
    ENTERTAINMENT("Creative & Fun", "বিনোদন ও ক্রিয়েটিভ", "🎨"),
    DEVICE("Device & Agent Tools", "ডিভাইস ও সিস্টেম টুলস", "⚙️")
}

object SrsFeatureCatalog {

    val allFeatures: List<SrsFeature> = listOf(
        // ==========================================
        // 1. CODING & DEV (12 Features)
        // ==========================================
        SrsFeature(
            id = "code_bug_fixer",
            title = "Bug Detector & Fixer",
            titleBn = "কোড বাগ ও এরর ফিক্সার",
            category = FeatureCategory.CODING,
            iconEmoji = "🐛",
            description = "Analyzes your code snippet, pinpoints syntax/logical bugs, and provides clean fixed code with explanations.",
            promptTemplate = "Please analyze this code snippet, find any bugs, errors or edge cases, and provide the corrected code with detailed explanations in Bengali/English:\n\n```\n// Paste code here\n```",
            tags = listOf("code", "bug", "debug", "error", "fix", "python", "javascript", "kotlin")
        ),
        SrsFeature(
            id = "code_explainer",
            title = "Code Explainer Pro",
            titleBn = "সহজ বাংলায় কোড ব্যাখ্যাকারী",
            category = FeatureCategory.CODING,
            iconEmoji = "💡",
            description = "Explains complex algorithms, functions, and architectures in crystal-clear plain Bengali or English.",
            promptTemplate = "Explain the logic, time complexity, and step-by-step execution of this code in simple Bengali:\n\n```\n// Paste code here\n```",
            tags = listOf("explain", "algorithm", "logic", "learn", "study")
        ),
        SrsFeature(
            id = "sql_query_architect",
            title = "SQL Query Architect",
            titleBn = "এসকিউএল কোয়েরি বিল্ডার",
            category = FeatureCategory.CODING,
            iconEmoji = "🗄️",
            description = "Generates optimized SQL queries, complex JOINs, indexing strategies, and database schemas.",
            promptTemplate = "Write an optimized SQL query for the following requirement. Include table schema assumptions, JOIN explanation, and index recommendation:\nRequirement: ",
            tags = listOf("sql", "database", "query", "mysql", "postgres")
        ),
        SrsFeature(
            id = "regex_builder",
            title = "Regex Master",
            titleBn = "রেগুলার এক্সপ্রেশন বিল্ডার",
            category = FeatureCategory.CODING,
            iconEmoji = "🔍",
            description = "Builds and explains regular expressions for emails, phones, URLs, passwords, and custom data formats.",
            promptTemplate = "Build a robust Regular Expression (Regex) that matches: [your requirement]. Provide regex pattern, flag details, and test case examples.",
            tags = listOf("regex", "pattern", "matching", "parser")
        ),
        SrsFeature(
            id = "unit_test_generator",
            title = "Unit Test Generator",
            titleBn = "ইউনিট টেস্ট কোড জেনারেটর",
            category = FeatureCategory.CODING,
            iconEmoji = "🧪",
            description = "Creates comprehensive unit test suites covering edge cases, happy paths, and error scenarios (JUnit/Jest/PyTest).",
            promptTemplate = "Write complete unit tests with full edge-case coverage for this function (specify language/framework e.g., Kotlin JUnit, Jest, PyTest):\n\n```\n// Paste function here\n```",
            tags = listOf("test", "junit", "jest", "pytest", "mock")
        ),
        SrsFeature(
            id = "api_doc_writer",
            title = "API Doc & Swagger Generator",
            titleBn = "এপিআই ডকুমেন্টেশন রাইটার",
            category = FeatureCategory.CODING,
            iconEmoji = "📄",
            description = "Turns raw backend endpoints or controller code into clean Markdown API documentation or OpenAPI specifications.",
            promptTemplate = "Generate professional Markdown API documentation including request body schema, headers, status codes, and JSON response for this endpoint: ",
            tags = listOf("api", "swagger", "openapi", "rest", "backend")
        ),
        SrsFeature(
            id = "json_schema_validator",
            title = "JSON Formatter & Schema",
            titleBn = "জেসন ফরম্যাটার ও স্কিমা",
            category = FeatureCategory.CODING,
            iconEmoji = "📦",
            description = "Validates, formats, minifies, and derives JSON Schema / TypeScript interfaces from sample payloads.",
            promptTemplate = "Format and validate this JSON, fix syntax errors, and generate corresponding TypeScript/Kotlin data classes:\n\n",
            tags = listOf("json", "schema", "typescript", "kotlin")
        ),
        SrsFeature(
            id = "git_command_helper",
            title = "Git & Merge Conflict Helper",
            titleBn = "গিট কমান্ড ও মার্জ কনফ্লিক্ট",
            category = FeatureCategory.CODING,
            iconEmoji = "🌿",
            description = "Solves tricky Git rebases, merge conflicts, detached heads, and generates exact terminal commands.",
            promptTemplate = "What are the exact git commands to solve this situation: ",
            tags = listOf("git", "github", "merge", "rebase", "branch")
        ),
        SrsFeature(
            id = "code_refactor_clean",
            title = "Clean Code Refactorer",
            titleBn = "ক্লিন কোড ও রিফ্যাক্টরিং",
            category = FeatureCategory.CODING,
            iconEmoji = "✨",
            description = "Refactors messy code into SOLID, DRY, highly readable and maintainable enterprise code.",
            promptTemplate = "Refactor this code following Clean Architecture and SOLID design principles, improving readability and performance:\n\n```\n// Paste code\n```",
            tags = listOf("refactor", "clean", "architecture", "solid")
        ),
        SrsFeature(
            id = "html_tailwind_styler",
            title = "HTML & Tailwind Component Maker",
            titleBn = "এইচটিএমএল ও টেইলউইন্ড ডিজাইন",
            category = FeatureCategory.CODING,
            iconEmoji = "🎨",
            description = "Generates responsive, modern, dark-mode friendly Tailwind CSS components and landing hero sections.",
            promptTemplate = "Create a responsive, beautiful Tailwind CSS component for: [e.g., Pricing card, Hero banner, Navbar with mobile drawer]. Provide clean HTML/Tailwind.",
            tags = listOf("html", "tailwind", "css", "frontend", "ui")
        ),
        SrsFeature(
            id = "docker_devops_helper",
            title = "Docker & CI/CD Config",
            titleBn = "ডকার ও ডেভঅপস কনফিগারেশন",
            category = FeatureCategory.CODING,
            iconEmoji = "🐳",
            description = "Creates production-ready multi-stage Dockerfiles, docker-compose.yml files, and GitHub Actions CI/CD workflows.",
            promptTemplate = "Write a production-ready multi-stage Dockerfile and docker-compose.yml for an application with: [e.g., Node.js + PostgreSQL or Python FastAPI].",
            tags = listOf("docker", "devops", "container", "cicd", "github")
        ),
        SrsFeature(
            id = "python_data_analyst",
            title = "Python Data Science Helper",
            titleBn = "পাইথন ডেটা সায়েন্স ও পান্ডাস",
            category = FeatureCategory.CODING,
            iconEmoji = "📊",
            description = "Generates Pandas data wrangling, Matplotlib/Seaborn visualizations, and statistical modeling scripts.",
            promptTemplate = "Write Python Pandas code to clean, aggregate, and visualize the following dataset: ",
            tags = listOf("python", "pandas", "data", "analytics", "numpy")
        ),

        // ==========================================
        // 2. WRITING & CONTENT (12 Features)
        // ==========================================
        SrsFeature(
            id = "blog_article_pro",
            title = "SEO Blog Post Writer",
            titleBn = "এসইও ব্লগ ও আর্টিকেল রাইটার",
            category = FeatureCategory.WRITING,
            iconEmoji = "📝",
            description = "Generates long-form, SEO-optimized articles with engaging hooks, H2/H3 subheadings, and meta tags.",
            promptTemplate = "Write a comprehensive, engaging 800-word SEO-friendly blog article on the topic: [Enter topic]. Include introduction hook, H2/H3 subheadings, bullet points, and concluding takeaways.",
            tags = listOf("blog", "seo", "article", "writing", "content")
        ),
        SrsFeature(
            id = "formal_email_writer",
            title = "Executive Formal Email",
            titleBn = "অফিসিয়াল ও প্রফেশনাল ইমেইল",
            category = FeatureCategory.WRITING,
            iconEmoji = "✉️",
            description = "Drafts polite, concise, and persuasive business emails for bosses, clients, professors, and partners.",
            promptTemplate = "Write a formal, polite, and effective business email regarding: [e.g., Requesting project extension / Submitting quotation / Leave application]. Include compelling Subject line.",
            tags = listOf("email", "office", "formal", "corporate", "letter")
        ),
        SrsFeature(
            id = "chat_message_polisher",
            title = "Message Polisher & Tone Fixer",
            titleBn = "মেসেজ সুন্দর ও আকর্ষণীয় করার টুল",
            category = FeatureCategory.WRITING,
            iconEmoji = "💬",
            description = "Enhances WhatsApp, Telegram, or Slack draft messages to sound confident, respectful, and crystal clear.",
            promptTemplate = "Rewrite and polish this draft message in 3 styles: (1) Super polite & professional, (2) Friendly & warm, (3) Short & punchy:\n\nDraft: ",
            tags = listOf("chat", "whatsapp", "message", "polish", "tone")
        ),
        SrsFeature(
            id = "bengali_poetry_writer",
            title = "Bengali Poetry Studio",
            titleBn = "অনবদ্য বাংলা কবিতা ও ছন্দ",
            category = FeatureCategory.WRITING,
            iconEmoji = "🌸",
            description = "Composes emotional, rhyming, and expressive Bengali poems (কবিতা) on love, life, rain, and nostalgia.",
            promptTemplate = "নিচের বিষয়টি নিয়ে চমৎকার ছন্দ ও উপমায় একটি গভীর আবেগময়ী বাংলা কবিতা রচনা করো:\nবিষয়: ",
            tags = listOf("poem", "kobita", "bengali", "poetry", "rhyme")
        ),
        SrsFeature(
            id = "youtube_script_writer",
            title = "YouTube Video Script & Hook",
            titleBn = "ইউটিউব ভিডিও স্ক্রিপ্ট ও হুক",
            category = FeatureCategory.WRITING,
            iconEmoji = "🎬",
            description = "Structures high-retention video scripts with 5-second attention hooks, pacing cues, and call-to-actions.",
            promptTemplate = "Write a high-retention YouTube video script for a 5-minute video on: [Topic]. Include: 1) First 10-second retention hook, 2) Visual B-roll cues, 3) Core content points, 4) Call-to-action.",
            tags = listOf("youtube", "script", "video", "retention", "creator")
        ),
        SrsFeature(
            id = "academic_essay_outline",
            title = "Academic Essay Outliner",
            titleBn = "একাডেমিক প্রবন্ধ ও রিসার্চ আউটলাইন",
            category = FeatureCategory.WRITING,
            iconEmoji = "🎓",
            description = "Creates structured university essay outlines with thesis statement, arguments, counter-arguments, and citations.",
            promptTemplate = "Create a detailed academic essay outline with a strong thesis statement, 3 body paragraph arguments, counter-argument rebuttal, and conclusion for: ",
            tags = listOf("essay", "academic", "university", "thesis", "research")
        ),
        SrsFeature(
            id = "paraphrase_tone_shifter",
            title = "Smart Paraphraser",
            titleBn = "প্যারাফ্রেজিং ও রিরাইট টুল",
            category = FeatureCategory.WRITING,
            iconEmoji = "🔄",
            description = "Paraphrases text to eliminate plagiarism, enhance vocabulary, and shift reading level seamlessly.",
            promptTemplate = "Paraphrase and rewrite this text in original wording while preserving the core meaning and improving clarity:\n\n",
            tags = listOf("paraphrase", "rewrite", "plagiarism", "vocabulary")
        ),
        SrsFeature(
            id = "creative_story_weaver",
            title = "Creative Fiction Storyteller",
            titleBn = "রোমাঞ্চকর গল্প ও ফিকশন রাইটার",
            category = FeatureCategory.WRITING,
            iconEmoji = "📖",
            description = "Weaves captivating short stories with plot twists, dynamic character arcs, and rich sensory descriptions.",
            promptTemplate = "Write a thrilling short fiction story with a shocking plot twist based on this premise: ",
            tags = listOf("story", "fiction", "novel", "creative", "twist")
        ),
        SrsFeature(
            id = "catchy_headline_craft",
            title = "Viral Headline & Title Crafter",
            titleBn = "আকর্ষণীয় হেডলাইন ও টাইটেল",
            category = FeatureCategory.WRITING,
            iconEmoji = "🔥",
            description = "Generates 10 high-click-through-rate headlines using proven curiosity, urgency, and emotional triggers.",
            promptTemplate = "Generate 10 magnetic, high-CTR headline ideas using curiosity, urgency, and clarity for this article/video topic: ",
            tags = listOf("headline", "title", "ctr", "click", "viral")
        ),
        SrsFeature(
            id = "resume_bullet_architect",
            title = "Resume / CV Action Bullets",
            titleBn = "সিভি ও রেজুমে পাওয়ার বুলেটস",
            category = FeatureCategory.WRITING,
            iconEmoji = "📋",
            description = "Transforms weak resume descriptions into metric-driven, impact-heavy bullet points (Google XYZ formula).",
            promptTemplate = "Transform this job duty into 3 high-impact resume bullet points using the formula 'Accomplished [X] as measured by [Y], by doing [Z]' with strong action verbs:\nDuty: ",
            tags = listOf("resume", "cv", "job", "career", "bullet")
        ),
        SrsFeature(
            id = "product_review_writer",
            title = "Product Review & Testimonial",
            titleBn = "প্রোডাক্ট রিভিউ ও প্রশংসাপত্র",
            category = FeatureCategory.WRITING,
            iconEmoji = "⭐",
            description = "Drafts realistic, balanced, and persuasive product reviews highlighting pros, cons, and user experience.",
            promptTemplate = "Write an authentic, insightful product review covering build quality, usability, pros & cons, and final verdict for: ",
            tags = listOf("review", "testimonial", "product", "ecommerce")
        ),
        SrsFeature(
            id = "press_release_announcement",
            title = "Press Release & Announcement",
            titleBn = "প্রেস রিলিজ ও আনুষ্ঠানিক বিজ্ঞপ্তি",
            category = FeatureCategory.WRITING,
            iconEmoji = "📢",
            description = "Formats AP-style press releases for company launches, product updates, partnerships, and milestones.",
            promptTemplate = "Write a formal company press release following standard news media format (FOR IMMEDIATE RELEASE, Dateline, Quotes, Boilerplate) for: ",
            tags = listOf("press", "announcement", "pr", "media", "news")
        ),

        // ==========================================
        // 3. CAREER & BUSINESS (10 Features)
        // ==========================================
        SrsFeature(
            id = "mock_interview_prep",
            title = "Job Interview Simulator",
            titleBn = "জব ইন্টারভিউ প্রিপারেশন কোচ",
            category = FeatureCategory.CAREER,
            iconEmoji = "👔",
            description = "Simulates realistic interview questions with sample STAR method answers (Situation, Task, Action, Result).",
            promptTemplate = "Act as a strict hiring manager interviewing me for the role of [Target Role e.g., Android Developer, Marketing Lead]. Ask me 3 tough technical/behavioral questions and provide ideal STAR model answer outlines.",
            tags = listOf("interview", "job", "career", "star", "hiring")
        ),
        SrsFeature(
            id = "cover_letter_tailor",
            title = "Targeted Cover Letter Tailor",
            titleBn = "কাস্টম কাভার লেটার রাইটার",
            category = FeatureCategory.CAREER,
            iconEmoji = "📜",
            description = "Generates customized cover letters matched specifically to a company culture and job description.",
            promptTemplate = "Write a compelling, personalized cover letter that connects my background with this job posting:\nJob Posting: [Paste requirements]\nMy Background: [Paste experience summary]",
            tags = listOf("cover letter", "job", "application", "hiring")
        ),
        SrsFeature(
            id = "elevator_pitch_builder",
            title = "30-Second Elevator Pitch",
            titleBn = "৩০ সেকেন্ডের এলিভেটর পিচ",
            category = FeatureCategory.CAREER,
            iconEmoji = "⏱️",
            description = "Distills your personal brand or startup concept into a memorable 30-second speaking pitch.",
            promptTemplate = "Create a powerful 30-second elevator pitch that introduces who I am, the specific problem I solve, and why someone should collaborate with me:\nMy details: ",
            tags = listOf("pitch", "startup", "networking", "personal brand")
        ),
        SrsFeature(
            id = "swot_analysis_framework",
            title = "SWOT Analysis Generator",
            titleBn = "ব্যবসায়িক সোয়াট (SWOT) অ্যানালাইসিস",
            category = FeatureCategory.CAREER,
            iconEmoji = "🎯",
            description = "Structures Strengths, Weaknesses, Opportunities, and Threats for any business idea or project.",
            promptTemplate = "Perform a strategic, in-depth SWOT Analysis (Strengths, Weaknesses, Opportunities, Threats) with actionable mitigation strategies for: ",
            tags = listOf("swot", "business", "strategy", "analysis")
        ),
        SrsFeature(
            id = "business_plan_outline",
            title = "Lean Business Model Canvas",
            titleBn = "বিজনেস প্ল্যান ও মডেল ক্যানভাস",
            category = FeatureCategory.CAREER,
            iconEmoji = "📑",
            description = "Maps value proposition, customer segments, revenue streams, and cost structures for new startups.",
            promptTemplate = "Create a Lean Business Model Canvas (Problem, Solution, Key Metrics, Value Proposition, Unfair Advantage, Channels, Customer Segments, Cost Structure, Revenue Streams) for: ",
            tags = listOf("business", "lean", "startup", "model")
        ),
        SrsFeature(
            id = "ecommerce_desc_writer",
            title = "E-Commerce Product Copy",
            titleBn = "অনলাইন শপ প্রোডাক্ট বর্ণনা",
            category = FeatureCategory.CAREER,
            iconEmoji = "🛍️",
            description = "Writes compelling, benefit-focused sales copy for Daraz, Amazon, Facebook Shop, and Shopify stores.",
            promptTemplate = "Write high-converting e-commerce product copy including emotional benefits, feature bullet points, specifications, and urgency trigger for: ",
            tags = listOf("ecommerce", "daraz", "sales", "shopify", "product")
        ),
        SrsFeature(
            id = "cold_email_sales_pitch",
            title = "B2B Cold Outreach Specialist",
            titleBn = "বিটুবি কোল্ড ইমেইল ও ক্লায়েন্ট হান্ট",
            category = FeatureCategory.CAREER,
            iconEmoji = "🤝",
            description = "Crafts hyper-personalized cold emails that bypass spam filters and secure meetings with decision-makers.",
            promptTemplate = "Write a high-converting 3-part cold email sequence (Initial email, Follow-up 1, Breakup email) to pitch our service [describe service] to potential clients in [industry].",
            tags = listOf("cold email", "sales", "b2b", "leads", "client")
        ),
        SrsFeature(
            id = "meeting_minutes_action",
            title = "Meeting Summary & Action Items",
            titleBn = "মিটিং সারসংক্ষেপ ও অ্যাকশন আইটেম",
            category = FeatureCategory.CAREER,
            iconEmoji = "📌",
            description = "Transforms unstructured meeting notes or voice transcripts into executive summaries with assigned owners.",
            promptTemplate = "Extract executive decisions, action items with assigned owners, and pending questions from these meeting notes:\n\n",
            tags = listOf("meeting", "minutes", "action", "work")
        ),
        SrsFeature(
            id = "budget_categorizer_advisor",
            title = "Smart Budget & Cost Cutter",
            titleBn = "মাসিক বাজেট ও খরচ কমানোর পরামর্শ",
            category = FeatureCategory.CAREER,
            iconEmoji = "💰",
            description = "Calculates 50/30/20 budget allocations and suggests realistic areas to cut expenses and boost savings.",
            promptTemplate = "Based on a monthly income of [Amount] and these expenses [List expenses], organize a 50/30/20 budget and recommend 5 actionable ways to cut costs.",
            tags = listOf("budget", "finance", "money", "savings", "expense")
        ),
        SrsFeature(
            id = "crypto_stock_explainer",
            title = "Market & Financial Terms",
            titleBn = "শেয়ার মার্কেট ও ফিন্যান্স গাইড",
            category = FeatureCategory.CAREER,
            iconEmoji = "📈",
            description = "Demystifies P/E ratios, bull/bear markets, dividends, market capitalization, and risk management.",
            promptTemplate = "Explain this financial or market concept in simple terms with everyday analogies: ",
            tags = listOf("stock", "finance", "crypto", "investment", "market")
        ),

        // ==========================================
        // 4. EDUCATION, MATH & SCIENCE (12 Features)
        // ==========================================
        SrsFeature(
            id = "math_step_solver",
            title = "Step-by-Step Math Solver",
            titleBn = "ধাপে ধাপে গণিত সমাধান",
            category = FeatureCategory.EDUCATION,
            iconEmoji = "➗",
            description = "Solves algebra, calculus, geometry, and arithmetic problems showing every intermediate step and rule.",
            promptTemplate = "Solve this mathematics problem step-by-step. Show all working, formulas used, and explain each step in simple Bengali/English:\nProblem: ",
            tags = listOf("math", "algebra", "calculus", "solver", "step")
        ),
        SrsFeature(
            id = "physics_concept_master",
            title = "Physics Concept & Formula Guide",
            titleBn = "পদার্থবিজ্ঞান সূত্র ও কনসেপ্ট",
            category = FeatureCategory.EDUCATION,
            iconEmoji = "⚛️",
            description = "Clarifies laws of motion, thermodynamics, electromagnetism, and quantum mechanics with real-life examples.",
            promptTemplate = "Explain this physics concept, derive the relevant formula, and give a real-world intuition: ",
            tags = listOf("physics", "science", "formula", "motion", "gravity")
        ),
        SrsFeature(
            id = "chemistry_reaction_balancer",
            title = "Chemistry Equation & Reactions",
            titleBn = "রসায়ন বিক্রিয়া ও সমীকরণ সমাধান",
            category = FeatureCategory.EDUCATION,
            iconEmoji = "🧪",
            description = "Balances chemical equations, explains oxidation-reduction, organic reaction mechanisms, and periodic trends.",
            promptTemplate = "Balance this chemical reaction, explain the reaction mechanism, and state reaction conditions:\nReaction: ",
            tags = listOf("chemistry", "reaction", "organic", "periodic", "balance")
        ),
        SrsFeature(
            id = "biology_human_body",
            title = "Biology & Medical Concepts",
            titleBn = "জীববিজ্ঞান ও মানবদেহের শারীরবৃত্ত",
            category = FeatureCategory.EDUCATION,
            iconEmoji = "🧬",
            description = "Explains human anatomy, genetics, cellular respiration, immune responses, and biological ecosystems.",
            promptTemplate = "Explain the biological function, anatomical process, and importance of: ",
            tags = listOf("biology", "genetics", "medical", "anatomy", "dna")
        ),
        SrsFeature(
            id = "history_event_chronology",
            title = "History & Timeline Explainer",
            titleBn = "ইতিহাসের ঘটনাবলী ও টাইমলাইন",
            category = FeatureCategory.EDUCATION,
            iconEmoji = "🏛️",
            description = "Provides chronological timelines, key historical catalysts, and global impact of wars, treaties, and revolutions.",
            promptTemplate = "Provide a comprehensive chronological timeline, key catalysts, and historical significance of: [Historical event/period]",
            tags = listOf("history", "war", "liberation", "timeline", "event")
        ),
        SrsFeature(
            id = "quiz_exam_generator",
            title = "MCQ & Quiz Generator",
            titleBn = "কুইজ ও বহুনির্বাচনী প্রশ্ন তৈরি",
            category = FeatureCategory.EDUCATION,
            iconEmoji = "❓",
            description = "Generates 5 to 10 multiple-choice questions with answer key and explanations on any academic topic.",
            promptTemplate = "Create 5 challenging multiple-choice questions (MCQs) with 4 options each, followed by an Answer Key with detailed explanations for: ",
            tags = listOf("quiz", "mcq", "exam", "test", "study")
        ),
        SrsFeature(
            id = "flashcard_deck_creator",
            title = "Active Recall Flashcards",
            titleBn = "স্মরণশক্তি বৃদ্ধির ফ্ল্যাশকার্ড",
            category = FeatureCategory.EDUCATION,
            iconEmoji = "🎴",
            description = "Formats concise Question/Answer pairs designed for Anki and spaced-repetition exam preparation.",
            promptTemplate = "Create a deck of 8 high-yield active-recall flashcards (Front: Question/Prompt, Back: Concise Answer) based on: ",
            tags = listOf("flashcard", "anki", "memory", "study", "exam")
        ),
        SrsFeature(
            id = "grammar_tutor_pro",
            title = "English Grammar Deep Coach",
            titleBn = "ইংরেজি ব্যাকরণ ও কারেকশন কোচ",
            category = FeatureCategory.EDUCATION,
            iconEmoji = "✍️",
            description = "Diagnoses grammatical tense, preposition, subject-verb agreement, and punctuation errors with rules.",
            promptTemplate = "Correct all grammar, punctuation, and tense mistakes in this sentence, and explain the exact grammar rule for each fix:\nSentence: ",
            tags = listOf("grammar", "english", "tense", "preposition", "rules")
        ),
        SrsFeature(
            id = "vocab_builder_builder",
            title = "IELTS / GRE Vocabulary Booster",
            titleBn = "উন্নত শব্দভাণ্ডার ও আইইএলটিএস ভোকাব",
            category = FeatureCategory.EDUCATION,
            iconEmoji = "📚",
            description = "Introduces advanced contextual vocabulary with phonetic pronunciation, synonyms, antonyms, and sentence examples.",
            promptTemplate = "Provide 5 advanced high-scoring vocabulary words related to [Topic e.g., Environment, Technology, Education] with Bengali meaning, synonyms, and 2 sample sentences each.",
            tags = listOf("vocabulary", "ielts", "gre", "words", "english")
        ),
        SrsFeature(
            id = "book_chapter_summary",
            title = "Book & Chapter Summary",
            titleBn = "বইয়ের সারমর্ম ও মূল শিক্ষা",
            category = FeatureCategory.EDUCATION,
            iconEmoji = "📖",
            description = "Extracts top 5 key takeaways, actionable insights, and philosophical lessons from non-fiction books.",
            promptTemplate = "Summarize the book [Book Title by Author]. Provide: 1) One-paragraph core thesis, 2) Top 5 transformative ideas, 3) Actionable habits to apply today.",
            tags = listOf("book", "summary", "read", "learning", "ideas")
        ),
        SrsFeature(
            id = "research_paper_helper",
            title = "Research Paper & Literature Review",
            titleBn = "গবেষণাপত্র ও লিটারেচার রিভিউ",
            category = FeatureCategory.EDUCATION,
            iconEmoji = "🔬",
            description = "Helps formulate research questions, methodology frameworks, and academic literature reviews.",
            promptTemplate = "Help structure a research methodology and literature review outline for this research question: ",
            tags = listOf("research", "paper", "journal", "academic", "methodology")
        ),
        SrsFeature(
            id = "homework_checker_tutor",
            title = "Homework Checker & Explainer",
            titleBn = "হোমওয়ার্ক চেকার ও শিক্ষক",
            category = FeatureCategory.EDUCATION,
            iconEmoji = "📝",
            description = "Reviews homework answers, points out where reasoning went wrong, and guides without spoiling the joy of learning.",
            promptTemplate = "Review my answer to this assignment question. Highlight where my reasoning is accurate and where improvements are needed:\nQuestion: [Question]\nMy Answer: [Answer]",
            tags = listOf("homework", "assignment", "school", "college", "check")
        ),

        // ==========================================
        // 5. LANGUAGE & TRANSLATION (10 Features)
        // ==========================================
        SrsFeature(
            id = "en_to_bn_pro",
            title = "English to Shuddho Bengali",
            titleBn = "ইংরেজি থেকে প্রমিত বাংলা অনুবাদ",
            category = FeatureCategory.LANGUAGE,
            iconEmoji = "🇧🇩",
            description = "Flawless, natural, context-aware translation from English to high-quality grammatical Bengali.",
            promptTemplate = "Translate the following English text into elegant, natural, standard Bengali (শুদ্ধ প্রমিত বাংলা):\n\n",
            tags = listOf("translate", "bengali", "english", "bangla", "shuddho")
        ),
        SrsFeature(
            id = "bn_to_en_pro",
            title = "Bengali to Professional English",
            titleBn = "বাংলা থেকে প্রফেশনাল ইংরেজি",
            category = FeatureCategory.LANGUAGE,
            iconEmoji = "🇬🇧",
            description = "Converts colloquial or formal Bengali thoughts into fluent, native-sounding English.",
            promptTemplate = "Translate this Bengali text into fluent, native, professional English:\n\n",
            tags = listOf("translate", "english", "bangla", "fluent")
        ),
        SrsFeature(
            id = "bengali_proverbs_idioms",
            title = "Bengali Proverb & Idiom Guide",
            titleBn = "প্রবাদ-প্রবচন ও বাগধারা অভিধান",
            category = FeatureCategory.LANGUAGE,
            iconEmoji = "📜",
            description = "Explains the origins, meanings, and English equivalents of traditional Bengali proverbs and বাগধারা.",
            promptTemplate = "Explain the meaning, cultural context, and English equivalent of this Bengali proverb or idiom (বাগধারা):\n",
            tags = listOf("idiom", "proverb", "bagdhara", "bengali", "culture")
        ),
        SrsFeature(
            id = "chittagong_sylhet_dialect",
            title = "Regional Dialects (চাটগাঁইয়া, সিলেটি)",
            titleBn = "আঞ্চলিক ভাষার রূপান্তর",
            category = FeatureCategory.LANGUAGE,
            iconEmoji = "🗣️",
            description = "Converts standard Bengali sentences into authentic Chittagonian, Sylheti, Noakhali, or Barisali dialects.",
            promptTemplate = "Convert this sentence into authentic [Chittagonian / Sylheti / Noakhali / Barisali] regional dialect with explanation of unique words:\nSentence: ",
            tags = listOf("dialect", "regional", "sylheti", "chittagong", "noakhali")
        ),
        SrsFeature(
            id = "tone_politeness_converter",
            title = "Honorifics & Politeness Shifter",
            titleBn = "আপনি / তুমি / তুই সম্মানসূচক পরিবর্তন",
            category = FeatureCategory.LANGUAGE,
            iconEmoji = "🙇",
            description = "Adjusts pronouns, verbs, and tone between Formal (আপনি), Casual (তুমি), and Intimate (তুই).",
            promptTemplate = "Rewrite this sentence in 3 distinct registers of Bengali politeness: 1) Highly respectful (আপনি), 2) Friendly/Peer (তুমি), 3) Very close (তুই):\nSentence: ",
            tags = listOf("tone", "polite", "apni", "tumi", "honorific")
        ),
        SrsFeature(
            id = "genz_slang_translator",
            title = "Gen-Z & Internet Slang Decoder",
            titleBn = "আধুনিক ইন্টারনেট স্ল্যাং ও অর্থ",
            category = FeatureCategory.LANGUAGE,
            iconEmoji = "🕶️",
            description = "Decodes modern slang (Rizz, Cap, Bet, Sus, Sigma, Delulu) into clear Bengali and everyday English.",
            promptTemplate = "Explain the origin, meaning, and sample conversation usage of this modern slang term in Bengali: ",
            tags = listOf("slang", "genz", "internet", "modern", "chat")
        ),
        SrsFeature(
            id = "polyglot_multilingual_chat",
            title = "Polyglot Multi-Language Chat",
            titleBn = "বহুভাষিক চ্যাট (হিন্দি, আরবি, জাপানি)",
            category = FeatureCategory.LANGUAGE,
            iconEmoji = "🌍",
            description = "Translates and converses across Arabic, Hindi, Japanese, Spanish, German, and French.",
            promptTemplate = "Translate this phrase into Arabic (with transliteration), Hindi, and Japanese:\nPhrase: ",
            tags = listOf("polyglot", "arabic", "hindi", "japanese", "spanish")
        ),
        SrsFeature(
            id = "pronunciation_phonetics",
            title = "Pronunciation & Phonetic Guide",
            titleBn = "সঠিক উচ্চারণ ও ফোনেটিক্স নির্দেশিকা",
            category = FeatureCategory.LANGUAGE,
            iconEmoji = "🗣️",
            description = "Provides IPA symbols, syllables, and audio imitation guides for difficult English or foreign words.",
            promptTemplate = "Provide the exact phonetic pronunciation (with Bengali pronunciation script and syllable breakdown) for: ",
            tags = listOf("pronounce", "phonetics", "accent", "speak")
        ),
        SrsFeature(
            id = "legal_contract_translator",
            title = "Legal & Contract Terminology",
            titleBn = "আইনি দলিল ও চুক্তিনামার ভাষা",
            category = FeatureCategory.LANGUAGE,
            iconEmoji = "⚖️",
            description = "Translates and simplifies complex legal clauses, NDAs, tenancy agreements, and terms of service.",
            promptTemplate = "Translate and simplify this legal clause into plain, understandable terms without altering contractual significance:\nClause: ",
            tags = listOf("legal", "contract", "nda", "law", "terms")
        ),
        SrsFeature(
            id = "speech_draft_writer",
            title = "Public Speech & Welcome Address",
            titleBn = "বক্তৃতা ও স্বাগত ভাষণ",
            category = FeatureCategory.LANGUAGE,
            iconEmoji = "🎙️",
            description = "Drafts charismatic 2-minute to 5-minute speeches for events, weddings, school ceremonies, and meetings.",
            promptTemplate = "Draft an inspiring, charismatic 3-minute public speech for [Occasion e.g., Graduation ceremony / Welcome address / Wedding toast].",
            tags = listOf("speech", "public speaking", "address", "ceremony")
        ),

        // ==========================================
        // 6. PRODUCTIVITY & LIFE HACKS (10 Features)
        // ==========================================
        SrsFeature(
            id = "daily_timebox_schedule",
            title = "Daily Time-Blocking Master",
            titleBn = "দৈনিক টাইম-ব্লকিং শিডিউল",
            category = FeatureCategory.PRODUCTIVITY,
            iconEmoji = "⏰",
            description = "Structures your entire day into ultra-focused 90-minute blocks with rest intervals and buffer zones.",
            promptTemplate = "Create an optimized time-blocked daily schedule from 7:00 AM to 11:00 PM for someone who needs to accomplish these goals today:\nGoals: ",
            tags = listOf("schedule", "time", "plan", "daily", "productivity")
        ),
        SrsFeature(
            id = "habit_stacking_coach",
            title = "Atomic Habit Stacking Coach",
            titleBn = "অভ্যাস গঠন ও হ্যাবিট ট্র্যাকিং",
            category = FeatureCategory.PRODUCTIVITY,
            iconEmoji = "🧱",
            description = "Applies James Clear's Atomic Habits principles to anchor new behaviors onto existing daily routines.",
            promptTemplate = "Design a 21-day Atomic Habit stacking plan to build [Desired Habit] using implementation intentions ('After I [current habit], I will [new habit]').",
            tags = listOf("habit", "routine", "discipline", "atomic")
        ),
        SrsFeature(
            id = "pomodoro_task_breaker",
            title = "Pomodoro Task Deconstructor",
            titleBn = "পোমোডোরো টাস্ক ব্রেকডাউন",
            category = FeatureCategory.PRODUCTIVITY,
            iconEmoji = "🍅",
            description = "Breaks overwhelming projects into bite-sized 25-minute actionable sprint checkpoints.",
            promptTemplate = "Break down this complex, intimidating project into distinct 25-minute Pomodoro sprint tasks with clear definitions of done:\nProject: ",
            tags = listOf("pomodoro", "focus", "sprint", "task")
        ),
        SrsFeature(
            id = "pros_cons_matrix",
            title = "Decision Matrix (Pros & Cons)",
            titleBn = "সিদ্ধান্ত গ্রহণের তুলনামূলক বিশ্লেষণ",
            category = FeatureCategory.PRODUCTIVITY,
            iconEmoji = "⚖️",
            description = "Analyzes tough personal or professional dilemmas with weighted criteria and risk mitigation.",
            promptTemplate = "Help me make a rational decision between Option A: [Describe] and Option B: [Describe]. Build a weighted pros/cons matrix and final verdict.",
            tags = listOf("decision", "matrix", "pros", "cons", "choice")
        ),
        SrsFeature(
            id = "smart_grocery_checklist",
            title = "Grocery & Pantry Organizer",
            titleBn = "বাজারের ফর্দ ও খাদ্য তালিকা",
            category = FeatureCategory.PRODUCTIVITY,
            iconEmoji = "🛒",
            description = "Groups grocery items by supermarket aisle (Produce, Dairy, Spices, Meat) to save 50% shopping time.",
            promptTemplate = "Organize a healthy weekly grocery checklist for a family of [Number] categorized by grocery department with estimated essentials:",
            tags = listOf("grocery", "shopping", "pantry", "food")
        ),
        SrsFeature(
            id = "travel_itinerary_architect",
            title = "Travel & Vacation Itinerary",
            titleBn = "ভ্রমণ পরিকল্পনা ও ট্যুর গাইড",
            category = FeatureCategory.PRODUCTIVITY,
            iconEmoji = "✈️",
            description = "Creates day-by-day travel plans with local cuisine recommendations, budget tips, and packing lists.",
            promptTemplate = "Create a detailed [Number]-day travel itinerary for [Destination e.g., Cox's Bazar, Sajek, Nepal, Turkey] on a [Budget/Luxury] budget. Include sightseeing, local food spots, and safety tips.",
            tags = listOf("travel", "tour", "vacation", "trip", "itinerary")
        ),
        SrsFeature(
            id = "fridge_recipe_generator",
            title = "Fridge Leftover Recipe Maker",
            titleBn = "ঘরের উপকরণ দিয়ে মজাদার রান্না",
            category = FeatureCategory.PRODUCTIVITY,
            iconEmoji = "🍳",
            description = "Suggests delicious meals and cooking steps using only the ingredients currently in your kitchen.",
            promptTemplate = "I have these ingredients in my kitchen: [List ingredients e.g., eggs, onion, potatoes, rice]. Suggest 2 delicious recipes I can make in under 30 minutes with step-by-step cooking instructions.",
            tags = listOf("recipe", "cooking", "food", "kitchen", "dinner")
        ),
        SrsFeature(
            id = "home_cleaning_organizer",
            title = "Home Declutter & Organization",
            titleBn = "ঘর গোছানো ও পরিচ্ছন্নতা রুটিন",
            category = FeatureCategory.PRODUCTIVITY,
            iconEmoji = "🧹",
            description = "Generates efficient 15-minute room-by-room decluttering checklists and cleaning schedules.",
            promptTemplate = "Give me an efficient 30-minute room-by-room rapid decluttering and cleaning checklist for: [e.g., Bedroom, Study desk, Living room].",
            tags = listOf("cleaning", "home", "organize", "declutter")
        ),
        SrsFeature(
            id = "gift_idea_recommender",
            title = "Thoughtful Gift Ideator",
            titleBn = "সেরা উপহার আইডিয়া জেনারেটর",
            category = FeatureCategory.PRODUCTIVITY,
            iconEmoji = "🎁",
            description = "Finds personalized, creative gift ideas tailored to the recipient's hobbies, age, and budget.",
            promptTemplate = "Suggest 5 creative, memorable gift ideas for a [Relationship e.g., friend, mother, colleague] who loves [Hobbies] within a budget of [Budget].",
            tags = listOf("gift", "present", "birthday", "anniversary")
        ),
        SrsFeature(
            id = "diy_troubleshooter",
            title = "DIY Fixer & Troubleshooter",
            titleBn = "বাসার জিনিস মেরামত ও টেক ফিক্স",
            category = FeatureCategory.PRODUCTIVITY,
            iconEmoji = "🔧",
            description = "Guides you step-by-step through fixing squeaky doors, clogged sinks, slow laptops, or frozen apps.",
            promptTemplate = "Guide me step-by-step on how to troubleshoot and fix: [Describe problem e.g., Android phone screen flickering / Laptop running slow / Wi-Fi dropping].",
            tags = listOf("diy", "fix", "repair", "troubleshoot", "tech")
        ),

        // ==========================================
        // 7. HEALTH & WELLNESS (10 Features)
        // ==========================================
        SrsFeature(
            id = "personalized_workout_plan",
            title = "Custom Workout Routine",
            titleBn = "ব্যায়াম ও ফিটনেস রুটিন",
            category = FeatureCategory.HEALTH,
            iconEmoji = "🏋️",
            description = "Builds weekly workout splits (Push/Pull/Legs or Calisthenics) based on your equipment and schedule.",
            promptTemplate = "Design a 4-day weekly workout routine for [Goal e.g., fat loss, muscle gain] using [Home bodyweight / Gym dumbbells] with sets, reps, and warm-up guide.",
            tags = listOf("workout", "fitness", "gym", "exercise", "muscle")
        ),
        SrsFeature(
            id = "calorie_macro_estimator",
            title = "Nutrition & Macro Guide",
            titleBn = "ক্যালোরি ও পুষ্টি উপাদান হিসাব",
            category = FeatureCategory.HEALTH,
            iconEmoji = "🥗",
            description = "Estimates calories, protein, carbs, and fats in Bengali and international meals.",
            promptTemplate = "Estimate the calories and macronutrient breakdown (Protein, Carbs, Fat) for this typical meal: [Describe food e.g., 1 cup rice, chicken curry, dal, salad].",
            tags = listOf("calorie", "nutrition", "diet", "protein", "food")
        ),
        SrsFeature(
            id = "hydration_water_coach",
            title = "Hydration & Water Schedule",
            titleBn = "পানি পানের নিয়ম ও গাইড",
            category = FeatureCategory.HEALTH,
            iconEmoji = "💧",
            description = "Calculates optimal daily water requirements and plans hourly reminders based on your weight and climate.",
            promptTemplate = "Calculate ideal daily water intake for a person weighing [Weight kg] in a warm climate, and provide an hourly hydration schedule throughout the day.",
            tags = listOf("water", "hydration", "health", "drink")
        ),
        SrsFeature(
            id = "breathwork_calm_guide",
            title = "Box Breathing & Anti-Anxiety",
            titleBn = "শ্বাসপ্রশ্বাসের ব্যায়াম ও মানসিক শান্তি",
            category = FeatureCategory.HEALTH,
            iconEmoji = "🌬️",
            description = "Guides 4-4-4-4 Box Breathing and 4-7-8 relaxation techniques to instantly reduce stress and heart rate.",
            promptTemplate = "Guide me through a 3-minute 4-7-8 breathing exercise to calm down anxiety, stress, or nervous energy step-by-step in comforting Bengali/English.",
            tags = listOf("breathing", "anxiety", "calm", "meditation", "stress")
        ),
        SrsFeature(
            id = "sleep_hygiene_checklist",
            title = "Deep Sleep Optimization",
            titleBn = "গভীর ঘুমের টিপস ও স্লিপ হাইজিন",
            category = FeatureCategory.HEALTH,
            iconEmoji = "🌙",
            description = "Actionable tips on circadian rhythm, blue light cutoffs, room temperature, and evening routines.",
            promptTemplate = "Provide an actionable, scientifically grounded bedtime protocol to fall asleep in under 15 minutes and achieve deeper REM sleep.",
            tags = listOf("sleep", "insomnia", "bedtime", "rest", "night")
        ),
        SrsFeature(
            id = "desk_posture_ergonomics",
            title = "Desk Ergonomics & Posture",
            titleBn = "ডেস্ক ওয়ার্ক ও পিঠের ব্যথার সমাধান",
            category = FeatureCategory.HEALTH,
            iconEmoji = "🪑",
            description = "Ergonomic chair setups, screen height rules, and 2-minute stretches to prevent neck and back pain.",
            promptTemplate = "Give me 4 simple 60-second desk stretches to relieve neck and lower back stiffness from sitting at a computer all day.",
            tags = listOf("posture", "ergonomics", "desk", "stretch", "back")
        ),
        SrsFeature(
            id = "healthy_snack_swapper",
            title = "Smart Healthy Food Swaps",
            titleBn = "স্বাস্থ্যকর খাবারের বিকল্প নির্বাচন",
            category = FeatureCategory.HEALTH,
            iconEmoji = "🍎",
            description = "Swaps high-sugar junk foods and deep-fried snacks for nutritious, satisfying alternatives.",
            promptTemplate = "Provide 5 healthy, affordable snack alternatives to replace junk food (like chips, sugary sodas, and oily singara) available in Bangladesh/South Asia.",
            tags = listOf("snack", "healthy", "diet", "nutrition", "swap")
        ),
        SrsFeature(
            id = "couch_to_5k_runner",
            title = "Running & Cardio Progression",
            titleBn = "রানিং ও কার্ডিও ফিটনেস গাইড",
            category = FeatureCategory.HEALTH,
            iconEmoji = "🏃",
            description = "Interval run/walk schedules to safely build cardiovascular stamina from beginner to 5K.",
            promptTemplate = "Create a beginner-friendly 4-week running progression schedule combining walking and jogging intervals to build stamina safely.",
            tags = listOf("running", "cardio", "5k", "jog", "stamina")
        ),
        SrsFeature(
            id = "mental_gratitude_journal",
            title = "Gratitude & Mindfulness Journal",
            titleBn = "কৃতজ্ঞতা ও ইতিবাচক মানসিকতা",
            category = FeatureCategory.HEALTH,
            iconEmoji = "✨",
            description = "Daily reflection questions and mindfulness prompts to cultivate optimism and resilience.",
            promptTemplate = "Give me 3 introspective journaling prompts for tonight to reflect on small wins, overcome frustration, and cultivate gratitude.",
            tags = listOf("mindfulness", "gratitude", "journal", "mental health")
        ),
        SrsFeature(
            id = "bmi_bmr_calculator",
            title = "BMI & BMR Metabolic Guide",
            titleBn = "বিএমআই ও ক্যালোরি মেটাবলিজম",
            category = FeatureCategory.HEALTH,
            iconEmoji = "📏",
            description = "Explains body mass index, basal metabolic rate, and maintenance calorie calculations.",
            promptTemplate = "Explain how to calculate BMR and TDEE (Total Daily Energy Expenditure) for weight maintenance or fat loss, and what factors affect it.",
            tags = listOf("bmi", "bmr", "metabolism", "weight", "calories")
        ),

        // ==========================================
        // 8. SOCIAL MEDIA & MARKETING (10 Features)
        // ==========================================
        SrsFeature(
            id = "viral_facebook_post",
            title = "Viral Facebook Status & Story",
            titleBn = "ভাইরাল ফেসবুক পোস্ট ও স্ট্যাটাস",
            category = FeatureCategory.SOCIAL,
            iconEmoji = "📘",
            description = "Generates relatable, emotional, or humorous Facebook posts with high comment and share potential.",
            promptTemplate = "Write an engaging, relatable Facebook status (with emojis and line breaks) in conversational Bengali about: [Topic e.g., Traffic in Dhaka / Childhood memories / Work life].",
            tags = listOf("facebook", "viral", "status", "social", "bangla")
        ),
        SrsFeature(
            id = "instagram_caption_bio",
            title = "Aesthetic Instagram Bio & Captions",
            titleBn = "ইনস্টাগ্রাম বায়ো ও নান্দনিক ক্যাপশন",
            category = FeatureCategory.SOCIAL,
            iconEmoji = "📸",
            description = "Crafts aesthetic 1-line captions, poetic quotes, and stylish multi-line profile bios.",
            promptTemplate = "Generate 5 aesthetic, short Instagram captions (some moody, some witty) for a photo showing: [Describe photo e.g., Sunset over river / Coffee and book].",
            tags = listOf("instagram", "caption", "bio", "aesthetic", "quote")
        ),
        SrsFeature(
            id = "linkedin_thought_leader",
            title = "LinkedIn Thought-Leadership Post",
            titleBn = "লিংকডইন প্রফেশনাল পোস্ট",
            category = FeatureCategory.SOCIAL,
            iconEmoji = "💼",
            description = "Formats viral LinkedIn posts with strong first-line hooks, storytelling, and professional insights.",
            promptTemplate = "Write a viral-format LinkedIn post with a scroll-stopping first line, a short storytelling lesson, and an engaging question at the end about: ",
            tags = listOf("linkedin", "career", "networking", "professional")
        ),
        SrsFeature(
            id = "tiktok_reels_hook",
            title = "Reels & TikTok 3-Second Hooks",
            titleBn = "রিলস ও টিকটক ৩-সেকেন্ড হুক",
            category = FeatureCategory.SOCIAL,
            iconEmoji = "⚡",
            description = "Creates psychological curiosity hooks designed to stop thumbs from scrolling within 3 seconds.",
            promptTemplate = "Give me 5 viral 3-second opening hook scripts for an Instagram Reel / TikTok video about: [Topic]. Focus on pattern interruption and curiosity.",
            tags = listOf("reels", "tiktok", "shorts", "hook", "viral")
        ),
        SrsFeature(
            id = "ad_copy_high_convert",
            title = "Facebook & Google Ad Copy",
            titleBn = "হাই-কনভার্টিং বিজ্ঞাপন ও অ্যাড কপি",
            category = FeatureCategory.SOCIAL,
            iconEmoji = "🎯",
            description = "Uses AIDA (Attention, Interest, Desire, Action) and PAS (Problem, Agitate, Solution) copywriting frameworks.",
            promptTemplate = "Write 2 high-converting Facebook Ad copy variations (one using PAS framework, one using AIDA) for selling: [Product/Service].",
            tags = listOf("ad", "facebook ads", "copywriting", "sales", "marketing")
        ),
        SrsFeature(
            id = "twitter_thread_creator",
            title = "Twitter / X Viral Thread",
            titleBn = "টুইটার / এক্স থ্রেড রাইটার",
            category = FeatureCategory.SOCIAL,
            iconEmoji = "🐦",
            description = "Drafts 6-tweet informative threads with punchy hooks, value points, and a retweetable conclusion.",
            promptTemplate = "Write an informative, viral 5-tweet Twitter / X thread breaking down: [Topic]. Include hook tweet, 3 value tweets, and a recap tweet.",
            tags = listOf("twitter", "thread", "tweet", "x", "viral")
        ),
        SrsFeature(
            id = "hashtag_strategist",
            title = "Hashtag & Tag Ranking Strategist",
            titleBn = "হ্যাশট্যাগ ও ট্যাগ রিসার্চ",
            category = FeatureCategory.SOCIAL,
            iconEmoji = "#️⃣",
            description = "Curates balanced hashtags across low, medium, and high competition tiers for maximum discoverability.",
            promptTemplate = "Provide 20 targeted hashtags for an Instagram / TikTok post about [Topic] organized into: 5 High-reach, 10 Niche-specific, and 5 Community hashtags.",
            tags = listOf("hashtag", "tags", "reach", "algorithm")
        ),
        SrsFeature(
            id = "influencer_collab_pitch",
            title = "Influencer Brand Collab Pitch",
            titleBn = "ইনফ্লুয়েন্সার কোলাবরেশন পিচ",
            category = FeatureCategory.SOCIAL,
            iconEmoji = "🌟",
            description = "Professional outreach message to pitch sponsored partnerships to brands or content creators.",
            promptTemplate = "Write a polite, professional brand sponsorship pitch email / DM to a brand proposing a creative collaboration for my channel/page: ",
            tags = listOf("influencer", "collab", "sponsorship", "pitch", "brand")
        ),
        SrsFeature(
            id = "email_newsletter_hook",
            title = "Newsletter Subject & Openers",
            titleBn = "নিউজলেটার বিষয় ও ওপেনিং হুক",
            category = FeatureCategory.SOCIAL,
            iconEmoji = "📬",
            description = "Crafts email subject lines with 40%+ open rate potential and warm personal opening paragraphs.",
            promptTemplate = "Write 5 high-open-rate subject lines and an engaging personal opening paragraph for a weekly newsletter about: ",
            tags = listOf("newsletter", "email", "subject line", "open rate")
        ),
        SrsFeature(
            id = "seo_meta_generator",
            title = "SEO Meta Title & Description",
            titleBn = "এসইও মেটা টাইটেল ও ডেসক্রিপশন",
            category = FeatureCategory.SOCIAL,
            iconEmoji = "🔍",
            description = "Generates Google SERP-compliant meta titles (under 60 chars) and descriptions (under 155 chars).",
            promptTemplate = "Generate 3 SEO Meta Title (under 60 characters) and Meta Description (under 155 characters) options targeting the primary keyword: [Keyword].",
            tags = listOf("seo", "meta", "google", "search", "keyword")
        ),

        // ==========================================
        // 9. CREATIVITY, ENTERTAINMENT & FUN (10 Features)
        // ==========================================
        SrsFeature(
            id = "bengali_comedy_jokes",
            title = "Bengali Jokes & Standup Comedy",
            titleBn = "হাস্যরসাত্মক বাংলা জোকস ও কৌতুক",
            category = FeatureCategory.ENTERTAINMENT,
            iconEmoji = "😂",
            description = "Tells clean, witty, culturally hilarious Bengali jokes, puns, and funny anecdotes.",
            promptTemplate = "একটি মজার, সম্পূর্ণ নতুন এবং হাসির বাংলা কৌতুক (জোকস) বা ছোট রম্য গল্প বলো যা শুনে মন ভালো হয়ে যায়!",
            tags = listOf("jokes", "comedy", "funny", "laugh", "bengali")
        ),
        SrsFeature(
            id = "riddle_puzzle_master",
            title = "Brain Teasers & Bengali Riddles",
            titleBn = "বুদ্ধির ধাঁধা ও ব্রেন টিজার",
            category = FeatureCategory.ENTERTAINMENT,
            iconEmoji = "🧩",
            description = "Presents clever riddles and logical brain teasers with hidden answers for friends and family.",
            promptTemplate = "আমাকে একটি কঠিন কিন্তু মজার বাংলা ধাঁধা (বা লজিক পাজল) দাও, আর উত্তরটি নিচে লুকানো বা ব্যাখ্যা সহ রাখো যাতে আমি প্রথমে নিজে চিন্তা করতে পারি।",
            tags = listOf("riddle", "dhadha", "puzzle", "brain", "game")
        ),
        SrsFeature(
            id = "song_lyrics_rap_verse",
            title = "Song Lyrics & Rap Verse Writer",
            titleBn = "গানের কথা ও হিপহপ র‍্যাপ লিরিক্স",
            category = FeatureCategory.ENTERTAINMENT,
            iconEmoji = "🎵",
            description = "Writes rhyming rap verses, catchy chorus hooks, and acoustic melodies with meter and tempo cues.",
            promptTemplate = "Write a catchy song chorus and a 16-bar rhyming rap verse (with internal rhymes and flow) about: ",
            tags = listOf("song", "lyrics", "rap", "music", "rhyme")
        ),
        SrsFeature(
            id = "movie_anime_recommender",
            title = "Movie, Anime & Series Matchmaker",
            titleBn = "মুভি, সিরিজ ও অ্যানিমে পরামর্শদাতা",
            category = FeatureCategory.ENTERTAINMENT,
            iconEmoji = "🍿",
            description = "Recommends hidden gem movies, anime, and TV shows based on your favorite genres and mood.",
            promptTemplate = "Recommend 3 incredible movies, anime, or series (with IMDb rating, premise, and why it's worth watching) if I loved: [Name 1 or 2 favorites] and I am in the mood for: [Mood e.g., Mind-bending thriller, Heartwarming anime, Fast-paced mystery].",
            tags = listOf("movie", "anime", "series", "netflix", "recommend")
        ),
        SrsFeature(
            id = "game_lore_quest_maker",
            title = "RPG Quest & Game Lore Creator",
            titleBn = "ভিডিও গেম স্টোরি ও কোয়েস্ট ক্রিয়েটর",
            category = FeatureCategory.ENTERTAINMENT,
            iconEmoji = "🎮",
            description = "Designs fantasy quests, boss dialogue, magical artifacts, and backstories for gaming and D&D campaigns.",
            promptTemplate = "Design an epic RPG side-quest (Quest Name, NPC Quest Giver, Objective, Moral Dilemma, and Unique Reward) in a dark fantasy setting.",
            tags = listOf("game", "rpg", "quest", "gaming", "dnd")
        ),
        SrsFeature(
            id = "brand_channel_name_ideator",
            title = "Brand & Channel Name Generator",
            titleBn = "ইউনিক ব্র্যান্ড ও চ্যানেল নাম আইডিয়া",
            category = FeatureCategory.ENTERTAINMENT,
            iconEmoji = "💡",
            description = "Brainstorms modern, catchy, memorable brand, app, or YouTube channel names with dot-com appeal.",
            promptTemplate = "Brainstorm 10 catchy, unique, and modern brand or YouTube channel names for a venture in [Niche e.g., Tech review, Cafe, Fashion, Coding tutorial]. Categorize into Minimalist, Punchy, and Creative.",
            tags = listOf("name", "brand", "startup", "channel", "creative")
        ),
        SrsFeature(
            id = "dream_interpreter_meaning",
            title = "Dream Analysis & Symbolism",
            titleBn = "স্বপ্নের ব্যাখ্যা ও মনস্তাত্ত্বিক তাৎপর্য",
            category = FeatureCategory.ENTERTAINMENT,
            iconEmoji = "🌌",
            description = "Explores psychological symbolism and Jungian archetypes behind mysterious dreams.",
            promptTemplate = "Explore the psychological symbolism and potential subconscious meaning of this dream:\nDream: ",
            tags = listOf("dream", "psychology", "symbol", "mind", "meaning")
        ),
        SrsFeature(
            id = "philosophical_debater",
            title = "Philosophy & Thought Experiments",
            titleBn = "দার্শনিক বিতর্ক ও যুক্তি পরীক্ষা",
            category = FeatureCategory.ENTERTAINMENT,
            iconEmoji = "🤔",
            description = "Explores the Trolley Problem, Ship of Theseus, free will, simulation hypothesis, and stoicism.",
            promptTemplate = "Discuss this deep philosophical question or thought experiment from multiple intellectual viewpoints: ",
            tags = listOf("philosophy", "debate", "ethics", "stoic", "logic")
        ),
        SrsFeature(
            id = "trivia_master_challenge",
            title = "World Trivia & General Knowledge",
            titleBn = "বিশ্ব সাধারণ জ্ঞান ও ট্রিভিয়া কুইজ",
            category = FeatureCategory.ENTERTAINMENT,
            iconEmoji = "🌍",
            description = "Fascinating facts about geography, space, world history, and science to impress friends.",
            promptTemplate = "Share 5 astonishing, lesser-known trivia facts about [Topic e.g., Space exploration, Ancient Egypt, Deep ocean, Bangladesh geography] that most people don't know.",
            tags = listOf("trivia", "knowledge", "facts", "gk", "science")
        ),
        SrsFeature(
            id = "fantasy_world_builder",
            title = "Fantasy World & Myth Builder",
            titleBn = "কাল্পনিক রূপকথা ও বিশ্ব নির্মাণ",
            category = FeatureCategory.ENTERTAINMENT,
            iconEmoji = "🏰",
            description = "Builds fantasy magic systems, alien planets, mythologies, and kingdom geography.",
            promptTemplate = "Create a unique magical system with strict rules, limitations, and costs for a fantasy novel based on: ",
            tags = listOf("fantasy", "worldbuilding", "myth", "magic", "novel")
        ),

        // ==========================================
        // 10. DEVICE & SYSTEM AGENT (12 Features)
        // ==========================================
        SrsFeature(
            id = "dev_pure_bg_mode",
            title = "Pure Background Voice & Action",
            titleBn = "শুদ্ধ ব্যাকগ্রাউন্ড এআই (কথা ও কাজ)",
            category = FeatureCategory.DEVICE,
            iconEmoji = "🎙️",
            description = "Operates 100% invisibly without any on-screen bubble or window. Speaks aloud and automates all tasks.",
            promptTemplate = "শুদ্ধ ব্যাকগ্রাউন্ড এআই চালু করো",
            isDeviceAction = true,
            actionCommand = "pure_background",
            tags = listOf("background", "voice", "handsfree", "agent", "headless")
        ),
        SrsFeature(
            id = "dev_bubble_resize_float",
            title = "Floating Bubble & Resize Controls",
            titleBn = "ভাসমান বাবল ও সাইজ নিয়ন্ত্রণ",
            category = FeatureCategory.DEVICE,
            iconEmoji = "🫧",
            description = "Resize bubble (40dp to 104dp) and move anywhere on screen without forced dock restrictions.",
            promptTemplate = "Change floating bubble size and settings",
            isDeviceAction = false,
            tags = listOf("floating", "bubble", "resize", "overlay", "drag")
        ),
        SrsFeature(
            id = "dev_flashlight_toggle",
            title = "Instant Flashlight Torch",
            titleBn = "টর্চ লাইট অন / অফ",
            category = FeatureCategory.DEVICE,
            iconEmoji = "🔦",
            description = "Toggles your device's camera LED flash on or off instantly with one tap.",
            promptTemplate = "Turn on flashlight",
            isDeviceAction = true,
            actionCommand = "flashlight",
            tags = listOf("torch", "flashlight", "light", "hardware")
        ),
        SrsFeature(
            id = "dev_battery_diagnostics",
            title = "Battery Health & Charge Info",
            titleBn = "ব্যাটারি স্বাস্থ্য ও চার্জের তথ্য",
            category = FeatureCategory.DEVICE,
            iconEmoji = "🔋",
            description = "Reads exact battery percentage, charging state, battery health, and temperature.",
            promptTemplate = "Check battery status",
            isDeviceAction = true,
            actionCommand = "battery",
            tags = listOf("battery", "charging", "health", "power")
        ),
        SrsFeature(
            id = "dev_camera_open",
            title = "Fast Camera Launcher",
            titleBn = "ক্যামেরা ওপেন করুন",
            category = FeatureCategory.DEVICE,
            iconEmoji = "📷",
            description = "Opens the default device camera app ready to snap high-resolution photos or record video.",
            promptTemplate = "Open camera",
            isDeviceAction = true,
            actionCommand = "camera",
            tags = listOf("camera", "photo", "record", "video")
        ),
        SrsFeature(
            id = "dev_screen_reader_ocr",
            title = "Screen Reader & Vision Analyzer",
            titleBn = "স্ক্রিন রিডার ও টেক্সট বিশ্লেষণ",
            category = FeatureCategory.DEVICE,
            iconEmoji = "👁️",
            description = "Reads text currently visible on your phone screen to summarize, explain, or answer questions.",
            promptTemplate = "What's on my screen?",
            isDeviceAction = true,
            actionCommand = "screen_context",
            tags = listOf("screen", "ocr", "read", "accessibility", "vision")
        ),
        SrsFeature(
            id = "dev_voice_recorder_open",
            title = "System Audio Voice Recorder",
            titleBn = "ভয়েস রেকর্ডার চালু করুন",
            category = FeatureCategory.DEVICE,
            iconEmoji = "🎙️",
            description = "Opens system sound recorder to capture memos, interviews, and audio notes.",
            promptTemplate = "Open voice recorder",
            isDeviceAction = true,
            actionCommand = "voice_recorder",
            tags = listOf("recorder", "voice", "mic", "sound", "memo")
        ),
        SrsFeature(
            id = "dev_volume_master",
            title = "Volume Up / Down / Mute",
            titleBn = "ভলিউম কন্ট্রোল ও মিউট",
            category = FeatureCategory.DEVICE,
            iconEmoji = "🔊",
            description = "Controls media volume slider up, down, or mutes phone audio instantly.",
            promptTemplate = "Volume up",
            isDeviceAction = true,
            actionCommand = "volume_up",
            tags = listOf("volume", "sound", "mute", "audio", "speaker")
        ),
        SrsFeature(
            id = "dev_auto_scroll_reels",
            title = "Auto-Scroll Reels & Shorts",
            titleBn = "হ্যান্ডস-ফ্রি অটো স্ক্রোল রিলস",
            category = FeatureCategory.DEVICE,
            iconEmoji = "🔄",
            description = "Hands-free automatic scrolling every 15 seconds for YouTube Shorts, Facebook Reels, and TikTok.",
            promptTemplate = "Start auto-scroll reels",
            isDeviceAction = true,
            actionCommand = "auto_scroll",
            tags = listOf("scroll", "reels", "shorts", "tiktok", "gesture")
        ),
        SrsFeature(
            id = "dev_system_screenshot",
            title = "Instant Screenshot Capture",
            titleBn = "স্ক্রিনশট গ্রহণ করুন",
            category = FeatureCategory.DEVICE,
            iconEmoji = "📸",
            description = "Captures phone display screenshot instantly using native Accessibility Service.",
            promptTemplate = "Take screenshot",
            isDeviceAction = true,
            actionCommand = "screenshot",
            tags = listOf("screenshot", "capture", "screen", "save")
        ),
        SrsFeature(
            id = "dev_lock_device_sleep",
            title = "Lock Screen & Device Sleep",
            titleBn = "স্ক্রিন লক ও স্লিপ মোড",
            category = FeatureCategory.DEVICE,
            iconEmoji = "🔒",
            description = "Locks the device screen immediately without needing to press the physical power button.",
            promptTemplate = "Lock screen",
            isDeviceAction = true,
            actionCommand = "lock_screen",
            tags = listOf("lock", "sleep", "screen", "power")
        ),
        SrsFeature(
            id = "dev_clipboard_manager",
            title = "Smart Clipboard Manager",
            titleBn = "ক্লিপবোর্ড রিডার ও ক্লিনজার",
            category = FeatureCategory.DEVICE,
            iconEmoji = "📋",
            description = "Inspects current clipboard content, parses text, and provides one-tap clipboard sanitization.",
            promptTemplate = "Read clipboard",
            isDeviceAction = true,
            actionCommand = "read_clipboard",
            tags = listOf("clipboard", "copy", "paste", "sanitize")
        )
    )

    fun getCategoryFeatures(category: FeatureCategory): List<SrsFeature> {
        return allFeatures.filter { it.category == category }
    }

    fun searchFeatures(query: String): List<SrsFeature> {
        if (query.isBlank()) return allFeatures
        val q = query.trim().lowercase()
        return allFeatures.filter {
            it.title.lowercase().contains(q) ||
            it.titleBn.lowercase().contains(q) ||
            it.description.lowercase().contains(q) ||
            it.tags.any { tag -> tag.lowercase().contains(q) } ||
            it.category.title.lowercase().contains(q) ||
            it.category.titleBn.lowercase().contains(q)
        }
    }
}
