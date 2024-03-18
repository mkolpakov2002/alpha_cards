package com.example.alpha.api

import com.example.alpha.data.api.Report
import com.example.alpha.data.api.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiService {
    @POST("report")
    suspend fun sendReport(@Body report: com.example.alpha.data.api.Report): Response<Unit>

    @POST("login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<com.example.alpha.data.api.User>
}