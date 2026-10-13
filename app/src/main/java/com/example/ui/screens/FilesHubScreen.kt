package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.model.*
import com.example.ui.theme.*
import com.example.viewmodel.LmsUiState

enum class FileFilterType {
    ALL,
    TEACHER_MATERIALS,
    STUDENT_HOMEWORK,
    OFFLINE_DOWNLOADS
}

@Composable
fun FilesHubScreen(
    uiState: LmsUiState,
    onToggleUserRole: () -> Unit,
    onUploadFile: (String, String, String, String, String, String) -> Unit,
    onToggleDownload: (String) -> Unit,
    onGradeSubmission: (String, Int, String) -> Unit,
    onOpenStorageExplainer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = uiState.language
    val isSi = lang == AppLanguage.SINHALA
    val isTeacher = uiState.userRole == UserRole.TEACHER

    var filterType by remember { mutableStateOf(FileFilterType.ALL) }
    var showUploadDialog by remember { mutableStateOf(false) }

    // Upload Dialog State
    var newFileTitle by remember { mutableStateOf("") }
    var newFileName by remember { mutableStateOf("") }
    var newFileType by remember { mutableStateOf("PDF") }
    var selectedCourseId by remember { mutableStateOf(uiState.courses.firstOrNull()?.id ?: "course-1") }

    // Teacher Grading Dialog State
    var activeGradingSubmission by remember { mutableStateOf<StudentSubmission?>(null) }
    var gradeScoreInput by remember { mutableStateOf("95") }
    var gradeFeedbackInput by remember { mutableStateOf("Good effort! Clean implementation.") }

    // Upload Modal Dialog
    if (showUploadDialog) {
        AlertDialog(
            onDismissRequest = { showUploadDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        imageVector = Icons.Default.CloudUpload,
                        contentDescription = null,
                        tint = PrimaryIndigo
                    )
                    Text(
                        text = if (isTeacher)
                            (if (isSi) "ගුරු ගොනුවක් Cloud වෙත එක් කරන්න" else "Teacher: Upload to Cloud Storage")
                        else
                            (if (isSi) "ශිෂ්‍ය පැවරුම් ගොනුවක් Upload කරන්න" else "Student: Upload Assignment File"),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = if (isSi)
                            "මෙම ගොනුව Cloud Storage (AWS S3 / Firebase Storage) තුළ සුරැකෙන අතර සියලු සිසුන්ට/ගුරුවරුන්ට Download කරගත හැක."
                        else
                            "This file is saved to Cloud Storage and accessible for downloading.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    OutlinedTextField(
                        value = newFileTitle,
                        onValueChange = { newFileTitle = it },
                        label = { Text(if (isSi) "ගොනුවේ මාතෘකාව (Title)" else "Title") },
                        placeholder = { Text(if (isTeacher) "Lecture Notes - Week 2" else "My Assignment Answer") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newFileName,
                        onValueChange = { newFileName = it },
                        label = { Text(if (isSi) "ගොනු නම (e.g. notes.pdf)" else "File Name") },
                        placeholder = { Text("notes_module_2.pdf") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // File Type selector chips
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listOf("PDF", "ZIP", "DOCX", "VIDEO").forEach { type ->
                            FilterChip(
                                selected = newFileType == type,
                                onClick = { newFileType = type },
                                label = { Text(type) }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val title = if (newFileTitle.isNotBlank()) newFileTitle else if (isTeacher) "New Course Material" else "Student Assignment Submission"
                        val filename = if (newFileName.isNotBlank()) newFileName else "document_${System.currentTimeMillis()}.${newFileType.lowercase()}"
                        val selectedCourse = uiState.courses.find { it.id == selectedCourseId } ?: uiState.courses.first()
                        onUploadFile(
                            title,
                            filename,
                            "4.5 MB",
                            newFileType,
                            selectedCourse.id,
                            if (isSi) selectedCourse.titleSi else selectedCourse.titleEn
                        )
                        showUploadDialog = false
                        newFileTitle = ""
                        newFileName = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo)
                ) {
                    Text(if (isSi) "Upload කරන්න (Cloud)" else "Upload Now")
                }
            },
            dismissButton = {
                TextButton(onClick = { showUploadDialog = false }) {
                    Text(if (isSi) "අවලංගු කරන්න" else "Cancel")
                }
            }
        )
    }

    // Teacher Grading Modal Dialog
    if (activeGradingSubmission != null) {
        val sub = activeGradingSubmission!!
        AlertDialog(
            onDismissRequest = { activeGradingSubmission = null },
            title = {
                Text(
                    text = if (isSi) "ශිෂ්‍ය පිළිතුරට ලකුණු ලබාදීම" else "Grade Student Submission",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "${sub.studentName} (${sub.studentEmail})",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = PrimaryIndigo
                    )
                    Text(
                        text = "Notes: ${sub.notes}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    if (sub.attachedFileName != null) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.AttachFile, contentDescription = null, tint = PrimaryIndigo)
                                Text(
                                    text = "${sub.attachedFileName} (${sub.attachedFileSize})",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = gradeScoreInput,
                        onValueChange = { gradeScoreInput = it },
                        label = { Text(if (isSi) "ලකුණු (0 - 100)" else "Score (0 - 100)") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = gradeFeedbackInput,
                        onValueChange = { gradeFeedbackInput = it },
                        label = { Text(if (isSi) "ගුරු ප්‍රතිචාරය (Feedback)" else "Feedback") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val scoreInt = gradeScoreInput.toIntOrNull() ?: 90
                        onGradeSubmission(sub.id, scoreInt, gradeFeedbackInput)
                        activeGradingSubmission = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen)
                ) {
                    Text(if (isSi) "ලකුණු සුරකින්න" else "Save Grade")
                }
            },
            dismissButton = {
                TextButton(onClick = { activeGradingSubmission = null }) {
                    Text(if (isSi) "අවලංගු කරන්න" else "Cancel")
                }
            }
        )
    }

    val filteredFiles = when (filterType) {
        FileFilterType.ALL -> uiState.uploadedFiles
        FileFilterType.TEACHER_MATERIALS -> uiState.uploadedFiles.filter { it.uploaderRole == UserRole.TEACHER }
        FileFilterType.STUDENT_HOMEWORK -> uiState.uploadedFiles.filter { it.uploaderRole == UserRole.STUDENT }
        FileFilterType.OFFLINE_DOWNLOADS -> uiState.uploadedFiles.filter { it.isDownloadedLocally }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("files_hub_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = if (isSi) "ගොනු මධ්‍යස්ථානය (Upload & Download Hub)" else "Files, Uploads & Downloads Hub",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (isSi)
                        "ගුරුවරුන්ට සහ සිසුන්ට පාඨමාලා ගොනු, පැවරුම් සහ සටහන් Cloud වෙත Upload / Download කළ හැක."
                    else
                        "Teacher course materials, student submissions, and offline cached files.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Active Role Banner with Switch Button
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isTeacher) WarningAmber.copy(alpha = 0.15f) else PrimaryIndigo.copy(alpha = 0.12f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(if (isTeacher) WarningAmber else PrimaryIndigo),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isTeacher) Icons.Default.CoPresent else Icons.Default.School,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = if (isTeacher)
                                        (if (isSi) "ගුරු මාදිලිය (Teacher Mode)" else "Teacher Mode")
                                    else
                                        (if (isSi) "ශිෂ්‍ය මාදිලිය (Student Mode)" else "Student Mode"),
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = if (isTeacher)
                                    (if (isSi) "පාඩම් ගොනු Upload කර ශිෂ්‍ය වැඩ පරීක්ෂා කරන්න" else "Upload materials & grade student work")
                                else
                                    (if (isSi) "පාඩම් Download කර ඔබගේ පැවරුම් Submit කරන්න" else "Download notes & upload your answers"),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Button(
                        onClick = onToggleUserRole,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isTeacher) PrimaryIndigo else WarningAmber
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("toggle_user_role_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isTeacher)
                                (if (isSi) "ශිෂ්‍යයාට මාරුවන්න" else "Student Mode")
                            else
                                (if (isSi) "ගුරුවරයාට මාරුවන්න" else "Teacher Mode"),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }

        // Educational "Where is Data Saved?" Callout Card
        item {
            Card(
                onClick = onOpenStorageExplainer,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("storage_explainer_banner")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(SecondaryTeal),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Storage,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (isSi) "දත්ත Save වෙන්නේ කොහෙද? (Storage Architecture)" else "Where is Data Saved? (Storage Architecture)",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isSi)
                                "Cloud Database, Cloud File Storage සහ Phone Offline Cache ක්‍රියා කරන ආකාරය බලන්න."
                            else
                                "Learn how Cloud DB, Cloud Storage & Phone Memory interact.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Action Row: Upload Button & Filter Chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isSi) "ගොනු ලැයිස්තුව (${filteredFiles.size})" else "Files & Materials (${filteredFiles.size})",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Button(
                    onClick = { showUploadDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                    modifier = Modifier.testTag("upload_file_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Upload,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isTeacher)
                            (if (isSi) "+ ගුරු සටහන් Upload" else "+ Teacher Upload")
                        else
                            (if (isSi) "+ ගොනුවක් Upload" else "+ Upload File"),
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        // Filter Chips Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = filterType == FileFilterType.ALL,
                    onClick = { filterType = FileFilterType.ALL },
                    label = { Text(if (isSi) "සියල්ල" else "All") }
                )
                FilterChip(
                    selected = filterType == FileFilterType.TEACHER_MATERIALS,
                    onClick = { filterType = FileFilterType.TEACHER_MATERIALS },
                    label = { Text(if (isSi) "ගුරු සටහන්" else "Lectures") }
                )
                FilterChip(
                    selected = filterType == FileFilterType.STUDENT_HOMEWORK,
                    onClick = { filterType = FileFilterType.STUDENT_HOMEWORK },
                    label = { Text(if (isSi) "ශිෂ්‍ය වැඩ" else "Homework") }
                )
                FilterChip(
                    selected = filterType == FileFilterType.OFFLINE_DOWNLOADS,
                    onClick = { filterType = FileFilterType.OFFLINE_DOWNLOADS },
                    label = { Text(if (isSi) "දුරකථනයේ ඇති (Offline)" else "Offline") }
                )
            }
        }

        // Teacher Submissions Review Section (Visible in Teacher Mode)
        if (isTeacher) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
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
                            Text(
                                text = if (isSi) "සිසුන් භාරදුන් පැවරුම් (Review Submissions)" else "Student Submissions to Review",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryIndigo
                            )
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = PrimaryIndigo.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = "${uiState.studentSubmissions.size} submissions",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = PrimaryIndigo,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }

                        uiState.studentSubmissions.forEach { sub ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (sub.isGraded) SuccessGreen.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surfaceVariant
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = sub.studentName,
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = sub.submittedAt,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    Text(
                                        text = sub.assignmentTitle,
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    Text(
                                        text = "Notes: ${sub.notes}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    if (sub.attachedFileName != null) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Icon(Icons.Default.AttachFile, contentDescription = null, tint = PrimaryIndigo, modifier = Modifier.size(16.dp))
                                            Text(
                                                text = "${sub.attachedFileName} (${sub.attachedFileSize})",
                                                style = MaterialTheme.typography.labelSmall.copy(color = PrimaryIndigo)
                                            )
                                        }
                                    }

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (sub.isGraded) {
                                            Text(
                                                text = "✓ " + (if (isSi) "ලකුණු ලබාදී ඇත: ${sub.grade}/100" else "Graded: ${sub.grade}/100"),
                                                style = MaterialTheme.typography.labelMedium.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    color = SuccessGreen
                                                )
                                            )
                                        } else {
                                            Text(
                                                text = if (isSi) "තවම ලකුණු ලබාදී නැත" else "Pending Review",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = WarningAmber
                                            )
                                        }

                                        Button(
                                            onClick = {
                                                activeGradingSubmission = sub
                                                gradeScoreInput = sub.grade?.toString() ?: "95"
                                                gradeFeedbackInput = sub.feedback ?: "Great submission!"
                                            },
                                            shape = RoundedCornerShape(8.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (sub.isGraded) MaterialTheme.colorScheme.surfaceVariant else PrimaryIndigo
                                            ),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                        ) {
                                            Text(
                                                text = if (sub.isGraded) (if (isSi) "වෙනස් කරන්න" else "Edit Grade") else (if (isSi) "ලකුණු දෙන්න" else "Grade Now"),
                                                color = if (sub.isGraded) MaterialTheme.colorScheme.onSurface else Color.White,
                                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Uploaded Files List
        items(filteredFiles) { file ->
            val isTeacherFile = file.uploaderRole == UserRole.TEACHER
            val isDownloaded = file.isDownloadedLocally

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("uploaded_file_${file.id}")
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
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isTeacherFile) PrimaryIndigo.copy(alpha = 0.12f) else SecondaryTeal.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = if (isTeacherFile)
                                    (if (isSi) "ගුරුවරයා විසින් (Teacher)" else "By Instructor")
                                else
                                    (if (isSi) "ශිෂ්‍යයා විසින් (Student)" else "By Student"),
                                color = if (isTeacherFile) PrimaryIndigo else SecondaryTeal,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        // Cloud Bucket indicator
                        Text(
                            text = file.uploadTimestamp,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = file.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = file.fileType,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }

                        Text(
                            text = file.fileName,
                            fontFamily = FontFamily.Monospace,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "(${file.fileSize})",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Cloud path label
                    Text(
                        text = "Cloud: ${file.cloudStorageBucket}${file.fileName}",
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )

                    // Download / Offline Status Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(
                                imageVector = if (isDownloaded) Icons.Default.CheckCircle else Icons.Default.CloudQueue,
                                contentDescription = null,
                                tint = if (isDownloaded) SuccessGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = if (isDownloaded)
                                    (if (isSi) "දුරකථනයේ සුරැකිණි (Offline Ready)" else "Downloaded on Device")
                                else
                                    (if (isSi) "Cloud Storage තුළ පමණි" else "Stored in Cloud"),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isDownloaded) SuccessGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = if (isDownloaded) FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        }

                        Button(
                            onClick = { onToggleDownload(file.id) },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isDownloaded) MaterialTheme.colorScheme.surfaceVariant else PrimaryIndigo
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("download_action_button_${file.id}")
                        ) {
                            Icon(
                                imageVector = if (isDownloaded) Icons.Default.DeleteOutline else Icons.Default.Download,
                                contentDescription = null,
                                tint = if (isDownloaded) ErrorRose else Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isDownloaded)
                                    (if (isSi) "ඉවත් කරන්න" else "Remove")
                                else
                                    (if (isSi) "බාගත කරන්න" else "Download"),
                                color = if (isDownloaded) ErrorRose else Color.White,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }
    }
}
