package com.space.quizapp.domain.usecase.user

import com.space.quizapp.domain.repository.UserDataRepository
import javax.inject.Inject
import kotlin.math.roundToInt

class GetUserGpaUseCse @Inject constructor(
    private val userDataRepository: UserDataRepository
) {

    suspend fun invoke(userId: String): String {
        var currentUserGPA = 0.0.toFloat()
        var questionCount = 0

        val userPoints = userDataRepository.getUserPoint(userId)

        if (userPoints.isEmpty()) {
            return currentUserGPA.toString()
        } else {
            userPoints.forEach { point ->
                currentUserGPA += point.point
                questionCount += point.questionCount
            }
        }

        return number2digits((currentUserGPA / questionCount * 4)).toString()
    }

    private fun number2digits(number: Float): Double = (number * 100.0).roundToInt() / 100.0

}