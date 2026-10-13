package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.*
import com.example.ui.theme.*
import com.example.viewmodel.LmsUiState

@Composable
fun CommunityScreen(
    uiState: LmsUiState,
    onSubmitAssignment: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = uiState.language
    var selectedTab by remember { mutableIntStateOf(0) }
    var activeSubmissionAssignmentId by remember { mutableStateOf<String?>(null) }
    var submissionText by remember { mutableStateOf("") }
    var attachedFileNameInput by remember { mutableStateOf("My_Project_Submission.zip") }

    // Dialog for Submitting Assignment
    if (activeSubmissionAssignmentId != null) {
        AlertDialog(
            onDismissRequest = { activeSubmissionAssignmentId = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.CloudUpload, contentDescription = null, tint = PrimaryIndigo)
                    Text(
                        text = if (lang == AppLanguage.SINHALA) "පැවරුම සහ ගොනුව ඉදිරිපත් කිරීම" else "Submit Assignment & Attach File",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = if (lang == AppLanguage.SINHALA)
                            "පිළිතුරු සටහන් සහ අමුණන ගොනුවේ නම (File) මෙහි ඇතුළත් කරන්න (Cloud Storage වෙත සුරැකේ):"
                        else
                            "Enter your notes and attachment name (saved to Cloud Storage):",
                        style = MaterialTheme.typography.bodySmall
                    )
                    OutlinedTextField(
                        value = submissionText,
                        onValueChange = { submissionText = it },
                        label = { Text(if (lang == AppLanguage.SINHALA) "පිළිතුරු සටහන් / Link" else "Notes / Link") },
                        placeholder = { Text("https://github.com/... or explanation") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp),
                        maxLines = 3
                    )
                    OutlinedTextField(
                        value = attachedFileNameInput,
                        onValueChange = { attachedFileNameInput = it },
                        label = { Text(if (lang == AppLanguage.SINHALA) "අමුණන ගොනුව (File Attachment)" else "Attachment Name") },
                        placeholder = { Text("Project_Code.zip") },
                        leadingIcon = { Icon(Icons.Default.AttachFile, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val id = activeSubmissionAssignmentId
                        if (id != null && submissionText.isNotBlank()) {
                            onSubmitAssignment(id, "$submissionText [Attached: $attachedFileNameInput]")
                        }
                        activeSubmissionAssignmentId = null
                        submissionText = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo)
                ) {
                    Text(if (lang == AppLanguage.SINHALA) "Cloud වෙත යවන්න (Submit)" else "Submit to Cloud")
                }
            },
            dismissButton = {
                TextButton(onClick = { activeSubmissionAssignmentId = null }) {
                    Text(if (lang == AppLanguage.SINHALA) "අවලංගු කරන්න" else "Cancel")
                }
            }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("community_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = if (lang == AppLanguage.SINHALA) "ප්‍රජාව සහ දැනුම්දීම්" else "Community & Assignments",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (lang == AppLanguage.SINHALA) "පාඨමාලා නිවේදන, පැවරුම් සහ කථිකාචාර්ය උපදෙස්" else "Course announcements, deadlines, and tutor feedback",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = PrimaryIndigo
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text(if (lang == AppLanguage.SINHALA) "නිවේදන (${uiState.announcements.size})" else "Announcements (${uiState.announcements.size})") }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text(if (lang == AppLanguage.SINHALA) "පැවරුම් (${uiState.assignments.size})" else "Assignments (${uiState.assignments.size})") }
                )
            }
        }

        if (selectedTab == 0) {
            items(uiState.announcements) { ann ->
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
                            Text(
                                text = ann.author,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryIndigo
                            )
                            Text(
                                text = ann.date,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = if (lang == AppLanguage.SINHALA) ann.titleSi else ann.titleEn,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = if (lang == AppLanguage.SINHALA) ann.contentSi else ann.contentEn,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(uiState.assignments) { assignment ->
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
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = when (assignment.status) {
                                    AssignmentStatus.GRADED -> SuccessGreen.copy(alpha = 0.15f)
                                    AssignmentStatus.SUBMITTED -> SecondaryCyan.copy(alpha = 0.15f)
                                    AssignmentStatus.PENDING -> WarningAmber.copy(alpha = 0.15f)
                                }
                            ) {
                                Text(
                                    text = when (assignment.status) {
                                        AssignmentStatus.GRADED -> if (lang == AppLanguage.SINHALA) "ලකුණු ලබාදී ඇත (${assignment.grade}/${assignment.maxScore})" else "Graded (${assignment.grade}/${assignment.maxScore})"
                                        AssignmentStatus.SUBMITTED -> if (lang == AppLanguage.SINHALA) "ඉදිරිපත් කර ඇත" else "Submitted"
                                        AssignmentStatus.PENDING -> if (lang == AppLanguage.SINHALA) "ඉදිරිපත් කළ යුතුයි" else "Pending"
                                    },
                                    color = when (assignment.status) {
                                        AssignmentStatus.GRADED -> SuccessGreen
                                        AssignmentStatus.SUBMITTED -> SecondaryTeal
                                        AssignmentStatus.PENDING -> WarningAmber
                                    },
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Text(
                                text = assignment.dueDate,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Text(
                            text = if (lang == AppLanguage.SINHALA) assignment.titleSi else assignment.titleEn,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = if (lang == AppLanguage.SINHALA) assignment.descriptionSi else assignment.descriptionEn,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (assignment.status == AssignmentStatus.GRADED && assignment.feedback.isNotBlank()) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "💬 " + assignment.feedback,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }

                        if (assignment.status == AssignmentStatus.PENDING) {
                            Button(
                                onClick = {
                                    activeSubmissionAssignmentId = assignment.id
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Default.UploadFile, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (lang == AppLanguage.SINHALA) "පිළිතුර ඉදිරිපත් කරන්න" else "Submit Work",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
