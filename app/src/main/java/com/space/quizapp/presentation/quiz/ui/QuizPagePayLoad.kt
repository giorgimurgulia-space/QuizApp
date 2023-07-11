package com.space.quizapp.presentation.quiz.ui

import com.space.quizapp.presentation.model.AnswerUIModel
import com.space.quizapp.common.resource.Result

data class QuizPagePayLoad(
    val quizTitle: String? = null,
    val question: String? = null,
    val answers: List<AnswerUIModel> = emptyList(),
    val correctAnswerIndex: Int? = null,
    val questionIndex: Int = 0,
    val questionCount: Int = 0,
    val currentPoint: Float = 0.0.toFloat(),
    val isLastQuestion: Boolean = false,
)