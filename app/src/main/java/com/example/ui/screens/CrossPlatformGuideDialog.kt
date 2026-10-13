package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun CrossPlatformGuideDialog(
    language: AppLanguage,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isSi = language == AppLanguage.SINHALA

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
                        modifier = Modifier.testTag("guide_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = if (isSi) "LMS App මාර්ගෝපදේශය (Android & iOS)" else "LMS App Guide (Android & iOS)",
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
                .testTag("guide_scrollable"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Hero
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = PrimaryIndigo),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = if (isSi) "ආරම්භකයින් සඳහා විශේෂයි" else "Beginner Guide",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Text(
                            text = if (isSi)
                                "VS Code මගින් Android සහ iOS දෙකටම LMS App එකක් සාදන සම්පූර්ණ පියවර"
                            else
                                "Building an LMS App for Android & iOS with VS Code from Scratch",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )

                        Text(
                            text = if (isSi)
                                "ඔබ මීට පෙර කිසිදු Code එකක් ලියා නොමැති වුවත්, පහත පියවර 5 අනුගමනය කිරීමෙන් ඔබේම App එක සාදාගත හැක."
                            else
                                "Even with zero programming experience, follow these 5 clear steps to build your dream mobile app.",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFE0E7FF))
                        )
                    }
                }
            }

            // Step 1
            item {
                GuideStepCard(
                    stepNumber = "1",
                    title = if (isSi) "VS Code (Visual Studio Code) සූදානම් කරගැනීම" else "Setting Up Visual Studio Code",
                    description = if (isSi)
                        "VS Code යනු Microsoft සමාගම විසින් නිර්මාණය කරන ලද නොමිලේ ලබාදෙන Code Editor එකකි. එය කේත ලිවීමට උපකාරී වේ."
                    else
                        "VS Code is a free, powerful code editor. Follow these quick steps to get ready:",
                    bulletPoints = if (isSi) listOf(
                        "code.visualstudio.com වෙතින් VS Code Install කරගන්න.",
                        "වම් පස ඇති Extensions icon එක (Ctrl + Shift + X) ක්ලික් කරන්න.",
                        "Flutter, Dart, සහ Material Icon Theme යන Extensions Search කර Install කරන්න.",
                        "Ctrl + ` (Backtick) මඟින් Terminal එක ඕනෑම වේලාවක විවෘත කරගත හැක."
                    ) else listOf(
                        "Download & install from code.visualstudio.com",
                        "Press Ctrl+Shift+X to open the Extensions marketplace",
                        "Install 'Flutter', 'Dart', and 'Material Icon Theme'",
                        "Press Ctrl+` to toggle the integrated terminal"
                    )
                )
            }

            // Step 2
            item {
                GuideStepCard(
                    stepNumber = "2",
                    title = if (isSi) "Android සහ iOS දෙකටම එකවර App සෑදීම (Cross-Platform)" else "Cross-Platform Framework (Android & iOS)",
                    description = if (isSi)
                        "Android සඳහා වෙනම, iOS සඳහා වෙනම කේත ලිවීම ආරම්භකයෙකුට අපහසුය. හොඳම ක්‍රමය වන්නේ Cross-Platform Framework එකක් තෝරා ගැනීමයි:"
                    else
                        "Writing separate native code for Android and iOS is hard for beginners. Use a single-codebase framework:",
                    bulletPoints = if (isSi) listOf(
                        "🏆 Flutter (Google): ඉතා නිර්දේශිතයි! Dart භාෂාව භාවිත වේ. ඉතා වේගවත් සහ ආකර්ෂණීය UI එකවර Android (APK) සහ iPhone (iOS) සඳහා ජනනය කරයි.",
                        "⚡ React Native (Expo): JavaScript/TypeScript භාවිත වේ. පරිගණකයේ Run කරන App එක ඔබේ සත්‍ය දුරකථනයෙන් (Expo Go App මගින්) ක්ෂණිකව බලාගත හැක.",
                        "📱 Native Jetpack Compose: දැනට මෙම යෙදුම සාදා ඇති Kotlin පදනම් කරගත් Android ලෝකයේ නවීනතම ක්‍රමයයි."
                    ) else listOf(
                        "🏆 Flutter (Google): Highly recommended! Uses Dart. Fast 60/120fps native performance for Android & iOS.",
                        "⚡ React Native (Expo): Uses JavaScript/TypeScript. Test instantly on your phone with Expo Go app.",
                        "📱 Jetpack Compose: The modern Android UI toolkit used to build this EduPulse LMS app!"
                    )
                )
            }

            // Step 3
            item {
                GuideStepCard(
                    stepNumber = "3",
                    title = if (isSi) "LMS (Learning Management System) එකක අඩංගු විය යුතු කොටස්" else "Core Architecture of an LMS App",
                    description = if (isSi)
                        "සම්පූර්ණ අධ්‍යාපනික යෙදුමක් සාර්ථක වීමට මෙම අංග 5 අවශ්‍ය වේ:"
                    else
                        "An industry-standard LMS mobile application consists of 5 main modules:",
                    bulletPoints = if (isSi) listOf(
                        "1. පරිශීලක පිවිසුම (Authentication): සිසුන් සහ ගුරුවරුන් සඳහා Login/Signup.",
                        "2. පාඨමාලා නාමාවලිය (Course Catalog): ප්‍රවර්ග (Categories), සෙවුම (Search), සහ පාඨමාලා විස්තර.",
                        "3. වීඩියෝ ධාවකය සහ ප්‍රගතිය (Video Player & Progress): වීඩියෝ නැරඹීම, පාඩම් සම්පූර්ණ කිරීම (60%, 100%).",
                        "4. ප්‍රශ්නාවලි සහ සහතිකපත් (Quizzes & Certificates): ලකුණු ලබාදීම සහ සාර්ථක සිසුන්ට නිල සහතිකපතක් නිකුත් කිරීම.",
                        "5. Cloud Database (Firebase / Supabase): දත්ත සුරැකීමට නොමිලේ භාවිතා කළ හැකි cloud backend."
                    ) else listOf(
                        "1. Authentication: Student & Instructor registration and login",
                        "2. Course Catalog: Categorized courses, search, instructor profiles",
                        "3. Video Player & Progress: Streaming lessons and tracking completion %",
                        "4. Quizzes & Certificates: Multiple-choice tests and automated digital certificates",
                        "5. Cloud Database: Firebase Firestore or Supabase for storing users, courses & progress"
                    )
                )
            }

            // Step 4
            item {
                GuideStepCard(
                    stepNumber = "4",
                    title = if (isSi) "අදම පළමු App එක Run කරන ආකාරය (Quick Start)" else "Quick Start: Running Your App Today",
                    description = if (isSi)
                        "VS Code Terminal එක විවෘත කර (Terminal > New Terminal) පහත විධානයන් අත්හදා බලන්න:"
                    else
                        "Open the VS Code Terminal and run these beginner commands:",
                    bulletPoints = if (isSi) listOf(
                        "Flutter සඳහා: 'flutter create my_lms_app' ලියා Enter කරන්න.",
                        "ඉන්පසු 'cd my_lms_app' සහ 'flutter run' මගින් App එක පරීක්ෂා කළ හැක.",
                        "React Native Expo සඳහා: 'npx create-expo-app my_lms_app' ලියා 'npx expo start' කරන්න.",
                        "ඔබේ ජංගම දුරකථනය පරිගණකයට USB කේබලයක් මගින් සම්බන්ධ කර කෙලින්ම App එක පරීක්ෂා කළ හැක!"
                    ) else listOf(
                        "For Flutter: Run 'flutter create my_lms_app' in terminal",
                        "Then run 'cd my_lms_app' and 'flutter run' to launch",
                        "For Expo: Run 'npx create-expo-app my_lms_app' and 'npx expo start'",
                        "Connect your physical phone via USB (or scan QR code) to test immediately!"
                    )
                )
            }

            // Step 5
            item {
                GuideStepCard(
                    stepNumber = "5",
                    title = if (isSi) "Google Play Store සහ Apple App Store වෙත මුදාහැරීම" else "Publishing to App Stores",
                    description = if (isSi)
                        "ඔබේ App එක සාදා අවසන් වූ පසු ලෝකයටම ලබා දෙන ආකාරය:"
                    else
                        "Releasing your finished app to millions of students worldwide:",
                    bulletPoints = if (isSi) listOf(
                        "Android සඳහා: 'flutter build appbundle' මගින් .aab ගොනුවක් සකසා Google Play Console වෙත උඩුගත කරන්න ($25 එක්වරක් ගෙවීමක්).",
                        "iOS සඳහා: Apple Developer Program ($99/year) අවශ්‍ය වේ. Build කිරීමට Mac පරිගණකයක් හෝ Cloud CI/CD (GitHub Actions / Codemagic) භාවිත කළ හැක.",
                        "අද මෙම AI Studio පරිසරය තුළදී ඔබට දැනටමත් මෙම Android LMS යෙදුම Live Run කර බැලීමට සහ Test කිරීමට හැකියාව ඇත!"
                    ) else listOf(
                        "Android: Generate an .aab file and upload to Google Play Console ($25 one-time registration fee)",
                        "iOS: Requires Apple Developer Program ($99/year) and Xcode (on macOS or Cloud CI/CD)",
                        "Right now inside this AI Studio environment, you can test and export this complete Android LMS app immediately!"
                    )
                )
            }

            // Summary Action Card
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
                        text = if (isSi) "යෙදුම වෙත ආපසු යන්න" else "Back to EduPulse LMS",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

@Composable
private fun GuideStepCard(
    stepNumber: String,
    title: String,
    description: String,
    bulletPoints: List<String>,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(PrimaryIndigo),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stepNumber,
                        color = Color.White,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                bulletPoints.forEach { point ->
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "•",
                            color = PrimaryIndigo,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = point,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
