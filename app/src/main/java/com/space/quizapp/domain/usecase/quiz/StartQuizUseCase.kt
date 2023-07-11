package com.space.quizapp.domain.usecase.quiz

import com.space.quizapp.domain.model.QuizModel
import com.space.quizapp.domain.repository.CurrentQuizRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StartQuizUseCase @Inject constructor(
    private val currentQuizRepository: CurrentQuizRepository
) {
    suspend fun invoke(subjectId: String) = flow {
        currentQuizRepository.getQuizById(subjectId)
        emit(currentQuizRepository.startQuiz())
    }
}