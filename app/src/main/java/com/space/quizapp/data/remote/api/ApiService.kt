package com.space.quizapp.data.remote.api

import com.space.quizapp.data.remote.dto.AvailableQuizDto
import com.space.quizapp.data.remote.dto.QuizDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("dbdf9ee9-8083-425a-99f0-4850c8f87cc6")
    suspend fun getAvailableQuiz(): Response<List<AvailableQuizDto>>

    @GET("8f09207a-ce06-4fec-921f-1446156f6693")
    suspend fun getQuiz(): Response<List<QuizDto>>
}