package com.example.alpha.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import io.ktor.http.HttpMethod

object ApiObject {
    private const val BASE_URL = "https://1789.nas.helow19274.ru"
    private val client = HttpClient {
        expectSuccess = true
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
    private val json = Json {
        ignoreUnknownKeys = false
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
        allowStructuredMapKeys = true
    }

    suspend fun authCallback(accessToken: String): String? {
        return try {
            val request = HttpRequestBuilder().apply {
                method = HttpMethod.Get
                url("https://profile.miem.hse.ru/auth/realms/MIEM/protocol/openid-connect/auth")
                header("state", "android")
//                header("Authorization", "Bearer $accessToken")
            }
            Log.d("API", "Outgoing request: $request")
            val response = client.request(request)
            val responseBody = response.bodyAsText()
            Log.d("API", "response: $responseBody")
            responseBody
        } catch (e: Exception) {
            Log.e("YandexSmartHomeAPI", "Error getting user info: $e")
            null
        }
    }

    suspend fun getBuildings(accessToken: String, page: Int, perPage: Int): String? {
        return try {
            val request = HttpRequestBuilder().apply {
                method = HttpMethod.Get
                url("$BASE_URL/buildings?page=$page&per_page=$perPage")
                header("state", "android")
            }
            Log.d("API", "Outgoing request: $request")
            val response = client.request(request)
            val responseBody = response.bodyAsText()
            Log.d("API", "response: $responseBody")
            responseBody
        } catch (e: Exception) {
            Log.e("YandexSmartHomeAPI", "Error getting user info: $e")
            null
        }
    }

    suspend fun getUserInfo(accessToken: String): String? {
        return try {
            val request = HttpRequestBuilder().apply {
                method = HttpMethod.Get
                url("https://profile.miem.hse.ru/auth/realms/MIEM/protocol/openid-connect/auth?client_id=19230-prj&redirect_uri=https://1789.nas.helow19274.ru/auth/callback&response_type=code")
//                header("detail", "")
            }
            Log.d("API", "Outgoing request: $request")
            val response = client.request(request)
            val responseBody = response.bodyAsText()
            Log.d("API", "response: $responseBody")
            responseBody
        } catch (e: Exception) {
            Log.e("YandexSmartHomeAPI", "Error getting user info: $e")
            null
        }
    }

}