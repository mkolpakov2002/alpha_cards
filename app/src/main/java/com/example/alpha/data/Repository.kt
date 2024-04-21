package com.example.alpha.data

import android.util.Log
import com.example.alpha.data.api.ApiClient
import com.example.alpha.data.api.Room
import com.example.alpha.data.api.RoomCreateRequest
import com.example.alpha.data.api.RoomCreateResponse
import com.example.alpha.data.api.RoomResponse
import com.example.alpha.data.api.RoomUpdateRequest
import com.example.alpha.data.api.RoomUpdateResponse

class Repository(private val apiClient: ApiClient) {

    suspend fun getRooms(page: Int = 1, perPage: Int = 50): List<Room> {
        val response = apiClient.getRoomList(page, perPage)
        return response.result
    }

    suspend fun createRoom(request: RoomCreateRequest): RoomCreateResponse {
        return apiClient.createRoom(request)
    }

    suspend fun getRoomById(id: Int): RoomResponse {
        return apiClient.getRoomById(id)
    }

    suspend fun updateRoom(id: Int, request: RoomUpdateRequest): RoomUpdateResponse {
        return apiClient.updateRoom(id, request)
    }

    // Другие методы для работы с терминалами, секциями и т.д.
    // ...

    // В будущем здесь можно добавить методы для работы с базой данных Realm
    // Например:
    // suspend fun getRoomsFromDatabase(): List<Room> {
    //     // Загрузка списка комнат из базы данных Realm
    // }
    //
    // suspend fun saveRoomToDatabase(room: Room) {
    //     // Сохранение комнаты в базу данных Realm
    // }

    companion object {
        private lateinit var instance: Repository
        private lateinit var token: String

        fun getInstance(token: String): Repository {
            if (!this::instance.isInitialized || !this::token.isInitialized || token != this.token) {
                val apiClient = ApiClient(token = token)
                instance = Repository(apiClient)
                this.token = token
            }
            return instance
        }
    }
}