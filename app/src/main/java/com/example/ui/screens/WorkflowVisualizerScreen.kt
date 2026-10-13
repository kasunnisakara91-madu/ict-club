package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.ui.theme.*
import com.example.viewmodel.LmsUiState

enum class WorkflowTrack {
    TEACHER,
    STUDENT,
    DATA_FLOW,
    DEV_ROADMAP
}

data class WorkflowStepItem(
    val stepNumber: Int,
    val titleEn: String,
    val titleSi: String,
    val descriptionEn: String,
    val descriptionSi: String,
    val iconEmoji: String,
    val technicalNoteEn: String,
    val technicalNoteSi: String
)

@Composable
fun WorkflowVisualizerScreen(
    uiState: LmsUiState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = uiState.language
    val isSi = lang == AppLanguage.SINHALA
    var activeTrack by remember { mutableStateOf(WorkflowTrack.TEACHER) }

    val teacherWorkflow = listOf(
        WorkflowStepItem(
            stepNumber = 1,
            titleEn = "Instructor Account & Verification",
            titleSi = "ගුරු ගිණුම පිහිටුවීම සහ තහවුරු කිරීම",
            descriptionEn = "Teacher registers, submits qualifications, and gets instructor badge credentials.",
            descriptionSi = "ගුරුවරයා ලියාපදිංචි වී තම සුදුසුකම් ඇතුළත් කර ගුරු ගිණුම සක්‍රිය කරගැනීම.",
            iconEmoji = "👨‍🏫",
            technicalNoteEn = "Saved in Cloud DB 'users' collection with role='TEACHER'.",
            technicalNoteSi = "Cloud Database හි users collection එකේ role='TEACHER' ලෙස සුරැකේ."
        ),
        WorkflowStepItem(
            stepNumber = 2,
            titleEn = "Course & Curriculum Planning",
            titleSi = "පාඨමාලාව සහ මොඩියුල සැලසුම් කිරීම",
            descriptionEn = "Teacher defines course outcomes, requirements, module syllabus, and lesson topics.",
            descriptionSi = "පාඨමාලාවේ නම, විස්තරය, මොඩියුල සහ පාඩම් මාලාව පිළිවෙළට සකස් කිරීම.",
            iconEmoji = "📚",
            technicalNoteEn = "Course JSON document created with nested modules and lessons schema.",
            technicalNoteSi = "පාඨමාලාවේ මොඩියුල ව්‍යුහය JSON Document එකක් ලෙස Database හි නිර්මාණය වේ."
        ),
        WorkflowStepItem(
            stepNumber = 3,
            titleEn = "Upload Materials & Videos to Cloud",
            titleSi = "පාඩම් සටහන් සහ වීඩියෝ Cloud වෙත Upload කිරීම",
            descriptionEn = "Teacher uploads lecture slides, PDF hand-outs, video streams, and source code files.",
            descriptionSi = "දේශන වීඩියෝ, PDF සටහන් සහ ප්‍රායෝගික කේත Cloud Storage වෙත Upload කිරීම.",
            iconEmoji = "☁️",
            technicalNoteEn = "Binary streams to Cloud Storage (AWS S3/Firebase). Secure download URL stored in DB.",
            technicalNoteSi = "ගොනු Cloud Storage Buckets වල තැන්පත් වී ඒවායේ Download URLs දත්ත සමුදායට එක් වේ."
        ),
        WorkflowStepItem(
            stepNumber = 4,
            titleEn = "Publish Quizzes & Assignments",
            titleSi = "ප්‍රශ්නාවලි සහ පැවරුම් සකස් කර නිකුත් කිරීම",
            descriptionEn = "Creates multiple-choice tests with passing thresholds and homework assignments with due dates.",
            descriptionSi = "සමත් ලකුණු සහිත ප්‍රශ්නාවලි (Quizzes) සහ නියමිත දින සහිත පැවරුම් ශිෂ්‍යයන්ට ලබාදීම.",
            iconEmoji = "📝",
            technicalNoteEn = "Quizzes and assignments collections indexed by courseId for instant querying.",
            technicalNoteSi = "courseId මගින් සම්බන්ධ වන Quizzes සහ Assignments ලැයිස්තු සකස් වේ."
        ),
        WorkflowStepItem(
            stepNumber = 5,
            titleEn = "Review & Grade Student Submissions",
            titleSi = "ශිෂ්‍ය වැඩ පරීක්ෂා කර ලකුණු සහ ප්‍රතිචාර ලබාදීම",
            descriptionEn = "Teacher inspects student-uploaded project ZIPs and notes, assigns marks, and writes feedback.",
            descriptionSi = "සිසුන් භාරදුන් පැවරුම් සහ ගොනු බාගත කර පරීක්ෂා කර ලකුණු සහ ගුරු උපදෙස් ලබාදීම.",
            iconEmoji = "✅",
            technicalNoteEn = "Teacher updates submission document with grade, feedback, and status='GRADED'.",
            technicalNoteSi = "ශිෂ්‍යයාගේ submission ලේඛනයට ලකුණු, feedback සහ status='GRADED' ලෙස යාවත්කාලීන වේ."
        ),
        WorkflowStepItem(
            stepNumber = 6,
            titleEn = "Issue Verified Certificates",
            titleSi = "නිල ඩිජිටල් සහතිකපත් නිකුත් කිරීම",
            descriptionEn = "System automatically generates a cryptographically verifiable PDF certificate for passing students.",
            descriptionSi = "පාඨමාලාව සාර්ථකව අවසන් කළ සිසුන්ට QR කේත සහිත ඩිජිටල් සහතිකපත් නිකුත් වේ.",
            iconEmoji = "🏆",
            technicalNoteEn = "Unique verification hash generated and recorded for employer authenticity check.",
            technicalNoteSi = "අනන්‍ය සහතික අංකයක් (Verification Code) ජනනය වී සුරැකේ."
        )
    )

    val studentWorkflow = listOf(
        WorkflowStepItem(
            stepNumber = 1,
            titleEn = "Registration & Sign-In",
            titleSi = "ශිෂ්‍ය ලියාපදිංචිය සහ පිවිසුම",
            descriptionEn = "Student creates an account, selects interests (Mobile Dev, UI/UX), and sets learning goals.",
            descriptionSi = "ශිෂ්‍යයා තමන්ගේ ගිණුම සාදා තමන් කැමති විෂය ක්ෂේත්‍ර තෝරාගැනීම.",
            iconEmoji = "🎓",
            technicalNoteEn = "Secure JWT / Firebase Auth session token stored securely in device storage.",
            technicalNoteSi = "ආරක්ෂිත Login Token එක දුරකථනයේ සුරැකේ."
        ),
        WorkflowStepItem(
            stepNumber = 2,
            titleEn = "Course Discovery & Enrollment",
            titleSi = "පාඨමාලා සොයා බලා ලියාපදිංචි වීම",
            descriptionEn = "Browses catalog, reads syllabus, reviews tutor credentials, and clicks 'Enroll for Free'.",
            descriptionSi = "පාඨමාලා නාමාවලියෙන් අවශ්‍ය පාඨමාලාව තෝරා නොමිලේ ලියාපදිංචි වීම.",
            iconEmoji = "🔍",
            technicalNoteEn = "Student userId appended to course.enrolledStudents list in Cloud DB.",
            technicalNoteSi = "ශිෂ්‍යයාගේ ID එක පාඨමාලාවේ ලියාපදිංචි ලැයිස්තුවට එක් වේ."
        ),
        WorkflowStepItem(
            stepNumber = 3,
            titleEn = "Watch Lessons & Download Offline",
            titleSi = "පාඩම් නැරඹීම සහ Offline බාගත කරගැනීම",
            descriptionEn = "Streams interactive video lessons, reads notes, and downloads lecture PDFs for offline study.",
            descriptionSi = "වීඩියෝ පාඩම් බැලීම, සටහන් කියවීම සහ අන්තර්ජාලය නැතිවිට බැලීමට PDFs බාගත කිරීම.",
            iconEmoji = "📲",
            technicalNoteEn = "Player tracks watch progress seconds; downloads saved to internal app cache.",
            technicalNoteSi = "නැරඹූ කාලය Cloud එකට Sync වන අතර, Download කළ ගොනු දුරකථනයේ Cache එකේ සුරැකේ."
        ),
        WorkflowStepItem(
            stepNumber = 4,
            titleEn = "Take Interactive Quizzes",
            titleSi = "දැනුම පරීක්ෂණ (Quizzes) වලට පිළිතුරු සැපයීම",
            descriptionEn = "Solves multiple-choice questions, receives instant auto-scoring, and reviews answer explanations.",
            descriptionSi = "ප්‍රශ්නාවලිවලට මුහුණ දී ක්ෂණික ලකුණු ප්‍රතිශතය සහ විවරණ ලබාගැනීම.",
            iconEmoji = "🧠",
            technicalNoteEn = "Client calculates score, sends attempt record to Cloud DB, validates 70%+ pass.",
            technicalNoteSi = "ලකුණු ප්‍රතිශතය ගණනය වී සමත් ලකුණු Cloud එකට වාර්තා වේ."
        ),
        WorkflowStepItem(
            stepNumber = 5,
            titleEn = "Submit Assignment Files",
            titleSi = "පැවරුම් පිළිතුරු සහ Files Upload කිරීම",
            descriptionEn = "Writes solution notes, attaches project ZIP archive, and submits for teacher review.",
            descriptionSi = "පැවරුම් පිළිතුරු ලියා, Project ZIP/PDF ගොනුව Upload කර ගුරුවරයාට භාරදීම.",
            iconEmoji = "📤",
            technicalNoteEn = "Attached file uploaded to Cloud Storage bucket; submission linked to studentId.",
            technicalNoteSi = "ගොනුව Cloud Storage වෙත Upload වී ගුරුවරයාගේ Review පුවරුවට වැටේ."
        ),
        WorkflowStepItem(
            stepNumber = 6,
            titleEn = "Receive Feedback & Verified Certificate",
            titleSi = "ගුරු ප්‍රතිචාර සහ නිල සහතිකපත ලබාගැනීම",
            descriptionEn = "Reads teacher remarks, celebrates completion, and views/downloads verifiable digital certificate.",
            descriptionSi = "ගුරුවරයාගේ ප්‍රතිචාර කියවා, පාඨමාලාව අවසන් කර නිල සහතිකපත ලබාගැනීම.",
            iconEmoji = "📜",
            technicalNoteEn = "Certificate displayed in Profile screen with verification code and grade.",
            technicalNoteSi = "අනන්‍ය කේතය සහිත සහතිකපත Profile තුළින් බලාගත හැක."
        )
    )

    val dataFlowSteps = listOf(
        WorkflowStepItem(
            stepNumber = 1,
            titleEn = "Client Action (Phone App)",
            titleSi = "ජංගම යෙදුම (Mobile App) තුළ ක්‍රියාව",
            descriptionEn = "Teacher clicks 'Upload PDF' or student clicks 'Submit Assignment' in the Flutter / Compose UI.",
            descriptionSi = "ගුරුවරයා හෝ ශිෂ්‍යයා App එක තුළ Upload හෝ Download බොත්තමක් ක්ලික් කරයි.",
            iconEmoji = "📱",
            technicalNoteEn = "Mobile UI triggers background Coroutine / async task.",
            technicalNoteSi = "ජංගම යෙදුම මඟින් Background Coroutine එකක් ක්‍රියාත්මක කරයි."
        ),
        WorkflowStepItem(
            stepNumber = 2,
            titleEn = "Cloud Storage Upload Stream",
            titleSi = "Cloud Storage වෙත ගොනුව ගලායාම",
            descriptionEn = "The heavy binary file (5MB - 50MB) streams directly to Firebase Storage or AWS S3 bucket.",
            descriptionSi = "විශාල ගොනුව සෘජුවම Cloud Storage Bucket එකක් (AWS S3 / Firebase) වෙත උඩුගත වේ.",
            iconEmoji = "☁️",
            technicalNoteEn = "Chunked multipart upload with progress listener and MD5 hash verification.",
            technicalNoteSi = "ආරක්ෂිතව කොටස් වශයෙන් Upload වී තහවුරු කෙරේ."
        ),
        WorkflowStepItem(
            stepNumber = 3,
            titleEn = "Database Metadata Creation",
            titleSi = "දත්ත සමුදායේ (Database) වාර්තාවක් ලිවීම",
            descriptionEn = "Cloud Storage returns a public/signed URL. The app writes a tiny JSON document to Firestore DB.",
            descriptionSi = "Cloud Storage එකෙන් ලැබෙන URL එක සහ ගොනු විස්තරය Database (Firestore) එකේ සටහන් වේ.",
            iconEmoji = "🗄️",
            technicalNoteEn = "Writes: {fileName, fileSize, downloadUrl, uploaderId, courseId, timestamp}.",
            technicalNoteSi = "ගොනු නම, ප්‍රමාණය සහ Download Link එක JSON ආකාරයෙන් සුරැකේ."
        ),
        WorkflowStepItem(
            stepNumber = 4,
            titleEn = "Real-Time Cloud Synchronization",
            titleSi = "සජීවීව සියලුම දුරකථන වෙත දත්ත සමමුහුර්ත වීම",
            descriptionEn = "Firestore Snapshot Listeners push the new file alert to all enrolled students instantly.",
            descriptionSi = "Cloud Database එකේ වෙනස්කම සියලුම සිසුන්ගේ දුරකථනවලට ක්ෂණිකව Sync වේ.",
            iconEmoji = "⚡",
            technicalNoteEn = "Websocket / gRPC push stream triggers Compose UI state update.",
            technicalNoteSi = "සිසුන්ගේ දුරකථනවල අලුත් ගොනුව ක්ෂණිකව දිස්වේ."
        ),
        WorkflowStepItem(
            stepNumber = 5,
            titleEn = "Offline Caching on Demand",
            titleSi = "දුරකථනයේ Local Storage එකට Download වීම",
            descriptionEn = "When student taps Download, the file is saved to internal phone memory for offline viewing.",
            descriptionSi = "සිසුවා Download කළ පසු එය දුරකථනයේ Local මතකයට සුරැකී Offline වැඩ කරයි.",
            iconEmoji = "💾",
            technicalNoteEn = "Stored in context.filesDir or Room DB cache for offline resilience.",
            technicalNoteSi = "අන්තර්ජාලය නැතිවිටත් බැලිය හැකි වන පරිදි Phone Internal Storage එකේ සුරැකේ."
        )
    )

    val devRoadmapSteps = listOf(
        WorkflowStepItem(
            stepNumber = 1,
            titleEn = "Day 1-3: VS Code & Tooling Setup",
            titleSi = "දින 1-3: VS Code සහ මෙවලම් සකස් කිරීම",
            descriptionEn = "Install VS Code, Flutter / React Native SDK, Android Studio emulators, and Git.",
            descriptionSi = "VS Code ස්ථාපනය, Flutter/Dart extensions, Android SDK සූදානම් කරගැනීම.",
            iconEmoji = "💻",
            technicalNoteEn = "Run 'flutter doctor' in terminal to ensure green checkmarks.",
            technicalNoteSi = "Terminal එකේ 'flutter doctor' මගින් සියල්ල හරියාකාරව සූදානම්දැයි බලන්න."
        ),
        WorkflowStepItem(
            stepNumber = 2,
            titleEn = "Day 4-10: UI Layouts & Navigation",
            titleSi = "දින 4-10: අතුරුමුහුණත් සහ සංචාලනය නිර්මාණය",
            descriptionEn = "Build Explore screen, Course Details, Video player frame, and Bottom Navigation bar.",
            descriptionSi = "Explore තිරය, Course කාඩ්පත්, Video Player එක සහ Bottom Bar එක කේතනය කිරීම.",
            iconEmoji = "🎨",
            technicalNoteEn = "Material Design 3 components, theme tokens, and responsive paddings.",
            technicalNoteSi = "නවීන Material 3 මඟින් ආකර්ෂණීය තිර නිර්මාණය."
        ),
        WorkflowStepItem(
            stepNumber = 3,
            titleEn = "Day 11-18: State Management & Offline Cache",
            titleSi = "දින 11-18: තත්ත්ව කළමනාකරණය සහ Local මතකය",
            descriptionEn = "Connect ViewModel, StateFlow, Room DB / SQLite for tracking progress and bookmarks.",
            descriptionSi = "සිසුවා ඉගෙනගත් ප්‍රමාණය, Quizzes ලකුණු කළමනාකරණය කරන කේත ලිවීම.",
            iconEmoji = "⚙️",
            technicalNoteEn = "Clean MVVM architecture with reactive Flows for seamless state handling.",
            technicalNoteSi = "MVVM ආකෘතිය භාවිතයෙන් පිරිසිදු කේත ලිවීම."
        ),
        WorkflowStepItem(
            stepNumber = 4,
            titleEn = "Day 19-25: Cloud Backend (Firebase / Supabase)",
            titleSi = "දින 19-25: Cloud Backend සහ Storage සම්බන්ධ කිරීම",
            descriptionEn = "Configure User Authentication, Firestore collections, and Storage buckets for Upload/Download.",
            descriptionSi = "Login ක්‍රමය, Cloud Database සහ ගොනු Upload කිරීමට Cloud Storage සම්බන්ධ කිරීම.",
            iconEmoji = "🔥",
            technicalNoteEn = "Provision Firebase Auth, Firestore security rules, and Storage bucket API.",
            technicalNoteSi = "ආරක්ෂිත Cloud Security Rules සකස් කර යෙදුමට සම්බන්ධ කිරීම."
        ),
        WorkflowStepItem(
            stepNumber = 5,
            titleEn = "Day 26-30: Build, Test & Store Release",
            titleSi = "දින 26-30: පරීක්ෂා කිරීම සහ App Store වෙත දැමීම",
            descriptionEn = "Generate Android .aab bundle and iOS archive, test on real devices, and submit to stores.",
            descriptionSi = "සත්‍ය දුරකථනවල පරීක්ෂා කර Google Play Store සහ Apple App Store වෙත මුදාහැරීම.",
            iconEmoji = "🚀",
            technicalNoteEn = "Sign with release keystore, configure Play Store listing & privacy policies.",
            technicalNoteSi = "Release Keystore එකෙන් Sign කර App Stores වෙත එක් කිරීම."
        )
    )

    val currentList = when (activeTrack) {
        WorkflowTrack.TEACHER -> teacherWorkflow
        WorkflowTrack.STUDENT -> studentWorkflow
        WorkflowTrack.DATA_FLOW -> dataFlowSteps
        WorkflowTrack.DEV_ROADMAP -> devRoadmapSteps
    }

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("workflow_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = if (isSi) "LMS සම්පූර්ණ කාර්ය ප්‍රවාහය (Full Workflow)" else "LMS Full Workflow Visualizer",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("workflow_scrollable"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Track Switcher Tabs
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (isSi) "කාර්ය ප්‍රවාහය තෝරන්න (Select Workflow Track):" else "Select Workflow Perspective:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = PrimaryIndigo
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            FilterChip(
                                selected = activeTrack == WorkflowTrack.TEACHER,
                                onClick = { activeTrack = WorkflowTrack.TEACHER },
                                label = { Text(if (isSi) "👨‍🏫 ගුරුවරයා" else "Teacher") },
                                modifier = Modifier.weight(1f)
                            )
                            FilterChip(
                                selected = activeTrack == WorkflowTrack.STUDENT,
                                onClick = { activeTrack = WorkflowTrack.STUDENT },
                                label = { Text(if (isSi) "🎓 ශිෂ්‍යයා" else "Student") },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            FilterChip(
                                selected = activeTrack == WorkflowTrack.DATA_FLOW,
                                onClick = { activeTrack = WorkflowTrack.DATA_FLOW },
                                label = { Text(if (isSi) "⚡ Data Flow" else "Data Flow") },
                                modifier = Modifier.weight(1f)
                            )
                            FilterChip(
                                selected = activeTrack == WorkflowTrack.DEV_ROADMAP,
                                onClick = { activeTrack = WorkflowTrack.DEV_ROADMAP },
                                label = { Text(if (isSi) "💻 Dev Guide" else "Dev Steps") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Track Header Banner
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryIndigo),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = when (activeTrack) {
                                WorkflowTrack.TEACHER -> if (isSi) "👨‍🏫 ගුරුවරයාගේ සම්පූර්ණ කාර්ය ප්‍රවාහය" else "👨‍🏫 Complete Instructor Workflow"
                                WorkflowTrack.STUDENT -> if (isSi) "🎓 ශිෂ්‍යයාගේ සම්පූර්ණ කාර්ය ප්‍රවාහය" else "🎓 Complete Student Workflow"
                                WorkflowTrack.DATA_FLOW -> if (isSi) "⚡ Cloud & Phone Storage දත්ත ගලායන ආකාරය" else "⚡ Cloud & Device Data Flow"
                                WorkflowTrack.DEV_ROADMAP -> if (isSi) "💻 VS Code මගින් App එක තනන පියවර 5" else "💻 30-Day App Development Roadmap"
                            },
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = Color.White)
                        )

                        Text(
                            text = when (activeTrack) {
                                WorkflowTrack.TEACHER -> if (isSi)
                                    "ගිණුම සෑදීමේ සිට පාඨමාලා නිර්මාණය, දේශන Upload, ශිෂ්‍ය වැඩ පරීක්ෂාව සහ සහතික නිකුත් කිරීම දක්වා පියවර."
                                else
                                    "From registration to creating curriculum, uploading notes, reviewing work, and issuing certificates."
                                WorkflowTrack.STUDENT -> if (isSi)
                                    "පාඨමාලා සෙවීමේ සිට වීඩියෝ නැරඹීම, Offline Download, Quizzes, Assignment භාරදීම සහ සහතිකය ලබාගැනීම දක්වා පියවර."
                                else
                                    "From exploring courses to streaming video, offline caching, quizzes, homework submissions, and certificates."
                                WorkflowTrack.DATA_FLOW -> if (isSi)
                                    "ජංගම තිරයේ සිට Cloud Storage (AWS/Firebase), Cloud Database (Firestore) සහ Local Phone Memory වෙත දත්ත ගමන් කරන ආකාරය."
                                else
                                    "How binary files and JSON metadata stream between phone, cloud storage buckets, and databases."
                                WorkflowTrack.DEV_ROADMAP -> if (isSi)
                                    "VS Code එකෙන් මුල සිටම Android & iOS LMS App එකක් තනා අවසන් කර නිකුත් කරන තාක්ෂණික පියවර."
                                else
                                    "Step-by-step developer roadmap from VS Code setup to Play Store and App Store submission."
                            },
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFE0E7FF))
                        )
                    }
                }
            }

            // Step Timeline Cards
            itemsIndexed(currentList) { index, step ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(PrimaryIndigo),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${step.stepNumber}",
                                        color = Color.White,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                }

                                Text(
                                    text = if (isSi) step.titleSi else step.titleEn,
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            Text(
                                text = step.iconEmoji,
                                fontSize = 24.sp
                            )
                        }

                        Text(
                            text = if (isSi) step.descriptionSi else step.descriptionEn,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Technical Note Pill
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Terminal,
                                    contentDescription = null,
                                    tint = PrimaryIndigo,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (isSi) step.technicalNoteSi else step.technicalNoteEn,
                                    fontFamily = FontFamily.Monospace,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = onBack,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = if (isSi) "ආපසු යෙදුම වෙත (Back to App)" else "Back to EduPulse LMS",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}
