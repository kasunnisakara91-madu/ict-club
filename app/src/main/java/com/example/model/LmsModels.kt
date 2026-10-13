package com.example.model

enum class AppLanguage {
    ENGLISH,
    SINHALA
}

enum class UserRole(val labelEn: String, val labelSi: String) {
    STUDENT("Student", "ශිෂ්‍යයා"),
    TEACHER("Teacher / Instructor", "ගුරුවරයා / කථිකාචාර්ය")
}

enum class CourseCategory(val labelEn: String, val labelSi: String) {
    ALL("All Courses", "සියලුම පාඨමාලා"),
    MOBILE_DEV("Mobile Dev", "ජංගම යෙදුම් සංවර්ධනය"),
    WEB_DEV("Web Dev", "වෙබ් සංවර්ධනය"),
    UI_UX("UI/UX Design", "අතුරුමුහුණත් නිර්මාණය"),
    DATA_SCIENCE("Data Science", "දත්ත විද්‍යාව"),
    AI_ML("AI & ML", "කෘත්‍රිම බුද්ධිය"),
    BUSINESS("Business", "ව්‍යාපාරික දැනුම")
}

enum class CourseLevel(val labelEn: String, val labelSi: String) {
    BEGINNER("Beginner", "ආරම්භක"),
    INTERMEDIATE("Intermediate", "මධ්‍යම"),
    ADVANCED("Advanced", "උසස්")
}

enum class LessonType {
    VIDEO,
    READING,
    QUIZ,
    ASSIGNMENT
}

data class LessonResource(
    val title: String,
    val size: String,
    val fileType: String,
    val id: String = "",
    val isDownloaded: Boolean = false
)

data class Lesson(
    val id: String,
    val titleEn: String,
    val titleSi: String,
    val durationMinutes: Int,
    val type: LessonType,
    val summaryEn: String,
    val summarySi: String,
    val contentNotesEn: String,
    val contentNotesSi: String,
    val resources: List<LessonResource> = emptyList(),
    val videoDurationSec: Int = 360
)

data class Module(
    val id: String,
    val titleEn: String,
    val titleSi: String,
    val order: Int,
    val lessons: List<Lesson>
)

data class QuizQuestion(
    val id: String,
    val questionEn: String,
    val questionSi: String,
    val optionsEn: List<String>,
    val optionsSi: List<String>,
    val correctOptionIndex: Int,
    val explanationEn: String,
    val explanationSi: String
)

data class Quiz(
    val id: String,
    val courseId: String,
    val titleEn: String,
    val titleSi: String,
    val durationMinutes: Int,
    val passingScorePercent: Int,
    val questions: List<QuizQuestion>
)

enum class AssignmentStatus {
    PENDING,
    SUBMITTED,
    GRADED
}

data class Assignment(
    val id: String,
    val courseId: String,
    val courseTitle: String,
    val titleEn: String,
    val titleSi: String,
    val descriptionEn: String,
    val descriptionSi: String,
    val dueDate: String,
    val maxScore: Int,
    val status: AssignmentStatus = AssignmentStatus.PENDING,
    val userSubmission: String = "",
    val attachedFileName: String? = null,
    val grade: Int? = null,
    val feedback: String = ""
)

data class Certificate(
    val id: String,
    val courseId: String,
    val courseTitleEn: String,
    val courseTitleSi: String,
    val studentName: String,
    val issueDate: String,
    val gradePercent: Int,
    val verificationCode: String
)

data class Announcement(
    val id: String,
    val courseTitle: String,
    val author: String,
    val date: String,
    val titleEn: String,
    val titleSi: String,
    val contentEn: String,
    val contentSi: String
)

data class UploadedFile(
    val id: String,
    val title: String,
    val fileName: String,
    val fileSize: String,
    val fileType: String, // "PDF", "ZIP", "DOCX", "VIDEO"
    val uploaderRole: UserRole, // TEACHER or STUDENT
    val uploaderName: String,
    val courseId: String,
    val courseTitle: String,
    val uploadTimestamp: String,
    val isDownloadedLocally: Boolean = false,
    val cloudStorageBucket: String = "gs://edupulse-lms-assets/uploads/"
)

data class StudentSubmission(
    val id: String,
    val assignmentId: String,
    val assignmentTitle: String,
    val studentName: String,
    val studentEmail: String,
    val notes: String,
    val attachedFileName: String? = null,
    val attachedFileSize: String? = null,
    val submittedAt: String,
    val grade: Int? = null,
    val feedback: String? = null,
    val isGraded: Boolean = false
)

data class Course(
    val id: String,
    val titleEn: String,
    val titleSi: String,
    val subtitleEn: String,
    val subtitleSi: String,
    val instructorName: String,
    val instructorRoleEn: String,
    val instructorRoleSi: String,
    val category: CourseCategory,
    val level: CourseLevel,
    val rating: Double,
    val reviewCount: Int,
    val enrolledCount: Int,
    val durationHours: Double,
    val price: String, // "Free" or "$29.99"
    val drawableResName: String,
    val modules: List<Module>,
    val learningPointsEn: List<String>,
    val learningPointsSi: List<String>,
    val requirementsEn: List<String>,
    val requirementsSi: List<String>
)
