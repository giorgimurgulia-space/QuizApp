package com.space.quizapp.data.local.database.model.entity

import androidx.room.Entity


@Entity(tableName = "user_point", primaryKeys = ["userId", "subjectId"])
data class UserPointEntity(
    val userId: String,
    val subjectId: String,
    val quizTitle: String,
    val quizDescription: String,
    val quizIcon: String,
    var point: Float,
    val questionCount: Int
)