package com.example.alpha.data

import android.content.Context
import com.example.alpha.App
import com.example.alpha.data.api.ApiClient
import com.example.alpha.data.api.BuildingCreateRequest
import com.example.alpha.data.api.BuildingCreateResponse
import com.example.alpha.data.api.BuildingItem
import com.example.alpha.data.api.BuildingResponse
import com.example.alpha.data.api.BuildingUpdateRequest
import com.example.alpha.data.api.BuildingUpdateResponse
import com.example.alpha.data.api.Hardware
import com.example.alpha.data.api.HardwareCreateRequest
import com.example.alpha.data.api.HardwareCreateResponse
import com.example.alpha.data.api.HardwareResponse
import com.example.alpha.data.api.HardwareUpdateRequest
import com.example.alpha.data.api.HardwareUpdateResponse
import com.example.alpha.data.api.Item
import com.example.alpha.data.api.ItemCreateRequest
import com.example.alpha.data.api.ItemCreateResponse
import com.example.alpha.data.api.ItemUpdateRequest
import com.example.alpha.data.api.LabCreateRequest
import com.example.alpha.data.api.LabCreateResponse
import com.example.alpha.data.api.LabItem
import com.example.alpha.data.api.LabResponse
import com.example.alpha.data.api.LabUpdateRequest
import com.example.alpha.data.api.LabUpdateResponse
import com.example.alpha.data.api.PlaceCreateRequest
import com.example.alpha.data.api.PlaceCreateResponse
import com.example.alpha.data.api.PlaceItem
import com.example.alpha.data.api.PlaceResponse
import com.example.alpha.data.api.PlaceUpdateRequest
import com.example.alpha.data.api.PlaceUpdateResponse
import com.example.alpha.data.api.Room
import com.example.alpha.data.api.RoomCreateRequest
import com.example.alpha.data.api.RoomCreateResponse
import com.example.alpha.data.api.RoomResponse
import com.example.alpha.data.api.RoomUpdateRequest
import com.example.alpha.data.api.RoomUpdateResponse
import com.example.alpha.data.api.Section
import com.example.alpha.data.api.SectionCreateRequest
import com.example.alpha.data.api.SectionCreateResponse
import com.example.alpha.data.api.SectionResponse
import com.example.alpha.data.api.SectionUpdateRequest
import com.example.alpha.data.api.SectionUpdateResponse
import com.example.alpha.data.api.Terminal
import com.example.alpha.data.api.TerminalCreateRequest
import com.example.alpha.data.api.TerminalCreateResponse
import com.example.alpha.data.api.TerminalResponse
import com.example.alpha.data.api.TerminalUpdateRequest
import com.example.alpha.data.api.TerminalUpdateResponse
import com.example.alpha.data.api.User
import com.example.alpha.data_base.AppDatabase

class Repository(private val apiClient: ApiClient) {

    suspend fun getRoomItems(page: Int = 1, perPage: Int = 250): List<Room> {
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

    // Hardware
    suspend fun getHardwareItems(page: Int = 1, perPage: Int = 250): List<Hardware> {
        val response = apiClient.getHardwareList(page, perPage)
        return response.result
    }

    suspend fun createHardware(request: HardwareCreateRequest): HardwareCreateResponse {
        return apiClient.createHardware(request)
    }

    suspend fun getHardwareById(id: Int): HardwareResponse {
        return apiClient.getHardwareById(id)
    }

    suspend fun updateHardware(id: Int, request: HardwareUpdateRequest): HardwareUpdateResponse {
        return apiClient.updateHardware(id, request)
    }

    // Item
    suspend fun getItemItems(page: Int = 1, perPage: Int = 250): List<Item> {
        val response = apiClient.getItemList(page, perPage)
        return response.result
    }

    suspend fun createItem(request: ItemCreateRequest): ItemCreateResponse {
        return apiClient.createItem(request)
    }

    suspend fun getItemById(id: Int): Item {
        return apiClient.getItemById(id)
    }

//    suspend fun updateItem(id: Int, request: ItemUpdateRequest): ItemUpdateResponse {
//        return apiClient.updateItem(id, request)
//    }

    // Terminal
    suspend fun getTerminalItems(page: Int = 1, perPage: Int = 250): List<Terminal> {
        val response = apiClient.getTerminalList(page, perPage)
        return response.result
    }

    suspend fun createTerminal(request: TerminalCreateRequest): TerminalCreateResponse {
        return apiClient.createTerminal(request)
    }

    suspend fun getTerminalById(id: Int): TerminalResponse {
        return apiClient.getTerminalById(id)
    }

    suspend fun updateTerminal(id: Int, request: TerminalUpdateRequest): TerminalUpdateResponse {
        return apiClient.updateTerminal(id, request)
    }

    // Section
    suspend fun getSectionItems(page: Int = 1, perPage: Int = 250): List<Section> {
        val response = apiClient.getSectionList(page, perPage)
        return response.result
    }

    suspend fun createSection(request: SectionCreateRequest): SectionCreateResponse {
        return apiClient.createSection(request)
    }

    suspend fun getSectionById(id: Int): SectionResponse {
        return apiClient.getSectionById(id)
    }

    suspend fun updateSection(id: Int, request: SectionUpdateRequest): SectionUpdateResponse {
        return apiClient.updateSection(id, request)
    }

    // PlaceItem
    suspend fun getPlaceItems(page: Int = 1, perPage: Int = 250): List<PlaceItem> {
        val response = apiClient.getPlaceItemList(page, perPage)
        return response.result
    }

    suspend fun createPlaceItem(request: PlaceCreateRequest): PlaceCreateResponse {
        return apiClient.createPlaceItem(request)
    }

    suspend fun getPlaceItemById(id: Int): PlaceResponse {
        return apiClient.getPlaceItemById(id)
    }

    suspend fun updatePlaceItem(id: Int, request: PlaceUpdateRequest): PlaceUpdateResponse {
        return apiClient.updatePlaceItem(id, request)
    }

    // LabItem
    suspend fun getLabItems(page: Int = 1, perPage: Int = 250): List<LabItem> {
        val response = apiClient.getLabItemList(page, perPage)
        return response.result
    }

    suspend fun createLabItem(request: LabCreateRequest): LabCreateResponse {
        return apiClient.createLabItem(request)
    }

    suspend fun getLabItemById(id: Int): LabResponse {
        return apiClient.getLabItemById(id)
    }

    suspend fun updateLabItem(id: Int, request: LabUpdateRequest): LabUpdateResponse {
        return apiClient.updateLabItem(id, request)
    }

    // BuildingItem
    suspend fun getBuildingItems(page: Int = 1, perPage: Int = 250): List<BuildingItem> {
        val response = apiClient.getBuildingItemList(page, perPage)
        return response.result
    }

    suspend fun createBuildingItem(request: BuildingCreateRequest): BuildingCreateResponse {
        return apiClient.createBuildingItem(request)
    }

    suspend fun getBuildingItemById(id: Int): BuildingResponse {
        return apiClient.getBuildingItemById(id)
    }

    suspend fun updateBuildingItem(id: Int, request: BuildingUpdateRequest): BuildingUpdateResponse {
        return apiClient.updateBuildingItem(id, request)
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

    suspend fun getUser(): User {
        return appDatabase.userDao().getAllUsers()[0]
    }

    suspend fun saveUser(user: User) {
        appDatabase.userDao().deleteAllUsers()
        appDatabase.userDao().insert(user)
    }

    companion object {
        private lateinit var instance: Repository
        private lateinit var token: String
        private lateinit var appDatabase: AppDatabase

        fun getInstance(token: String): Repository {
            if (!this::instance.isInitialized || !this::token.isInitialized || token != this.token && token!="") {
                val apiClient = ApiClient(token = token)
                instance = Repository(apiClient)
                this.token = token
                this.appDatabase = AppDatabase.getInstance(App.applicationContext())
            }
            return instance
        }
    }
}