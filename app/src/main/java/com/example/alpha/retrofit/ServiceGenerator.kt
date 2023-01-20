package com.example.alpha.retrofit

import android.text.TextUtils
import okhttp3.Credentials

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceGenerator {
    const val API_BASE_URL = "https://your.api-base.url"
    private val httpClient = OkHttpClient.Builder()
    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
    private var retrofit: Retrofit = builder.build()
    fun <S> createService(serviceClass: Class<S>?): S {
        return createService(serviceClass, null, null)
    }

    fun <S> createService(
        serviceClass: Class<S>?, username: String?, password: String?
    ): S {
        if (!TextUtils.isEmpty(username)
            && !TextUtils.isEmpty(password) &&
            username != null &&
            password != null
                ) {
            val authToken: String = Credentials.basic(username, password)
            return createService(serviceClass, authToken)
        }
        return createService(serviceClass, null)
    }

    fun <S> createService(
        serviceClass: Class<S>?, authToken: String?
    ): S {
        if (authToken != null && !TextUtils.isEmpty(authToken)) {
            val interceptor = AuthenticationInterceptor(authToken)
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor)
                builder.client(httpClient.build())
                retrofit = builder.build()
            }
        }
        return retrofit.create(serviceClass)
    }
}