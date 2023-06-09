package com.space.quizapp.domain.usecase.user

import com.space.quizapp.domain.model.PointModel
import com.space.quizapp.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserDataUseCse @Inject constructor(
    private val userDataRepository: UserDataRepository
) {

    suspend fun getUser(userId: String) = userDataRepository.getUser(userId)

    suspend fun getUserGPA(userId: String): String {
        var currentUserGPA = 0.0.toFloat()
        val userPoints = userDataRepository.getUserPoint(userId)

        if (userPoints.isEmpty()) {
            currentUserGPA = 0.toFloat()
        } else {
            userPoints.forEach { point ->
                currentUserGPA += point.point
            }
        }

        return currentUserGPA.toString()
    }

    suspend fun getUserPoints(userId: String): Flow<List<PointModel>> = flow {
        val userPoints = userDataRepository.getUserPoint(userId)
        emit(userPoints)
    }
}