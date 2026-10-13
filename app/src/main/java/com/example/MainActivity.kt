package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.LmsBottomNavigationBar
import com.example.ui.components.LmsTopAppBar
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.ActiveScreen
import com.example.viewmodel.LmsViewModel
import com.example.viewmodel.MainTab

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                LmsAppRoot()
            }
        }
    }
}

@Composable
fun LmsAppRoot(
    viewModel: LmsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Handle back button on sub-screens
    BackHandler(enabled = uiState.activeScreen != ActiveScreen.MAIN_TABS) {
        viewModel.navigateBack()
    }

    when (uiState.activeScreen) {
        ActiveScreen.COURSE_DETAIL -> {
            val course = uiState.selectedCourse
            if (course != null) {
                CourseDetailScreen(
                    course = course,
                    uiState = uiState,
                    onBack = { viewModel.navigateBack() },
                    onEnroll = { viewModel.enrollCourse(course.id) },
                    onLessonClick = { lesson -> viewModel.openLesson(lesson, course) },
                    onBookmarkToggle = { viewModel.toggleBookmark(course.id) }
                )
            } else {
                viewModel.navigateBack()
            }
        }

        ActiveScreen.LESSON_PLAYER -> {
            val lesson = uiState.selectedLesson
            val course = uiState.selectedCourse
            if (lesson != null && course != null) {
                LessonPlayerScreen(
                    lesson = lesson,
                    course = course,
                    uiState = uiState,
                    onBack = { viewModel.navigateBack() },
                    onMarkCompleted = { viewModel.markLessonCompleted(lesson.id) },
                    onNextLesson = { viewModel.nextLesson() },
                    onTogglePlayPause = { viewModel.togglePlayPause() },
                    onSeek = { seconds -> viewModel.seekVideo(seconds) },
                    onChangeSpeed = { viewModel.changePlaybackSpeed() }
                )
            } else {
                viewModel.navigateBack()
            }
        }

        ActiveScreen.QUIZ_SESSION -> {
            val quiz = uiState.selectedQuiz
            if (quiz != null) {
                QuizSessionScreen(
                    quiz = quiz,
                    uiState = uiState,
                    onBack = { viewModel.navigateBack() },
                    onSelectAnswer = { qIndex, optIndex -> viewModel.selectQuizAnswer(qIndex, optIndex) },
                    onNextQuestion = { viewModel.nextQuizQuestion() },
                    onPreviousQuestion = { viewModel.previousQuizQuestion() },
                    onSubmitQuiz = { viewModel.submitQuiz() }
                )
            } else {
                viewModel.navigateBack()
            }
        }

        ActiveScreen.CROSS_PLATFORM_GUIDE -> {
            CrossPlatformGuideDialog(
                language = uiState.language,
                onBack = { viewModel.navigateBack() }
            )
        }

        ActiveScreen.STORAGE_EXPLAINER -> {
            StorageExplainerScreen(
                uiState = uiState,
                onBack = { viewModel.navigateBack() }
            )
        }

        ActiveScreen.WORKFLOW_VISUALIZER -> {
            WorkflowVisualizerScreen(
                uiState = uiState,
                onBack = { viewModel.navigateBack() }
            )
        }

        ActiveScreen.MAIN_TABS -> {
            Scaffold(
                topBar = {
                    LmsTopAppBar(
                        language = uiState.language,
                        userRole = uiState.userRole,
                        onToggleLanguage = { viewModel.toggleLanguage() },
                        onToggleRole = { viewModel.toggleUserRole() },
                        onOpenGuide = { viewModel.openCrossPlatformGuide() }
                    )
                },
                bottomBar = {
                    LmsBottomNavigationBar(
                        currentTab = uiState.currentTab,
                        language = uiState.language,
                        onTabSelected = { tab -> viewModel.switchTab(tab) }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (uiState.currentTab) {
                        MainTab.EXPLORE -> {
                            ExploreScreen(
                                uiState = uiState,
                                onSearchChange = { query -> viewModel.updateSearchQuery(query) },
                                onSelectCategory = { cat -> viewModel.selectCategory(cat) },
                                onCourseClick = { course -> viewModel.openCourseDetail(course) },
                                onEnrollOrResume = { course ->
                                    if (course.id !in uiState.enrolledCourseIds) {
                                        viewModel.enrollCourse(course.id)
                                    }
                                    viewModel.openCourseDetail(course)
                                },
                                onOpenGuide = { viewModel.openCrossPlatformGuide() },
                                onOpenWorkflow = { viewModel.openWorkflowVisualizer() }
                            )
                        }

                        MainTab.MY_COURSES -> {
                            MyCoursesScreen(
                                uiState = uiState,
                                onCourseClick = { course -> viewModel.openCourseDetail(course) },
                                onResumeCourse = { course ->
                                    val firstLesson = course.modules.firstOrNull()?.lessons?.firstOrNull()
                                    if (firstLesson != null) {
                                        viewModel.openLesson(firstLesson, course)
                                    } else {
                                        viewModel.openCourseDetail(course)
                                    }
                                },
                                onExploreCourses = { viewModel.switchTab(MainTab.EXPLORE) }
                            )
                        }

                        MainTab.FILES_HUB -> {
                            FilesHubScreen(
                                uiState = uiState,
                                onToggleUserRole = { viewModel.toggleUserRole() },
                                onUploadFile = { title, fileName, size, type, cId, cTitle ->
                                    viewModel.uploadFile(title, fileName, size, type, cId, cTitle)
                                },
                                onToggleDownload = { fileId -> viewModel.toggleDownloadFile(fileId) },
                                onGradeSubmission = { subId, grade, feedback ->
                                    viewModel.gradeSubmission(subId, grade, feedback)
                                },
                                onOpenStorageExplainer = { viewModel.openStorageExplainer() }
                            )
                        }

                        MainTab.COMMUNITY -> {
                            CommunityScreen(
                                uiState = uiState,
                                onSubmitAssignment = { id, text -> viewModel.submitAssignment(id, text) }
                            )
                        }

                        MainTab.PROFILE -> {
                            ProfileScreen(
                                uiState = uiState,
                                onToggleLanguage = { viewModel.toggleLanguage() },
                                onOpenGuide = { viewModel.openCrossPlatformGuide() },
                                onOpenStorageExplainer = { viewModel.openStorageExplainer() },
                                onSelectCertificate = { cert -> viewModel.openCertificateModal(cert) },
                                onCloseCertificate = { viewModel.closeCertificateModal() }
                            )
                        }
                    }
                }
            }
        }
    }
}
