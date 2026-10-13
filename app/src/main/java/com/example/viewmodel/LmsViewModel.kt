package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SampleLmsData
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class MainTab {
    EXPLORE,
    MY_COURSES,
    FILES_HUB, // Upload / Download Center for Students & Teachers
    COMMUNITY,
    PROFILE
}

enum class ActiveScreen {
    MAIN_TABS,
    COURSE_DETAIL,
    LESSON_PLAYER,
    QUIZ_SESSION,
    CROSS_PLATFORM_GUIDE,
    STORAGE_EXPLAINER,
    WORKFLOW_VISUALIZER
}

data class LmsUiState(
    val currentTab: MainTab = MainTab.EXPLORE,
    val activeScreen: ActiveScreen = ActiveScreen.MAIN_TABS,
    val language: AppLanguage = AppLanguage.SINHALA,
    val userRole: UserRole = UserRole.STUDENT, // STUDENT or TEACHER
    val courses: List<Course> = SampleLmsData.courses,
    val selectedCategory: CourseCategory = CourseCategory.ALL,
    val searchQuery: String = "",
    val enrolledCourseIds: Set<String> = setOf("course-1"),
    val completedLessonIds: Set<String> = setOf("les-1-1"),
    val bookmarkedCourseIds: Set<String> = setOf("course-2"),
    val selectedCourse: Course? = null,
    val selectedLesson: Lesson? = null,
    val selectedQuiz: Quiz? = null,
    val selectedCertificate: Certificate? = null,
    val assignments: List<Assignment> = SampleLmsData.sampleAssignments,
    val announcements: List<Announcement> = SampleLmsData.sampleAnnouncements,
    val certificates: List<Certificate> = SampleLmsData.sampleCertificates,
    // File Upload & Download Hub
    val uploadedFiles: List<UploadedFile> = SampleLmsData.sampleUploadedFiles,
    val studentSubmissions: List<StudentSubmission> = SampleLmsData.sampleStudentSubmissions,
    // Active Quiz Session
    val quizQuestionIndex: Int = 0,
    val quizUserAnswers: Map<Int, Int> = emptyMap(),
    val isQuizSubmitted: Boolean = false,
    val quizScorePercent: Int = 0,
    // Video Player State
    val isVideoPlaying: Boolean = false,
    val videoCurrentSeconds: Int = 45,
    val videoTotalSeconds: Int = 360,
    val videoPlaybackSpeed: Float = 1.0f,
    // Profile
    val studentName: String = "Damith Madusanka",
    val studentEmail: String = "damithmadusanka179@gmail.com",
    val teacherName: String = "Kasun Perera (Senior Lecturer)",
    val teacherEmail: String = "kasun.perera@edupulse.lk",
    val studyStreakDays: Int = 7,
    val hoursLearned: Double = 14.5
) {
    val filteredCourses: List<Course>
        get() = courses.filter { course ->
            val matchesCategory = selectedCategory == CourseCategory.ALL || course.category == selectedCategory
            val matchesSearch = searchQuery.isBlank() ||
                    course.titleEn.contains(searchQuery, ignoreCase = true) ||
                    course.titleSi.contains(searchQuery, ignoreCase = true) ||
                    course.instructorName.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesSearch
        }

    val enrolledCourses: List<Course>
        get() = courses.filter { it.id in enrolledCourseIds }

    fun getCourseProgressPercent(course: Course): Int {
        val allLessonIds = course.modules.flatMap { it.lessons }.map { it.id }
        if (allLessonIds.isEmpty()) return 0
        val completedCount = allLessonIds.count { it in completedLessonIds }
        return ((completedCount.toFloat() / allLessonIds.size) * 100).toInt()
    }

    fun isCourseCompleted(course: Course): Boolean {
        val allLessonIds = course.modules.flatMap { it.lessons }.map { it.id }
        return allLessonIds.isNotEmpty() && allLessonIds.all { it in completedLessonIds }
    }
}

class LmsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LmsUiState())
    val uiState: StateFlow<LmsUiState> = _uiState.asStateFlow()

    fun switchTab(tab: MainTab) {
        _uiState.update {
            it.copy(currentTab = tab, activeScreen = ActiveScreen.MAIN_TABS)
        }
    }

    fun setUserRole(role: UserRole) {
        _uiState.update { it.copy(userRole = role) }
    }

    fun toggleUserRole() {
        _uiState.update {
            val newRole = if (it.userRole == UserRole.STUDENT) UserRole.TEACHER else UserRole.STUDENT
            it.copy(userRole = newRole)
        }
    }

    fun toggleLanguage() {
        _uiState.update {
            val newLang = if (it.language == AppLanguage.SINHALA) AppLanguage.ENGLISH else AppLanguage.SINHALA
            it.copy(language = newLang)
        }
    }

    fun setLanguage(lang: AppLanguage) {
        _uiState.update { it.copy(language = lang) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun selectCategory(category: CourseCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun openCourseDetail(course: Course) {
        _uiState.update {
            it.copy(
                selectedCourse = course,
                activeScreen = ActiveScreen.COURSE_DETAIL
            )
        }
    }

    fun enrollCourse(courseId: String) {
        _uiState.update { current ->
            val updated = current.enrolledCourseIds + courseId
            current.copy(enrolledCourseIds = updated)
        }
    }

    fun toggleBookmark(courseId: String) {
        _uiState.update { current ->
            val updated = if (courseId in current.bookmarkedCourseIds) {
                current.bookmarkedCourseIds - courseId
            } else {
                current.bookmarkedCourseIds + courseId
            }
            current.copy(bookmarkedCourseIds = updated)
        }
    }

    fun openLesson(lesson: Lesson, course: Course? = null) {
        _uiState.update { current ->
            current.copy(
                selectedLesson = lesson,
                selectedCourse = course ?: current.selectedCourse,
                activeScreen = ActiveScreen.LESSON_PLAYER,
                isVideoPlaying = true,
                videoCurrentSeconds = 0,
                videoTotalSeconds = lesson.videoDurationSec
            )
        }
    }

    fun markLessonCompleted(lessonId: String) {
        _uiState.update { current ->
            val updatedCompleted = current.completedLessonIds + lessonId
            val updatedHours = current.hoursLearned + 0.3
            val currentCourse = current.selectedCourse
            var updatedCerts = current.certificates
            if (currentCourse != null) {
                val allLessonIds = currentCourse.modules.flatMap { it.lessons }.map { it.id }
                val isNowFinished = allLessonIds.all { it in updatedCompleted }
                val alreadyHasCert = updatedCerts.any { it.courseId == currentCourse.id }
                if (isNowFinished && !alreadyHasCert) {
                    val newCert = Certificate(
                        id = "cert-${System.currentTimeMillis()}",
                        courseId = currentCourse.id,
                        courseTitleEn = currentCourse.titleEn,
                        courseTitleSi = currentCourse.titleSi,
                        studentName = current.studentName,
                        issueDate = "Today",
                        gradePercent = 96,
                        verificationCode = "EP-VERIFIED-${(1000..9999).random()}"
                    )
                    updatedCerts = updatedCerts + newCert
                }
            }
            current.copy(
                completedLessonIds = updatedCompleted,
                hoursLearned = updatedHours,
                certificates = updatedCerts
            )
        }
    }

    fun nextLesson() {
        val course = _uiState.value.selectedCourse ?: return
        val allLessons = course.modules.flatMap { it.lessons }
        val currentLesson = _uiState.value.selectedLesson ?: return
        val currentIndex = allLessons.indexOfFirst { it.id == currentLesson.id }
        if (currentIndex != -1 && currentIndex + 1 < allLessons.size) {
            val next = allLessons[currentIndex + 1]
            openLesson(next, course)
        }
    }

    // Video Player Controls
    fun togglePlayPause() {
        _uiState.update { it.copy(isVideoPlaying = !it.isVideoPlaying) }
    }

    fun seekVideo(seconds: Int) {
        _uiState.update {
            it.copy(videoCurrentSeconds = seconds.coerceIn(0, it.videoTotalSeconds))
        }
    }

    fun changePlaybackSpeed() {
        _uiState.update {
            val nextSpeed = when (it.videoPlaybackSpeed) {
                1.0f -> 1.25f
                1.5f -> 2.0f
                else -> 1.0f
            }
            it.copy(videoPlaybackSpeed = nextSpeed)
        }
    }

    // Quiz Session
    fun startQuiz(quiz: Quiz) {
        _uiState.update {
            it.copy(
                selectedQuiz = quiz,
                activeScreen = ActiveScreen.QUIZ_SESSION,
                quizQuestionIndex = 0,
                quizUserAnswers = emptyMap(),
                isQuizSubmitted = false,
                quizScorePercent = 0
            )
        }
    }

    fun selectQuizAnswer(questionIndex: Int, optionIndex: Int) {
        if (_uiState.value.isQuizSubmitted) return
        _uiState.update {
            val updated = it.quizUserAnswers.toMutableMap()
            updated[questionIndex] = optionIndex
            it.copy(quizUserAnswers = updated)
        }
    }

    fun nextQuizQuestion() {
        val quiz = _uiState.value.selectedQuiz ?: return
        if (_uiState.value.quizQuestionIndex < quiz.questions.size - 1) {
            _uiState.update { it.copy(quizQuestionIndex = it.quizQuestionIndex + 1) }
        }
    }

    fun previousQuizQuestion() {
        if (_uiState.value.quizQuestionIndex > 0) {
            _uiState.update { it.copy(quizQuestionIndex = it.quizQuestionIndex - 1) }
        }
    }

    fun submitQuiz() {
        val quiz = _uiState.value.selectedQuiz ?: return
        val answers = _uiState.value.quizUserAnswers
        var correctCount = 0
        quiz.questions.forEachIndexed { index, question ->
            if (answers[index] == question.correctOptionIndex) {
                correctCount++
            }
        }
        val scorePercent = ((correctCount.toFloat() / quiz.questions.size) * 100).toInt()
        _uiState.update {
            it.copy(
                isQuizSubmitted = true,
                quizScorePercent = scorePercent
            )
        }
    }

    // Upload & Download Actions
    fun uploadFile(
        title: String,
        fileName: String,
        fileSize: String,
        fileType: String,
        courseId: String,
        courseTitle: String
    ) {
        _uiState.update { current ->
            val isTeacher = current.userRole == UserRole.TEACHER
            val uploaderName = if (isTeacher) current.teacherName else "${current.studentName} (Student)"
            val newFile = UploadedFile(
                id = "file-${System.currentTimeMillis()}",
                title = title,
                fileName = fileName,
                fileSize = fileSize,
                fileType = fileType,
                uploaderRole = current.userRole,
                uploaderName = uploaderName,
                courseId = courseId,
                courseTitle = courseTitle,
                uploadTimestamp = "Just now",
                isDownloadedLocally = true
            )
            current.copy(uploadedFiles = listOf(newFile) + current.uploadedFiles)
        }
    }

    fun toggleDownloadFile(fileId: String) {
        _uiState.update { current ->
            val updated = current.uploadedFiles.map { file ->
                if (file.id == fileId) {
                    file.copy(isDownloadedLocally = !file.isDownloadedLocally)
                } else file
            }
            current.copy(uploadedFiles = updated)
        }
    }

    // Teacher Grades Student Submission
    fun gradeSubmission(submissionId: String, grade: Int, feedback: String) {
        _uiState.update { current ->
            val updatedSubmissions = current.studentSubmissions.map { sub ->
                if (sub.id == submissionId) {
                    sub.copy(
                        grade = grade,
                        feedback = feedback,
                        isGraded = true
                    )
                } else sub
            }
            // Also sync with assignments list if needed
            val updatedAssignments = current.assignments.map { a ->
                val matchingSub = updatedSubmissions.find { it.assignmentId == a.id }
                if (matchingSub != null && matchingSub.isGraded) {
                    a.copy(
                        status = AssignmentStatus.GRADED,
                        grade = matchingSub.grade,
                        feedback = matchingSub.feedback ?: ""
                    )
                } else a
            }
            current.copy(
                studentSubmissions = updatedSubmissions,
                assignments = updatedAssignments
            )
        }
    }

    // Student Submits Assignment with file attachment
    fun submitAssignment(assignmentId: String, text: String, attachedFileName: String? = null, attachedFileSize: String? = null) {
        _uiState.update { current ->
            val assignment = current.assignments.find { it.id == assignmentId }
            val newSubmission = StudentSubmission(
                id = "sub-${System.currentTimeMillis()}",
                assignmentId = assignmentId,
                assignmentTitle = assignment?.titleEn ?: "Assignment Submission",
                studentName = current.studentName,
                studentEmail = current.studentEmail,
                notes = text,
                attachedFileName = attachedFileName ?: "Assignment_Work.pdf",
                attachedFileSize = attachedFileSize ?: "2.4 MB",
                submittedAt = "Just now",
                isGraded = false
            )
            val updatedAssignments = current.assignments.map { a ->
                if (a.id == assignmentId) {
                    a.copy(
                        status = AssignmentStatus.SUBMITTED,
                        userSubmission = text,
                        attachedFileName = attachedFileName
                    )
                } else a
            }
            // Also register as an uploaded file
            val newUploadedFile = UploadedFile(
                id = "file-sub-${System.currentTimeMillis()}",
                title = "Submission: ${assignment?.titleEn ?: "Assignment"}",
                fileName = attachedFileName ?: "Assignment_Work.pdf",
                fileSize = attachedFileSize ?: "2.4 MB",
                fileType = "ZIP",
                uploaderRole = UserRole.STUDENT,
                uploaderName = current.studentName,
                courseId = assignment?.courseId ?: "course-1",
                courseTitle = assignment?.courseTitle ?: "Mobile Dev",
                uploadTimestamp = "Just now",
                isDownloadedLocally = true
            )
            current.copy(
                assignments = updatedAssignments,
                studentSubmissions = listOf(newSubmission) + current.studentSubmissions,
                uploadedFiles = listOf(newUploadedFile) + current.uploadedFiles
            )
        }
    }

    fun openCertificateModal(certificate: Certificate) {
        _uiState.update { it.copy(selectedCertificate = certificate) }
    }

    fun closeCertificateModal() {
        _uiState.update { it.copy(selectedCertificate = null) }
    }

    fun openCrossPlatformGuide() {
        _uiState.update { it.copy(activeScreen = ActiveScreen.CROSS_PLATFORM_GUIDE) }
    }

    fun openStorageExplainer() {
        _uiState.update { it.copy(activeScreen = ActiveScreen.STORAGE_EXPLAINER) }
    }

    fun openWorkflowVisualizer() {
        _uiState.update { it.copy(activeScreen = ActiveScreen.WORKFLOW_VISUALIZER) }
    }

    fun navigateBack() {
        _uiState.update { current ->
            when (current.activeScreen) {
                ActiveScreen.LESSON_PLAYER -> {
                    current.copy(
                        activeScreen = if (current.selectedCourse != null) ActiveScreen.COURSE_DETAIL else ActiveScreen.MAIN_TABS,
                        isVideoPlaying = false
                    )
                }
                ActiveScreen.COURSE_DETAIL -> {
                    current.copy(activeScreen = ActiveScreen.MAIN_TABS, selectedCourse = null)
                }
                ActiveScreen.QUIZ_SESSION -> {
                    current.copy(activeScreen = ActiveScreen.MAIN_TABS, selectedQuiz = null)
                }
                ActiveScreen.CROSS_PLATFORM_GUIDE -> {
                    current.copy(activeScreen = ActiveScreen.MAIN_TABS)
                }
                ActiveScreen.STORAGE_EXPLAINER -> {
                    current.copy(activeScreen = ActiveScreen.MAIN_TABS)
                }
                ActiveScreen.WORKFLOW_VISUALIZER -> {
                    current.copy(activeScreen = ActiveScreen.MAIN_TABS)
                }
                ActiveScreen.MAIN_TABS -> current
            }
        }
    }
}
