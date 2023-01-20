package com.example.alpha.retrofit

import com.android.volley.Request.Method.POST
import com.example.alpha.data.User
import retrofit2.Call
import retrofit2.http.POST


interface LoginService {
    @POST("/login")
    fun basicLogin(): Call<User?>?
}