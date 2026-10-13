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
import com.example.model.AppLanguage
import com.example.model.Course
import com.example.ui.components.CourseCard
import com.example.ui.theme.PrimaryIndigo
import com.example.ui.theme.SuccessGreen
import com.example.viewmodel.LmsUiState

enum class MyCourseFilter {
    ALL,
    IN_PROGRESS,
    COMPLETED
}

@Composable
fun MyCoursesScreen(
    uiState: LmsUiState,
    onCourseClick: (Course) -> Unit,
    onResumeCourse: (Course) -> Unit,
    onExploreCourses: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = uiState.language
    var selectedFilter by remember { mutableStateOf(MyCourseFilter.ALL) }

    val enrolledCourses = uiState.enrolledCourses

    val filteredList = when (selectedFilter) {
        MyCourseFilter.ALL -> enrolledCourses
        MyCourseFilter.IN_PROGRESS -> enrolledCourses.filter { !uiState.isCourseCompleted(it) }
        MyCourseFilter.COMPLETED -> enrolledCourses.filter { uiState.isCourseCompleted(it) }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("my_courses_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = if (lang == AppLanguage.SINHALA) "මගේ ඉගෙනුම් පාඨමාලා" else "My Enrolled Courses",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (lang == AppLanguage.SINHALA) "ඔබ ලියාපදිංචි වී ඇති සියලුම පාඨමාලා සහ ප්‍රගතිය" else "Continue where you left off and track your achievements",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Filter Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == MyCourseFilter.ALL,
                    onClick = { selectedFilter = MyCourseFilter.ALL },
                    label = {
                        Text(if (lang == AppLanguage.SINHALA) "සියල්ල (${enrolledCourses.size})" else "All (${enrolledCourses.size})")
                    },
                    modifier = Modifier.testTag("filter_all")
                )
                FilterChip(
                    selected = selectedFilter == MyCourseFilter.IN_PROGRESS,
                    onClick = { selectedFilter = MyCourseFilter.IN_PROGRESS },
                    label = {
                        val count = enrolledCourses.count { !uiState.isCourseCompleted(it) }
                        Text(if (lang == AppLanguage.SINHALA) "හැදෑරෙමින් පවතින ($count)" else "In Progress ($count)")
                    },
                    modifier = Modifier.testTag("filter_in_progress")
                )
                FilterChip(
                    selected = selectedFilter == MyCourseFilter.COMPLETED,
                    onClick = { selectedFilter = MyCourseFilter.COMPLETED },
                    label = {
                        val count = enrolledCourses.count { uiState.isCourseCompleted(it) }
                        Text(if (lang == AppLanguage.SINHALA) "අවසන් කළ ($count)" else "Completed ($count)")
                    },
                    modifier = Modifier.testTag("filter_completed")
                )
            }
        }

        if (filteredList.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = PrimaryIndigo,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = if (lang == AppLanguage.SINHALA) "මෙම කාණ්ඩයේ පාඨමාලා නැත" else "No courses in this category yet",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (lang == AppLanguage.SINHALA) "නව පාඨමාලා ගවේෂණය කර නොමිලේ ඉගෙනීම අරඹන්න" else "Explore exciting courses and start learning today",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Button(
                            onClick = onExploreCourses,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                            modifier = Modifier.testTag("explore_courses_cta")
                        ) {
                            Text(if (lang == AppLanguage.SINHALA) "පාඨමාලා සොයන්න" else "Explore Courses")
                        }
                    }
                }
            }
        } else {
            items(filteredList) { course ->
                val progress = uiState.getCourseProgressPercent(course)
                val isCompleted = uiState.isCourseCompleted(course)

                CourseCard(
                    course = course,
                    language = lang,
                    progressPercent = progress,
                    isEnrolled = true,
                    onCourseClick = { onCourseClick(course) },
                    onActionClick = { onResumeCourse(course) }
                )
            }
        }
    }
}
