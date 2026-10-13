package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SampleLmsData
import com.example.model.AppLanguage
import com.example.model.Quiz
import com.example.ui.theme.*
import com.example.viewmodel.LmsUiState

@Composable
fun QuizHubScreen(
    uiState: LmsUiState,
    onStartQuiz: (Quiz) -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = uiState.language
    val quizzes = SampleLmsData.sampleQuizzes

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("quiz_hub_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = if (lang == AppLanguage.SINHALA) "දැනුම පරීක්ෂණ (Quizzes)" else "Quizzes & Assessments",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = if (lang == AppLanguage.SINHALA)
                        "ඔබ උගත් දේ පරීක්ෂා කර ලකුණු 70% කට වඩා ලබාගෙන සහතිකපත් ලබාගන්න"
                    else
                        "Test your understanding, score above 70% and earn course certificates",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        items(quizzes) { quiz ->
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quiz_card_${quiz.id}")
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = PrimaryIndigo.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = "${quiz.questions.size} " + if (lang == AppLanguage.SINHALA) "ප්‍රශ්න" else "Questions",
                                color = PrimaryIndigo,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(Icons.Default.Timer, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                            Text(
                                text = "${quiz.durationMinutes} min",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Text(
                        text = if (lang == AppLanguage.SINHALA) quiz.titleSi else quiz.titleEn,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (lang == AppLanguage.SINHALA) "සමත් ලකුණු: ${quiz.passingScorePercent}%" else "Pass mark: ${quiz.passingScorePercent}%",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Button(
                            onClick = { onStartQuiz(quiz) },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                            modifier = Modifier.testTag("start_quiz_button_${quiz.id}")
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (lang == AppLanguage.SINHALA) "ප්‍රශ්නාවලිය අරඹන්න" else "Start Quiz",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuizSessionScreen(
    quiz: Quiz,
    uiState: LmsUiState,
    onBack: () -> Unit,
    onSelectAnswer: (Int, Int) -> Unit,
    onNextQuestion: () -> Unit,
    onPreviousQuestion: () -> Unit,
    onSubmitQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lang = uiState.language
    val currentIndex = uiState.quizQuestionIndex
    val question = quiz.questions.getOrNull(currentIndex) ?: return
    val selectedOption = uiState.quizUserAnswers[currentIndex]
    val isSubmitted = uiState.isQuizSubmitted
    val score = uiState.quizScorePercent
    val isPassed = score >= quiz.passingScorePercent

    val options = if (lang == AppLanguage.SINHALA) question.optionsSi else question.optionsEn

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
                        modifier = Modifier.testTag("quiz_session_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Text(
                        text = if (lang == AppLanguage.SINHALA) quiz.titleSi else quiz.titleEn,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1
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
                .testTag("quiz_session_scrollable"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Results Card if Submitted
            if (isSubmitted) {
                item {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isPassed) SuccessGreen.copy(alpha = 0.12f) else ErrorRose.copy(alpha = 0.12f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quiz_result_card")
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = if (isPassed) "🎉 සුබ පැතුම්!" else "✍️ නැවත උත්සාහ කරන්න",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = if (isPassed) SuccessGreen else ErrorRose
                            )
                            Text(
                                text = "$score%",
                                fontSize = 42.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (isPassed) SuccessGreen else ErrorRose
                            )
                            Text(
                                text = if (isPassed) {
                                    if (lang == AppLanguage.SINHALA) "ඔබ ප්‍රශ්නාවලිය සාර්ථකව සමත් විය! සහතිකපත ජනනය විය." else "You passed the quiz successfully! Certificate is unlocked."
                                } else {
                                    if (lang == AppLanguage.SINHALA) "සමත් වීමට ${quiz.passingScorePercent}% ක ලකුණක් අවශ්‍ය වේ." else "You need at least ${quiz.passingScorePercent}% to pass."
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            // Question Progress & Counter
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (lang == AppLanguage.SINHALA)
                                "ප්‍රශ්නය ${currentIndex + 1} / ${quiz.questions.size}"
                            else
                                "Question ${currentIndex + 1} of ${quiz.questions.size}",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = PrimaryIndigo
                        )
                        Text(
                            text = "${quiz.durationMinutes} min",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    LinearProgressIndicator(
                        progress = { (currentIndex + 1).toFloat() / quiz.questions.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = PrimaryIndigo,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }

            // Question Text Card
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (lang == AppLanguage.SINHALA) question.questionSi else question.questionEn,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Answer Options
            items(options.indices.toList()) { optionIndex ->
                val optionText = options[optionIndex]
                val isSelected = selectedOption == optionIndex
                val isCorrectAnswer = question.correctOptionIndex == optionIndex

                val borderColor = when {
                    isSubmitted && isCorrectAnswer -> SuccessGreen
                    isSubmitted && isSelected && !isCorrectAnswer -> ErrorRose
                    isSelected -> PrimaryIndigo
                    else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                }

                val cardBg = when {
                    isSubmitted && isCorrectAnswer -> SuccessGreen.copy(alpha = 0.1f)
                    isSubmitted && isSelected && !isCorrectAnswer -> ErrorRose.copy(alpha = 0.1f)
                    isSelected -> PrimaryIndigo.copy(alpha = 0.08f)
                    else -> MaterialTheme.colorScheme.surface
                }

                Surface(
                    onClick = {
                        if (!isSubmitted) onSelectAnswer(currentIndex, optionIndex)
                    },
                    shape = RoundedCornerShape(14.dp),
                    color = cardBg,
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("quiz_option_${currentIndex}_$optionIndex")
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
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) PrimaryIndigo else MaterialTheme.colorScheme.surfaceVariant
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = ('A' + optionIndex).toString(),
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Text(
                            text = optionText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )

                        if (isSubmitted && isCorrectAnswer) {
                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen)
                        } else if (isSubmitted && isSelected && !isCorrectAnswer) {
                            Icon(Icons.Default.Cancel, contentDescription = null, tint = ErrorRose)
                        }
                    }
                }
            }

            // Explanation if submitted
            if (isSubmitted) {
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = if (lang == AppLanguage.SINHALA) "විවරණය:" else "Explanation:",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = PrimaryIndigo
                            )
                            Text(
                                text = if (lang == AppLanguage.SINHALA) question.explanationSi else question.explanationEn,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            // Navigation and Submit Buttons
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (currentIndex > 0) {
                        OutlinedButton(
                            onClick = onPreviousQuestion,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (lang == AppLanguage.SINHALA) "පෙර ප්‍රශ්නය" else "Previous")
                        }
                    }

                    if (currentIndex < quiz.questions.size - 1) {
                        Button(
                            onClick = onNextQuestion,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryIndigo),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (lang == AppLanguage.SINHALA) "මීළඟ ප්‍රශ්නය" else "Next")
                        }
                    } else if (!isSubmitted) {
                        Button(
                            onClick = onSubmitQuiz,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessGreen),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("quiz_submit_button")
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (lang == AppLanguage.SINHALA) "ලකුණු ලබාගන්න" else "Submit Quiz",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        }
    }
}
