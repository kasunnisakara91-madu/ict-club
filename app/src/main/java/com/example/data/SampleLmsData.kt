package com.example.data

import com.example.model.*

object SampleLmsData {

    val courses: List<Course> = listOf(
        Course(
            id = "course-1",
            titleEn = "Android & iOS Mobile App Dev with VS Code & Cross-Platform",
            titleSi = "VS Code සමග Android සහ iOS ජංගම යෙදුම් සංවර්ධනය (මුල සිට සරලව)",
            subtitleEn = "Master cross-platform development for both Android & iOS from zero experience",
            subtitleSi = "කිසිදු පූර්ව දැනුමක් නොමැතිව Android සහ iOS සඳහා යෙදුම් නිර්මාණය කරන්න",
            instructorName = "Kasun Perera",
            instructorRoleEn = "Senior Mobile Architect & Tech Lead",
            instructorRoleSi = "ප්‍රධාන ජංගම යෙදුම් නිර්මාණ ශිල්පී",
            category = CourseCategory.MOBILE_DEV,
            level = CourseLevel.BEGINNER,
            rating = 4.9,
            reviewCount = 1420,
            enrolledCount = 5820,
            durationHours = 18.5,
            price = "Free",
            drawableResName = "img_course_coding",
            modules = listOf(
                Module(
                    id = "mod-1-1",
                    titleEn = "Module 1: Getting Started with VS Code & Mobile Dev",
                    titleSi = "මොඩියුලය 1: VS Code පිහිටුවීම සහ මූලික හැඳින්වීම",
                    order = 1,
                    lessons = listOf(
                        Lesson(
                            id = "les-1-1",
                            titleEn = "Lesson 1: What is Mobile App Development? (Android vs iOS)",
                            titleSi = "පාඩම 1: ජංගම යෙදුම් සංවර්ධනය යනු කුමක්ද? (Android සහ iOS වෙනස)",
                            durationMinutes = 12,
                            type = LessonType.VIDEO,
                            summaryEn = "Understand how Android (APK) and iOS (IPA) apps work under the hood, and how cross-platform frameworks allow writing one codebase for both platforms.",
                            summarySi = "Android සහ iOS යෙදුම් ක්‍රියා කරන ආකාරය සහ එකම කේතයකින් (Codebase) දෙකටම යෙදුම් සාදන ආකාරය තේරුම් ගන්න.",
                            contentNotesEn = """
                                Key Concepts:
                                1. Native vs Cross-Platform:
                                   - Native Android uses Kotlin & Jetpack Compose.
                                   - Native iOS uses Swift & SwiftUI.
                                   - Cross-platform solutions (Flutter / React Native) write once, run everywhere!
                                2. Tools required:
                                   - VS Code (Visual Studio Code): The world's most popular code editor.
                                   - Flutter SDK or Node.js / React Native.
                                   - Android Studio (for Android SDK & Emulators).
                                   - Xcode (for iOS simulators, required on macOS).
                            """.trimIndent(),
                            contentNotesSi = """
                                ප්‍රධාන සංකල්ප:
                                1. Native සහ Cross-Platform වෙනස:
                                   - Native Android සඳහා Kotlin & Jetpack Compose භාවිතා වේ.
                                   - Native iOS සඳහා Swift & SwiftUI භාවිතා වේ.
                                   - Flutter හෝ React Native මගින් එකම කේතයෙන් දෙකටම සෑදිය හැක!
                                2. අවශ්‍ය මූලික මෙවලම්:
                                   - VS Code: ලෝකයේ වඩාත්ම ජනප්‍රිය කේත සංස්කාරකය.
                                   - Flutter SDK හෝ React Native.
                                   - Android SDK (පරීක්ෂා කිරීම සඳහා).
                            """.trimIndent(),
                            resources = listOf(
                                LessonResource("Mobile_Dev_Roadmap.pdf", "2.4 MB", "PDF"),
                                LessonResource("VSCode_Shortcuts_CheatSheet.pdf", "1.1 MB", "PDF")
                            )
                        ),
                        Lesson(
                            id = "les-1-2",
                            titleEn = "Lesson 2: Setting up Visual Studio Code & Essential Extensions",
                            titleSi = "පාඩම 2: VS Code සකස් කිරීම සහ අත්‍යවශ්‍ය Extensions ස්ථාපනය",
                            durationMinutes = 18,
                            type = LessonType.VIDEO,
                            summaryEn = "Step-by-step setup guide for a beginner installing VS Code, Dart/Flutter extensions, Prettier, and Android emulators.",
                            summarySi = "මුල සිටම VS Code තුළ Extensions (Dart, Flutter, Bracket Pair) ස්ථාපනය කර සූදානම් කර ගන්නා ආකාරය.",
                            contentNotesEn = """
                                Step-by-Step Guide for Beginners:
                                1. Install VS Code from code.visualstudio.com.
                                2. Open Extensions tab (Ctrl+Shift+X or Cmd+Shift+X).
                                3. Install:
                                   - Flutter & Dart extension
                                   - Material Icon Theme
                                   - Prettier - Code Formatter
                                4. Open Terminal inside VS Code (Ctrl + `).
                            """.trimIndent(),
                            contentNotesSi = """
                                මුල සිට පියවර:
                                1. code.visualstudio.com වෙතින් VS Code බාගන්න.
                                2. Extensions (Ctrl+Shift+X) වෙත ගොස් Flutter, Dart ස්ථාපනය කරන්න.
                                3. Terminal එක විවෘත කරන්න (Ctrl + `).
                            """.trimIndent()
                        ),
                        Lesson(
                            id = "les-1-3",
                            titleEn = "Lesson 3: Your First Mobile UI Layout",
                            titleSi = "පාඩම 3: ඔබගේ පළමු ජංගම තිරය සහ Layout නිර්මාණය",
                            durationMinutes = 15,
                            type = LessonType.VIDEO,
                            summaryEn = "Creating buttons, text fields, cards, and scrollable lists using modern declarative UI.",
                            summarySi = "Buttons, Text fields, Cards සහ Scrollable lists නිර්මාණය කරන ආකාරය.",
                            contentNotesEn = "Declarative UI focuses on describing what the UI should look like for a given state.",
                            contentNotesSi = "තත්ත්වය (State) වෙනස් වන විට UI එක ස්වයංක්‍රීයව යාවත්කාලීන වන Declarative UI සංකල්පය."
                        )
                    )
                ),
                Module(
                    id = "mod-1-2",
                    titleEn = "Module 2: Building Real-World LMS Features",
                    titleSi = "මොඩියුලය 2: LMS යෙදුමක සත්‍ය අංග නිර්මාණය කිරීම",
                    order = 2,
                    lessons = listOf(
                        Lesson(
                            id = "les-1-4",
                            titleEn = "Lesson 4: User Authentication & Role Management",
                            titleSi = "පාඩම 4: පරිශීලක පිවිසුම (Login/Signup) සහ භූමිකාවන්",
                            durationMinutes = 22,
                            type = LessonType.VIDEO,
                            summaryEn = "Handling Student, Teacher, and Admin accounts securely.",
                            summarySi = "සිසුන් සහ ගුරුවරුන් සඳහා ආරක්ෂිත පිවිසුම් ක්‍රමවේදයක් සැකසීම.",
                            contentNotesEn = "Secure token storage and role-based access control.",
                            contentNotesSi = "ආරක්ෂිත Token ගබඩා කිරීම සහ පරිශීලක අවසර පාලනය."
                        ),
                        Lesson(
                            id = "les-1-5",
                            titleEn = "Lesson 5: Video Player Integration & Lesson Streaming",
                            titleSi = "පාඩම 5: වීඩියෝ ධාවකය සහ මාර්ගගත පාඩම් නැරඹීම",
                            durationMinutes = 25,
                            type = LessonType.VIDEO,
                            summaryEn = "Implementing full-screen video playback, playback speed, and progress checkpoints.",
                            summarySi = "වීඩියෝ ප්ලේයරයක් යෙදුමට එක් කර සිසුවා පාඩම නැරඹූ ප්‍රමාණය සටහන් කිරීම.",
                            contentNotesEn = "Best practices for adaptive bitrate streaming and media playback states.",
                            contentNotesSi = "වීඩියෝ වාදනය කිරීමේදී මතකය කළමනාකරණය කිරීම."
                        ),
                        Lesson(
                            id = "les-1-6",
                            titleEn = "Lesson 6: Quizzes, Progress Tracking & Completion Certificates",
                            titleSi = "පාඩම 6: ප්‍රශ්නාවලි, ප්‍රගති ලකුණු සහ සහතිකපත් ජනනය",
                            durationMinutes = 20,
                            type = LessonType.QUIZ,
                            summaryEn = "Interactive quiz engine with timer, auto-grading, and PDF certificates.",
                            summarySi = "ස්වයංක්‍රීය ලකුණු ලබාදෙන ප්‍රශ්නාවලියක් සහ ඩිජිටල් සහතිකපතක් ලබා දීම.",
                            contentNotesEn = "Calculating course completion percentage and dynamic certificate generator.",
                            contentNotesSi = "පාඨමාලා අවසන් ප්‍රතිශතය ගණනය කිරීම."
                        )
                    )
                )
            ),
            learningPointsEn = listOf(
                "How to build apps for both Android & iOS from single codebase",
                "How to use Visual Studio Code effectively as a complete beginner",
                "Designing beautiful modern responsive mobile interfaces",
                "Implementing authentication, video streaming, and quizzes",
                "Publishing to Google Play Store and Apple App Store"
            ),
            learningPointsSi = listOf(
                "එකම කේතයකින් Android සහ iOS දෙකටම යෙදුම් නිර්මාණය කරන ආකාරය",
                "ආරම්භකයෙකු ලෙස VS Code නිවැරදිව භාවිත කරන ආකාරය",
                "නවීන සහ ආකර්ෂණීය ජංගම අතුරුමුහුණත් සැකසීම",
                "වීඩියෝ නැරඹීම, ප්‍රශ්නාවලි සහ ප්‍රගති ලුහුබැඳීම එක් කිරීම",
                "Google Play Store සහ Apple App Store වෙත මුදාහැරීම"
            ),
            requirementsEn = listOf(
                "A computer (Windows, Mac, or Linux)",
                "No prior programming knowledge required — starts from level 0",
                "Curiosity and enthusiasm to build your own apps"
            ),
            requirementsSi = listOf(
                "පරිගණකයක් (Windows, Mac, හෝ Linux)",
                "පූර්ව කේතකරණ දැනුමක් අවශ්‍ය නොවේ — මුල සිටම ආරම්භ වේ",
                "තමන්ගේම App එකක් සෑදීමට ඇති උනන්දුව"
            )
        ),
        Course(
            id = "course-2",
            titleEn = "Modern UI/UX Design System for Educational Apps",
            titleSi = "අධ්‍යාපනික යෙදුම් සඳහා නවීන UI/UX අතුරුමුහුණත් නිර්මාණය",
            subtitleEn = "Design intuitive, beautiful interfaces following Material 3 & Apple HIG",
            subtitleSi = "Material 3 සහ Apple Human Interface Guidelines අනුව ආකර්ෂණීය නිර්මාණ",
            instructorName = "Dilani Senanayake",
            instructorRoleEn = "Principal Product Designer",
            instructorRoleSi = "ප්‍රධාන නිෂ්පාදන නිර්මාණ ශිල්පිනී",
            category = CourseCategory.UI_UX,
            level = CourseLevel.BEGINNER,
            rating = 4.8,
            reviewCount = 980,
            enrolledCount = 3450,
            durationHours = 12.0,
            price = "Free",
            drawableResName = "img_course_design",
            modules = listOf(
                Module(
                    id = "mod-2-1",
                    titleEn = "Module 1: Principles of Student-Centered Design",
                    titleSi = "මොඩියුලය 1: ශිෂ්‍ය කේන්ද්‍රීය නිර්මාණකරණයේ මූලධර්ම",
                    order = 1,
                    lessons = listOf(
                        Lesson(
                            id = "les-2-1",
                            titleEn = "Color Psychology & Visual Hierarchy in E-Learning",
                            titleSi = "වර්ණ භාවිතය සහ දෘශ්‍ය ධුරාවලිය",
                            durationMinutes = 14,
                            type = LessonType.VIDEO,
                            summaryEn = "Choosing readable palettes, dark mode balance, and accessible contrast ratios.",
                            summarySi = "කියවීමට පහසු වර්ණ සහ ආකර්ෂණීය Dark Mode සැකසීම.",
                            contentNotesEn = "Accessibility contrast requirements (WCAG 2.1 AA standard).",
                            contentNotesSi = "පහසුවෙන් කියවිය හැකි වර්ණ සංයෝජනය."
                        ),
                        Lesson(
                            id = "les-2-2",
                            titleEn = "Mobile Navigation Patterns & Bottom Navigation Bars",
                            titleSi = "ජංගම යෙදුම් සංචාලන ක්‍රම සහ Bottom Bar නිර්මාණය",
                            durationMinutes = 16,
                            type = LessonType.VIDEO,
                            summaryEn = "Ergonomic thumb zones, navigation rails for tablets, and edge-to-edge insets.",
                            summarySi = "එක් අතකින් පහසුවෙන් ක්‍රියා කළ හැකි අතුරුමුහුණත් සැකසීම.",
                            contentNotesEn = "Applying safe drawing insets and thumb-reach zones.",
                            contentNotesSi = "ජංගම තිරවල පහසු බොත්තම් ස්ථානගත කිරීම."
                        )
                    )
                )
            ),
            learningPointsEn = listOf(
                "Create high-converting e-learning user interfaces",
                "Master typography, 8dp grids, and touch target accessibility",
                "Build reusable design component libraries",
                "Export production-ready assets for engineers"
            ),
            learningPointsSi = listOf(
                "ඉගෙනීමට පහසු සහ සිත්ගන්නාසුලු අතුරුමුහුණත් නිර්මාණය",
                "අකුරු මෝස්තර, 8dp Grid පද්ධතිය සහ Touch Targets",
                "නැවත භාවිත කළ හැකි Components සැකසීම",
                "ඉංජිනේරුවන් සඳහා Design Assets සකස් කිරීම"
            ),
            requirementsEn = listOf(
                "Basic computer literacy",
                "A free Figma account"
            ),
            requirementsSi = listOf(
                "මූලික පරිගණක දැනුම",
                "නොමිලේ ලබාගත හැකි Figma ගිණුමක්"
            )
        ),
        Course(
            id = "course-3",
            titleEn = "Full-Stack Web Development Bootcamp (HTML, CSS, JS, Node)",
            titleSi = "සම්පූර්ණ වෙබ් සංවර්ධන පාඨමාලාව (HTML, CSS, JS, Node.js)",
            subtitleEn = "Build responsive web portals, teacher dashboards, and REST APIs",
            subtitleSi = "ප්‍රතිචාරාත්මක වෙබ් අඩවි, උපකරණ පුවරු සහ APIs තනන්න",
            instructorName = "Nuwan Bandara",
            instructorRoleEn = "Full-Stack Software Engineer",
            instructorRoleSi = "ප්‍රවීණ වෙබ් මෘදුකාංග ඉංජිනේරු",
            category = CourseCategory.WEB_DEV,
            level = CourseLevel.INTERMEDIATE,
            rating = 4.9,
            reviewCount = 1890,
            enrolledCount = 7600,
            durationHours = 24.0,
            price = "$19.99",
            drawableResName = "img_lms_hero",
            modules = listOf(
                Module(
                    id = "mod-3-1",
                    titleEn = "Module 1: Modern JavaScript & Frontend Frameworks",
                    titleSi = "මොඩියුලය 1: නවීන JavaScript සහ Frontend තාක්ෂණ",
                    order = 1,
                    lessons = listOf(
                        Lesson(
                            id = "les-3-1",
                            titleEn = "ES6+ Fundamentals for Modern Web & Mobile",
                            titleSi = "නවීන JavaScript මූලික සංකල්ප",
                            durationMinutes = 20,
                            type = LessonType.VIDEO,
                            summaryEn = "Async/await, Promises, Arrow functions, and Modules.",
                            summarySi = "Async/await, Promises සහ Modules පැහැදිලි කිරීම.",
                            contentNotesEn = "JavaScript execution model and asynchronous event loop.",
                            contentNotesSi = "JavaScript ක්‍රියාකාරී ආකෘතිය."
                        )
                    )
                )
            ),
            learningPointsEn = listOf(
                "Build modern responsive web applications",
                "Create server-side REST APIs with Node.js and Express",
                "Database modeling with SQL and MongoDB",
                "Deploying apps to cloud servers"
            ),
            learningPointsSi = listOf(
                "නවීන වෙබ් යෙදුම් නිර්මාණය",
                "Node.js මගින් REST APIs සැකසීම",
                "දත්ත සමුදාය (Database) කළමනාකරණය",
                "Cloud සේවාදායකයන් වෙත යෙදුම් එක් කිරීම"
            ),
            requirementsEn = listOf("Basic HTML & CSS knowledge helpful but not strictly required"),
            requirementsSi = listOf("මූලික පරිගණක දැනුම ප්‍රමාණවත් වේ")
        ),
        Course(
            id = "course-4",
            titleEn = "Data Science & Python for Absolute Beginners",
            titleSi = "ආරම්භකයින් සඳහා Python සහ දත්ත විද්‍යාව",
            subtitleEn = "Analyze datasets, build predictive charts, and automate tasks",
            subtitleSi = "දත්ත විශ්ලේෂණය සහ ප්‍රස්ථාර මගින් තීරණ ගැනීම",
            instructorName = "Chathura Dias",
            instructorRoleEn = "Lead Data Scientist",
            instructorRoleSi = "දත්ත විද්‍යා ප්‍රධානී",
            category = CourseCategory.DATA_SCIENCE,
            level = CourseLevel.BEGINNER,
            rating = 4.7,
            reviewCount = 840,
            enrolledCount = 2900,
            durationHours = 14.0,
            price = "Free",
            drawableResName = "img_course_coding",
            modules = listOf(
                Module(
                    id = "mod-4-1",
                    titleEn = "Module 1: Python Basics & Data Structures",
                    titleSi = "මොඩියුලය 1: Python මූලික කරුණු",
                    order = 1,
                    lessons = listOf(
                        Lesson(
                            id = "les-4-1",
                            titleEn = "Variables, Lists, and Loops in Python",
                            titleSi = "Python හි Variables සහ Loops",
                            durationMinutes = 15,
                            type = LessonType.VIDEO,
                            summaryEn = "Mastering basic scripting and data manipulation.",
                            summarySi = "Python කේත ලිවීමේ මූලික පදනම.",
                            contentNotesEn = "Data types and control flow.",
                            contentNotesSi = "දත්ත වර්ග සහ පාලන ව්‍යුහයන්."
                        )
                    )
                )
            ),
            learningPointsEn = listOf(
                "Write clean, idiomatic Python code",
                "Data manipulation with Pandas and NumPy",
                "Create stunning visual charts with Matplotlib & Seaborn"
            ),
            learningPointsSi = listOf(
                "පිරිසිදු Python කේත ලිවීම",
                "දත්ත විශ්ලේෂණය සහ සැකසීම",
                "ප්‍රස්ථාර සහ දෘශ්‍යකරණය"
            ),
            requirementsEn = listOf("Any computer with Python 3 installed"),
            requirementsSi = listOf("Python ස්ථාපනය කළ පරිගණකයක්")
        )
    )

    val sampleQuizzes: List<Quiz> = listOf(
        Quiz(
            id = "quiz-1",
            courseId = "course-1",
            titleEn = "Mobile Dev & VS Code Mastery Quiz",
            titleSi = "ජංගම යෙදුම් සහ VS Code දැනුම පරීක්ෂණ ප්‍රශ්නාවලිය",
            durationMinutes = 10,
            passingScorePercent = 70,
            questions = listOf(
                QuizQuestion(
                    id = "q-1",
                    questionEn = "What is the primary advantage of cross-platform frameworks like Flutter or React Native?",
                    questionSi = "Flutter හෝ React Native වැනි Cross-Platform තාක්ෂණ භාවිතයේ ප්‍රධාන වාසිය කුමක්ද?",
                    optionsEn = listOf(
                        "You can build both Android and iOS apps using a single codebase",
                        "You don't need any computer to write code",
                        "The app only works inside a web browser",
                        "It requires double the amount of code"
                    ),
                    optionsSi = listOf(
                        "එකම කේතයකින් (Codebase) Android සහ iOS දෙකටම යෙදුම් සෑදිය හැකිවීම",
                        "කේත ලිවීමට පරිගණකයක් අවශ්‍ය නොවීම",
                        "යෙදුම වෙබ් බ්‍රව්සරයක පමණක් ක්‍රියාත්මක වීම",
                        "දෙගුණයක කේත ලිවීමට සිදුවීම"
                    ),
                    correctOptionIndex = 0,
                    explanationEn = "Cross-platform frameworks compile to both Android (APK) and iOS (IPA) from a shared single codebase, saving huge development time!",
                    explanationSi = "එකම කේතයෙන් Android සහ iOS යන දෙකටම සහය දැක්වීම නිසා කාලය හා වියදම විශාල වශයෙන් ඉතිරි වේ!"
                ),
                QuizQuestion(
                    id = "q-2",
                    questionEn = "Which shortcut opens the integrated Terminal in Visual Studio Code?",
                    questionSi = "Visual Studio Code තුළ Integrated Terminal එක විවෘත කිරීමට භාවිත කරන Shortcut එක කුමක්ද?",
                    optionsEn = listOf(
                        "Ctrl + Shift + T",
                        "Ctrl + ` (Backtick) or Cmd + `",
                        "Alt + F4",
                        "Ctrl + Delete"
                    ),
                    optionsSi = listOf(
                        "Ctrl + Shift + T",
                        "Ctrl + ` (Backtick) හෝ Cmd + `",
                        "Alt + F4",
                        "Ctrl + Delete"
                    ),
                    correctOptionIndex = 1,
                    explanationEn = "Ctrl + ` (Backtick) toggles the bottom integrated terminal panel directly inside VS Code.",
                    explanationSi = "Ctrl + ` (Backtick) මඟින් VS Code තුළම Terminal එක ක්ෂණිකව විවෘත කරගත හැක."
                ),
                QuizQuestion(
                    id = "q-3",
                    questionEn = "What is an LMS application primarily used for?",
                    questionSi = "LMS (Learning Management System) යෙදුමක ප්‍රධාන කාර්යය කුමක්ද?",
                    optionsEn = listOf(
                        "Photo editing and 3D animation",
                        "Managing, delivering, and tracking educational courses and student progress",
                        "Playing high-end 3D video games",
                        "Operating system file management"
                    ),
                    optionsSi = listOf(
                        "ඡායාරූප සංස්කරණය කිරීම",
                        "අධ්‍යාපනික පාඨමාලා කළමනාකරණය, ඉගැන්වීම සහ සිසුන්ගේ ප්‍රගතිය ලුහුබැඳීම",
                        "වීඩියෝ ක්‍රීඩා ධාවනය කිරීම",
                        "ගොනු කළමනාකරණය කිරීම"
                    ),
                    correctOptionIndex = 1,
                    explanationEn = "An LMS (Learning Management System) administers courses, lessons, quizzes, grades, and student learning paths.",
                    explanationSi = "LMS යනු පාඨමාලා, වීඩියෝ පාඩම්, ප්‍රශ්නාවලි සහ සිසුන්ගේ ප්‍රගතිය කළමනාකරණය කරන පද්ධතියකි."
                ),
                QuizQuestion(
                    id = "q-4",
                    questionEn = "To test an iOS app on a real iPhone or Simulator, what operating system is officially required for building with Xcode?",
                    questionSi = "Xcode මඟින් iOS යෙදුමක් Build කර Simulator හෝ iPhone එකක පරීක්ෂා කිරීමට නිල වශයෙන් අවශ්‍ය මෙහෙයුම් පද්ධතිය කුමක්ද?",
                    optionsEn = listOf(
                        "macOS (Apple Mac computer)",
                        "Windows 98",
                        "Ubuntu Linux command line only",
                        "Android OS"
                    ),
                    optionsSi = listOf(
                        "macOS (Apple Mac පරිගණකයක්)",
                        "Windows 98",
                        "Ubuntu Linux පමණි",
                        "Android OS"
                    ),
                    correctOptionIndex = 0,
                    explanationEn = "While cross-platform code can be written on Windows in VS Code, compiling and publishing the iOS IPA requires macOS and Xcode (or cloud build services like Codemagic / GitHub Actions).",
                    explanationSi = "Windows මත VS Code මගින් කේත ලිවිය හැකි වුවද, iOS යෙදුම Build කිරීමට macOS (හෝ Cloud CI/CD) අවශ්‍ය වේ."
                )
            )
        )
    )

    val sampleAssignments: List<Assignment> = listOf(
        Assignment(
            id = "assign-1",
            courseId = "course-1",
            courseTitle = "Android & iOS Mobile App Dev with VS Code",
            titleEn = "Assignment 1: Design the LMS Course List Wireframe",
            titleSi = "පැවරුම 1: LMS පාඨමාලා ලැයිස්තු අතුරුමුහුණත නිර්මාණය",
            descriptionEn = "Create a mobile screen layout with at least 3 course cards, progress indicators, and an enroll button.",
            descriptionSi = "පාඨමාලා කාඩ්පත් 3ක්, ප්‍රගති දර්ශක සහ ලියාපදිංචි වීමේ බොත්තමක් සහිත තිරයක් නිර්මාණය කරන්න.",
            dueDate = "Next Sunday, 11:59 PM",
            maxScore = 100,
            status = AssignmentStatus.PENDING
        ),
        Assignment(
            id = "assign-2",
            courseId = "course-1",
            courseTitle = "Android & iOS Mobile App Dev with VS Code",
            titleEn = "Assignment 2: Setup VS Code & Run Hello World",
            titleSi = "පැවරුම 2: VS Code සකසා Hello World ධාවනය කිරීම",
            descriptionEn = "Submit a screenshot of your VS Code workspace running the initial mobile template.",
            descriptionSi = "ඔබගේ VS Code පරිසරයේ සාර්ථකව යෙදුම ක්‍රියාත්මක කළ තිර ඡායාරූපයක් ඉදිරිපත් කරන්න.",
            dueDate = "Completed",
            maxScore = 100,
            status = AssignmentStatus.GRADED,
            userSubmission = "Workspace configured successfully with Flutter and Android SDK.",
            grade = 98,
            feedback = "Excellent setup! All extensions and environment variables configured properly."
        )
    )

    val sampleAnnouncements: List<Announcement> = listOf(
        Announcement(
            id = "ann-1",
            courseTitle = "Android & iOS Mobile App Dev",
            author = "Kasun Perera (Instructor)",
            date = "Today",
            titleEn = "Welcome to EduPulse LMS Mobile App! 🚀",
            titleSi = "EduPulse LMS ජංගම යෙදුම වෙත සාදරයෙන් පිළිගනිමු! 🚀",
            contentEn = "Welcome everyone! In this app, you can explore courses, watch video lessons, take interactive quizzes, and track your study streak. For beginners who asked how to build Android & iOS apps with VS Code, check our comprehensive Guide tab in the Profile screen!",
            contentSi = "සියලු දෙනාම සාදරයෙන් පිළිගනිමු! මෙම යෙදුම තුළින් ඔබට පාඨමාලා හැදෑරීමට, වීඩියෝ පාඩම් නැරඹීමට, ප්‍රශ්නාවලිවලට මුහුණ දීමට සහ සහතිකපත් ලබා ගැනීමට හැක. ආරම්භකයින් සඳහා VS Code මගින් Android සහ iOS යෙදුම් සාදන ආකාරය Profile හි ඇති Guide එකෙන් කියවිය හැක!"
        ),
        Announcement(
            id = "ann-2",
            courseTitle = "General Campus",
            author = "EduPulse Academic Office",
            date = "Yesterday",
            titleEn = "Study Streak Rewards & New Certificates",
            titleSi = "දිනපතා ඉගෙනුම් දිරිගැන්වීම් සහ නව සහතිකපත්",
            contentEn = "Maintain a 7-day learning streak to earn the Master Learner badge and unlock downloadable verified digital certificates upon course completion.",
            contentSi = "පාඨමාලා සාර්ථකව අවසන් කර 70% ට වඩා ලකුණු ලබාගෙන ඔබගේ නිල ඩිජිටල් සහතිකපත බාගත කරගන්න."
        )
    )

    val sampleCertificates: List<Certificate> = listOf(
        Certificate(
            id = "cert-101",
            courseId = "course-1",
            courseTitleEn = "Android & iOS Mobile App Dev with VS Code",
            courseTitleSi = "VS Code සමග Android සහ iOS ජංගම යෙදුම් සංවර්ධනය",
            studentName = "Damith Madusanka",
            issueDate = "October 2026",
            gradePercent = 95,
            verificationCode = "EP-LMS-2026-8941"
        )
    )

    val sampleUploadedFiles: List<UploadedFile> = listOf(
        UploadedFile(
            id = "file-1",
            title = "Mobile App Architecture & Setup Guide 2026",
            fileName = "Mobile_Dev_Architecture_2026.pdf",
            fileSize = "3.8 MB",
            fileType = "PDF",
            uploaderRole = UserRole.TEACHER,
            uploaderName = "Kasun Perera (Teacher)",
            courseId = "course-1",
            courseTitle = "Android & iOS Mobile App Dev",
            uploadTimestamp = "Today, 09:30 AM",
            isDownloadedLocally = true
        ),
        UploadedFile(
            id = "file-2",
            title = "Flutter & Dart Starter Code Package",
            fileName = "Flutter_Starter_Template_v2.zip",
            fileSize = "14.2 MB",
            fileType = "ZIP",
            uploaderRole = UserRole.TEACHER,
            uploaderName = "Kasun Perera (Teacher)",
            courseId = "course-1",
            courseTitle = "Android & iOS Mobile App Dev",
            uploadTimestamp = "Yesterday",
            isDownloadedLocally = false
        ),
        UploadedFile(
            id = "file-3",
            title = "Figma Mobile Wireframe Component System",
            fileName = "LMS_Design_Tokens.fig",
            fileSize = "8.1 MB",
            fileType = "FIG",
            uploaderRole = UserRole.TEACHER,
            uploaderName = "Dilani Senanayake (Teacher)",
            courseId = "course-2",
            courseTitle = "Modern UI/UX Design System",
            uploadTimestamp = "2 days ago",
            isDownloadedLocally = false
        ),
        UploadedFile(
            id = "file-4",
            title = "LMS Assignment 1 Project Submission",
            fileName = "Damith_Madusanka_Assignment1.zip",
            fileSize = "5.6 MB",
            fileType = "ZIP",
            uploaderRole = UserRole.STUDENT,
            uploaderName = "Damith Madusanka (Student)",
            courseId = "course-1",
            courseTitle = "Android & iOS Mobile App Dev",
            uploadTimestamp = "Today, 11:15 AM",
            isDownloadedLocally = true
        ),
        UploadedFile(
            id = "file-5",
            title = "VS Code Terminal Configuration Screenshot",
            fileName = "vscode_terminal_proof.png",
            fileSize = "1.2 MB",
            fileType = "PNG",
            uploaderRole = UserRole.STUDENT,
            uploaderName = "Damith Madusanka (Student)",
            courseId = "course-1",
            courseTitle = "Android & iOS Mobile App Dev",
            uploadTimestamp = "Yesterday",
            isDownloadedLocally = true
        )
    )

    val sampleStudentSubmissions: List<StudentSubmission> = listOf(
        StudentSubmission(
            id = "sub-1",
            assignmentId = "assign-1",
            assignmentTitle = "Assignment 1: Design the LMS Course List Wireframe",
            studentName = "Damith Madusanka",
            studentEmail = "damithmadusanka179@gmail.com",
            notes = "Completed all 3 mobile screen wireframes with cards, bottom navigation, and course progress tracking.",
            attachedFileName = "Damith_Madusanka_Assignment1.zip",
            attachedFileSize = "5.6 MB",
            submittedAt = "Today, 11:15 AM",
            isGraded = false
        ),
        StudentSubmission(
            id = "sub-2",
            assignmentId = "assign-2",
            assignmentTitle = "Assignment 2: Setup VS Code & Run Hello World",
            studentName = "Damith Madusanka",
            studentEmail = "damithmadusanka179@gmail.com",
            notes = "Workspace configured successfully with Flutter and Android SDK. Terminal screenshot attached.",
            attachedFileName = "vscode_terminal_proof.png",
            attachedFileSize = "1.2 MB",
            submittedAt = "Yesterday",
            grade = 98,
            feedback = "Excellent setup! All extensions and environment variables configured properly.",
            isGraded = true
        ),
        StudentSubmission(
            id = "sub-3",
            assignmentId = "assign-1",
            assignmentTitle = "Assignment 1: Design the LMS Course List Wireframe",
            studentName = "Nimali Fernando",
            studentEmail = "nimali.f@gmail.com",
            notes = "Created mobile UI mockups in Figma and exported PDF preview.",
            attachedFileName = "Nimali_LMS_Screens.pdf",
            attachedFileSize = "4.1 MB",
            submittedAt = "Yesterday",
            isGraded = false
        )
    )
}
