package com.example

import com.example.data.SampleLmsData
import com.example.model.AppLanguage
import com.example.viewmodel.LmsUiState
import com.example.viewmodel.LmsViewModel
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun sampleCourses_areNotEmptyAndHaveValidModules() {
        val courses = SampleLmsData.courses
        assertTrue(courses.isNotEmpty())
        courses.forEach { course ->
            assertTrue(course.titleEn.isNotBlank())
            assertTrue(course.titleSi.isNotBlank())
            assertTrue(course.modules.isNotEmpty())
            val allLessons = course.modules.flatMap { it.lessons }
            assertTrue(allLessons.isNotEmpty())
        }
    }

    @Test
    fun courseProgressCalculation_calculatesCorrectPercentage() {
        val course = SampleLmsData.courses.first()
        val allLessonIds = course.modules.flatMap { it.lessons }.map { it.id }

        // Test with 0 completed
        val state0 = LmsUiState(completedLessonIds = emptySet())
        assertEquals(0, state0.getCourseProgressPercent(course))

        // Test with 1 completed
        val state1 = LmsUiState(completedLessonIds = setOf(allLessonIds.first()))
        val expected = ((1f / allLessonIds.size) * 100).toInt()
        assertEquals(expected, state1.getCourseProgressPercent(course))

        // Test with all completed
        val stateAll = LmsUiState(completedLessonIds = allLessonIds.toSet())
        assertEquals(100, stateAll.getCourseProgressPercent(course))
        assertTrue(stateAll.isCourseCompleted(course))
    }

    @Test
    fun sampleQuizzes_haveCorrectOptionIndicesWithinBounds() {
        val quizzes = SampleLmsData.sampleQuizzes
        assertTrue(quizzes.isNotEmpty())
        quizzes.forEach { quiz ->
            quiz.questions.forEach { q ->
                assertTrue(q.correctOptionIndex in q.optionsEn.indices)
                assertTrue(q.correctOptionIndex in q.optionsSi.indices)
            }
        }
    }
}
