package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.viewmodel.LmsUiState

@Composable
fun StorageExplainerScreen(
    uiState: LmsUiState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = uiState.language
    val isSi = lang == AppLanguage.SINHALA

    val totalCloudFiles = uiState.uploadedFiles.size
    val totalDownloadedOffline = uiState.uploadedFiles.count { it.isDownloadedLocally }

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
                        modifier = Modifier.testTag("storage_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = if (isSi) "දත්ත Save වෙන්නේ කොහෙද? (Data Storage)" else "Where is Data Saved? (Storage Architecture)",
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
                .testTag("storage_explainer_scrollable"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Explainer Card
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
                                text = if (isSi) "මෘදුකාංග ගෘහනිර්මාණ ශිල්පය (Architecture)" else "Software Architecture",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Text(
                            text = if (isSi)
                                "LMS App එකක දත්ත ගබඩා වන ප්‍රධාන ස්ථාන 3"
                            else
                                "The 3 Places Where LMS Data is Stored",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )

                        Text(
                            text = if (isSi)
                                "LMS යෙදුමක දත්ත සියල්ල එකම තැනක ගබඩා නොකරයි. විවිධ වර්ගයේ දත්ත සඳහා සුදුසුම ගබඩා ස්ථාන 3ක් භාවිත වේ."
                            else
                                "A modern app splits data across 3 layers for maximum speed, security, and offline support.",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFE0E7FF))
                        )
                    }
                }
            }

            // Live Metrics Summary
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Metric 1: Database records
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = "🗄️", fontSize = 22.sp)
                            Text(
                                text = "${uiState.courses.size + uiState.assignments.size}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = if (isSi) "DB Records" else "DB Records",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Metric 2: Cloud Storage Objects
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = "☁️", fontSize = 22.sp)
                            Text(
                                text = "$totalCloudFiles files",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = if (isSi) "Cloud Bucket" else "Cloud Storage",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Metric 3: Offline cached
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = "📱", fontSize = 22.sp)
                            Text(
                                text = "$totalDownloadedOffline files",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = if (isSi) "Phone Cache" else "Device Local",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Layer 1: Cloud Database
            item {
                StorageLayerCard(
                    layerNumber = "1",
                    badgeText = "Database (දත්ත සමුදාය)",
                    title = if (isSi) "Cloud Database (Firebase Firestore / Cloud SQL)" else "Cloud Database (Firestore / PostgreSQL)",
                    techStack = "Firebase Firestore, MongoDB, PostgreSQL, Supabase",
                    description = if (isSi)
                        "පරිශීලක තොරතුරු, පෙළ (Text) සහ ඉලක්කම් ලේඛන ගබඩා වන්නේ මෙහිය."
                    else
                        "Stores structured text records, user accounts, and relationships.",
                    itemsStored = if (isSi) listOf(
                        "👤 සිසුන් සහ ගුරුවරුන්ගේ ගිණුම් (Email, Password, Name)",
                        "📚 පාඨමාලා විස්තර (Title, Syllabus, Modules, Prices)",
                        "📊 ශිෂ්‍ය ප්‍රගතිය සහ ලකුණු (Progress %, Quiz 95%, Streak 7 days)",
                        "📝 පැවරුම් පෙළ (Assignment instructions and text answers)"
                    ) else listOf(
                        "User accounts & Authentication records",
                        "Course catalog metadata and module structure",
                        "Student progress tracking & quiz scores",
                        "Assignment text submissions & instructor feedback"
                    )
                )
            }

            // Layer 2: Cloud Object Storage
            item {
                StorageLayerCard(
                    layerNumber = "2",
                    badgeText = "File Storage (ගොනු ගබඩාව)",
                    title = if (isSi) "Cloud Object Storage (Firebase Storage / AWS S3)" else "Cloud File Storage (Firebase Storage / AWS S3)",
                    techStack = "Firebase Cloud Storage, AWS S3, Google Cloud Storage",
                    description = if (isSi)
                        "විශාල ගොනු (Files), වීඩියෝ සහ PDFs කිසිවිටෙකත් Database එක ඇතුළේ දමන්නේ නැත. ඒවා මෙවැනි Cloud Storage Bucket එකක ගබඩා වේ."
                    else
                        "Large binary files are stored in object storage buckets with download URLs saved in DB.",
                    itemsStored = if (isSi) listOf(
                        "📄 ගුරුවරුන් Upload කරන Lecture Notes & PDF පොත්",
                        "🎥 වීඩියෝ පාඩම් සහ දේශන පටිගත කිරීම් (MP4 / HLS streams)",
                        "📦 සිසුන් භාරදෙන Assignment Project ZIP files සහ Screenshots",
                        "🖼️ Course Thumbnails සහ Profile ඡායාරූප"
                    ) else listOf(
                        "Lecture slides, PDF hand-outs, and eBooks",
                        "Recorded lecture video streams & audio recordings",
                        "Student project source code ZIP archives and screenshots",
                        "High-resolution course banners and instructor avatars"
                    )
                )
            }

            // Layer 3: Local Phone Storage
            item {
                StorageLayerCard(
                    layerNumber = "3",
                    badgeText = "Local Storage (දුරකථන මතකය)",
                    title = if (isSi) "Phone Local Cache & Room Database (Offline Storage)" else "Device Local Storage (Room DB & Downloads)",
                    techStack = "Android Room (SQLite), DataStore, App Internal Storage",
                    description = if (isSi)
                        "සිසුවා 'Download' බොත්තම එබූ විට, ගොනුව දුරකථනයේ Local Storage එකට බාගත වේ. එවිට Internet නැති අවස්ථාවලදී පවා (Offline) පාඩම් බැලිය හැක."
                    else
                        "Downloaded materials are saved on the phone's internal storage for offline use.",
                    itemsStored = if (isSi) listOf(
                        "📶 Offline බැලීමට Download කළ PDF සටහන්",
                        "🔑 දුරකථනයේ Login වී සිටින Session Token එක",
                        "⚡ App එක විවෘත කළ වහාම පෙන්වන Cached Data"
                    ) else listOf(
                        "Cached PDF files and lecture notes downloaded for offline study",
                        "User secure login session token",
                        "Locally cached course catalog for instant offline loading"
                    )
                )
            }

            // How Upload & Download Works Step-by-Step
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = if (isSi) "Upload සහ Download සිදුවන සැබෑ ක්‍රියාවලිය" else "How Upload & Download Works in Real Life",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        val steps = if (isSi) listOf(
                            "1️⃣ ගුරුවරයා PDF එකක් Upload කරයි → එය Cloud Storage (AWS S3 / Firebase) වෙත උඩුගත වේ.",
                            "2️⃣ Cloud Storage එකෙන් එම ගොනුවට අදාළ Download Link එකක් ලබාදෙයි.",
                            "3️⃣ එම Download Link එක සහ ගොනු විස්තර Cloud Database (Firestore) තුළ සුරැකේ.",
                            "4️⃣ ශිෂ්‍යයා තම දුරකථනයෙන් 'Download' ක්ලික් කළ විට, එම Link එකෙන් ගොනුව දුරකථනයේ Local Storage එකට Save වේ!"
                        ) else listOf(
                            "1. Teacher uploads PDF file -> Sent to Cloud Storage bucket",
                            "2. Cloud Storage creates a secure URL for the file",
                            "3. Metadata and URL are saved into Cloud Database",
                            "4. Student clicks 'Download' -> File is saved to local device memory for offline reading!"
                        )

                        steps.forEach { step ->
                            Text(
                                text = step,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
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
                        text = if (isSi) "ආපසු ගොනු මධ්‍යස්ථානයට" else "Back to Files Hub",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

@Composable
private fun StorageLayerCard(
    layerNumber: String,
    badgeText: String,
    title: String,
    techStack: String,
    description: String,
    itemsStored: List<String>,
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(PrimaryIndigo),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = layerNumber,
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Text(
                        text = badgeText,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryIndigo
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = techStack,
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                itemsStored.forEach { item ->
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
