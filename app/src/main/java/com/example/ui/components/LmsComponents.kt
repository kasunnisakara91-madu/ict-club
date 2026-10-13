package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.*
import com.example.ui.theme.*
import com.example.viewmodel.MainTab

@Composable
fun LmsTopAppBar(
    language: AppLanguage,
    userRole: UserRole,
    onToggleLanguage: () -> Unit,
    onToggleRole: () -> Unit,
    onOpenGuide: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isTeacher = userRole == UserRole.TEACHER
    val isSi = language == AppLanguage.SINHALA

    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(PrimaryIndigo, SecondaryCyan)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "EduPulse LMS Logo",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Column {
                    Text(
                        text = "EduPulse LMS",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (isSi) "ස්මාර්ට් අධ්‍යාපන පද්ධතිය" else "Smart Learning Platform",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Role Switcher Pill (Teacher / Student)
                Surface(
                    onClick = onToggleRole,
                    shape = RoundedCornerShape(20.dp),
                    color = if (isTeacher) WarningAmber.copy(alpha = 0.15f) else PrimaryIndigo.copy(alpha = 0.12f),
                    modifier = Modifier.testTag("top_role_toggle_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = if (isTeacher) "👨‍🏫" else "🎓",
                            fontSize = 13.sp
                        )
                        Text(
                            text = if (isTeacher) (if (isSi) "ගුරුවරයා" else "Teacher") else (if (isSi) "ශිෂ්‍යයා" else "Student"),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isTeacher) WarningAmber else PrimaryIndigo
                            )
                        )
                    }
                }

                // Language Switcher Chip
                Surface(
                    onClick = onToggleLanguage,
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier.testTag("language_toggle_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = "Language Switch",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = if (language == AppLanguage.SINHALA) "සිංහල" else "EN",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                // Help / Dev Guide Button
                IconButton(
                    onClick = onOpenGuide,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("app_guide_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Beginner Setup Guide",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LmsBottomNavigationBar(
    currentTab: MainTab,
    language: AppLanguage,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp,
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .testTag("bottom_nav_bar")
    ) {
        val tabs = listOf(
            Triple(MainTab.EXPLORE, Icons.Filled.Explore to Icons.Outlined.Explore, if (language == AppLanguage.SINHALA) "ගවේෂණය" else "Explore"),
            Triple(MainTab.MY_COURSES, Icons.Filled.MenuBook to Icons.Outlined.MenuBook, if (language == AppLanguage.SINHALA) "පාඨමාලා" else "Courses"),
            Triple(MainTab.FILES_HUB, Icons.Filled.FolderZip to Icons.Outlined.Folder, if (language == AppLanguage.SINHALA) "ගොනු & Cloud" else "Files Hub"),
            Triple(MainTab.COMMUNITY, Icons.Filled.Campaign to Icons.Outlined.Campaign, if (language == AppLanguage.SINHALA) "ප්‍රජාව" else "Community"),
            Triple(MainTab.PROFILE, Icons.Filled.Person to Icons.Outlined.Person, if (language == AppLanguage.SINHALA) "ගිණුම" else "Profile")
        )

        tabs.forEach { (tab, icons, label) ->
            val selected = currentTab == tab
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = if (selected) icons.first else icons.second,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryIndigo,
                    selectedTextColor = PrimaryIndigo,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
            )
        }
    }
}

@Composable
fun CourseThumbnailImage(
    drawableResName: String,
    modifier: Modifier = Modifier,
    contentDescription: String = "Course image"
) {
    val resId = when (drawableResName) {
        "img_course_coding" -> R.drawable.img_course_coding
        "img_course_design" -> R.drawable.img_course_design
        "img_lms_hero" -> R.drawable.img_lms_hero
        else -> R.drawable.img_course_coding
    }

    Image(
        painter = painterResource(id = resId),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
fun CategoryChip(
    category: CourseCategory,
    isSelected: Boolean,
    language: AppLanguage,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) PrimaryIndigo else MaterialTheme.colorScheme.surfaceVariant,
        label = "chip_bg"
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "chip_text"
    )

    Surface(
        onClick = onSelect,
        shape = RoundedCornerShape(24.dp),
        color = bgColor,
        modifier = modifier.testTag("category_chip_${category.name.lowercase()}")
    ) {
        Text(
            text = if (language == AppLanguage.SINHALA) category.labelSi else category.labelEn,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun CourseCard(
    course: Course,
    language: AppLanguage,
    progressPercent: Int? = null,
    isEnrolled: Boolean = false,
    onCourseClick: () -> Unit,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onCourseClick,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .testTag("course_card_${course.id}")
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                CourseThumbnailImage(
                    drawableResName = course.drawableResName,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = course.titleEn
                )

                // Level Badge on Top Left
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Black.copy(alpha = 0.65f),
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = if (language == AppLanguage.SINHALA) course.level.labelSi else course.level.labelEn,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Price / Enrolled Badge on Top Right
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isEnrolled) SuccessGreen else PrimaryIndigo,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        text = if (isEnrolled) (if (language == AppLanguage.SINHALA) "ලියාපදිංචි වී ඇත" else "Enrolled") else course.price,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = if (language == AppLanguage.SINHALA) course.titleSi else course.titleEn,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = if (language == AppLanguage.SINHALA) course.subtitleSi else course.subtitleEn,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = WarningAmber,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${course.rating}",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "(${course.reviewCount})",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Duration",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${course.durationHours}h",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                if (progressPercent != null && isEnrolled) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (language == AppLanguage.SINHALA) "ප්‍රගතිය" else "Progress",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$progressPercent%",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryIndigo
                            )
                        }
                        LinearProgressIndicator(
                            progress = { progressPercent / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = PrimaryIndigo,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                }

                FilledTonalButton(
                    onClick = onActionClick,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = if (isEnrolled) MaterialTheme.colorScheme.primaryContainer else PrimaryIndigo,
                        contentColor = if (isEnrolled) PrimaryIndigoDark else Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("course_action_button_${course.id}")
                ) {
                    Icon(
                        imageVector = if (isEnrolled) Icons.Default.PlayArrow else Icons.Default.BookmarkAdd,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (isEnrolled) {
                            if (language == AppLanguage.SINHALA) "ඉගෙනුම ඉදිරියට ගෙන යන්න" else "Continue Learning"
                        } else {
                            if (language == AppLanguage.SINHALA) "නොමිලේ ලියාපදිංචි වන්න" else "Enroll for Free"
                        },
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}
