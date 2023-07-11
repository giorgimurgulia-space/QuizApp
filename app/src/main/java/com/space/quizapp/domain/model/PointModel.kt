package com.space.quizapp.domain.model

data class PointModel(
    val userId: String,
    val subjectId: String,
    val quizTitle: String,
    val quizDescription: String,
    val quizIcon: String,
    var point: Float,
    val questionCount: Int
)