package com.space.quizapp.common.mapper

import com.space.quizapp.data.local.database.model.entity.UserEntity
import com.space.quizapp.data.local.database.model.entity.UserPointEntity
import com.space.quizapp.data.remote.dto.AvailableQuizDto
import com.space.quizapp.data.remote.dto.QuestionDto
import com.space.quizapp.data.remote.dto.QuizDto
import com.space.quizapp.domain.model.*
import com.space.quizapp.presentation.model.*
import java.util.*

fun UserEntity.toDomainModel() = UserModel(userId, username)

fun UserPointEntity.toDomainModel() =
    PointModel(userId, subjectId, quizTitle, quizDescription, quizIcon, point, questionCount)

fun PointModel.toUIModel() =
    PointUIModel(userId, subjectId, quizTitle, quizDescription, quizIcon, point)

fun UserModel.toUIModel(userGPA: String) = UserUIModel(userId, username, userGPA)

fun AvailableQuizDto.toDomainModel() = AvailableQuizModel(id, quizTitle, quizDescription, quizIcon)

fun AvailableQuizModel.toUIModel() = AvailableQuizUIModel(id, quizTitle, quizDescription, quizIcon)

fun QuizDto.toDomainModel() =
    QuizModel(
        id,
        quizTitle,
        quizDescription,
        quizIcon,
        questionsCount
    )

fun QuestionDto.toDomainModel() =
    QuestionModel(
        questionTitle,
        answers.map { it.toAnswer() },
        answers.indexOf(correctAnswer),
        questionIndex.inc()
    )

fun QuizModel.toUIModel() = QuizUIModel(id, quizTitle, quizDescription, quizIcon, questionsCount)

fun AnswerModel.toUIModel() = AnswerUIModel(answerId, answerTitle)

fun String.toAnswer() = AnswerModel(UUID.randomUUID().toString(), this)

fun Float.toPointString(): String {
    // Convert float value
    // of N to integer
    val x = this.toInt()
    val temp2 = this - x

    // If N is not equivalent
    // to any integer
    return if (temp2 > 0)
        this.toString()
    else
        this.toInt().toString()
}

fun PointModel.toEntity() =
    UserPointEntity(userId, subjectId, quizTitle, quizDescription, quizIcon, point, questionCount)



