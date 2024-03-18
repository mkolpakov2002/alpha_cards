package com.example.alpha.domain

import com.example.alpha.api.ApiService
import com.example.alpha.data.api.Building
import com.example.alpha.data.api.Cell
import com.example.alpha.data.api.Element
import com.example.alpha.data.api.ElementGroup
import com.example.alpha.data.api.Equipment
import com.example.alpha.data.api.Laboratory
import com.example.alpha.data.api.Request
import com.example.alpha.data.api.Room
import com.example.alpha.data.api.Settings
import com.example.alpha.data.api.Shelf
import com.example.alpha.data.api.Terminal
import com.example.alpha.data.api.User
import com.example.alpha.data_base.AppDatabase

class Repository(private val database: AppDatabase, private val apiService: ApiService) {
    // Building
    suspend fun getAllBuildings(): List<Building> = database.buildingDao().getAll()
    suspend fun getBuildingById(id: Int): Building? = database.buildingDao().getById(id)
    suspend fun insertBuilding(building: Building) = database.buildingDao().insert(building)
    suspend fun updateBuilding(building: Building) = database.buildingDao().update(building)
    suspend fun deleteBuilding(building: Building) = database.buildingDao().delete(building)

    // Cell
    suspend fun getAllCells(): List<com.example.alpha.data.api.Cell> = database.cellDao().getAll()
    suspend fun getCellById(id: Int): com.example.alpha.data.api.Cell? = database.cellDao().getById(id)
    suspend fun insertCell(cell: com.example.alpha.data.api.Cell) = database.cellDao().insert(cell)
    suspend fun updateCell(cell: com.example.alpha.data.api.Cell) = database.cellDao().update(cell)
    suspend fun deleteCell(cell: com.example.alpha.data.api.Cell) = database.cellDao().delete(cell)

    // Equipment
    suspend fun getAllEquipment(): List<com.example.alpha.data.api.Equipment> = database.equipmentDao().getAll()
    suspend fun getEquipmentById(id: Int): com.example.alpha.data.api.Equipment? = database.equipmentDao().getById(id)
    suspend fun insertEquipment(equipment: com.example.alpha.data.api.Equipment) = database.equipmentDao().insert(equipment)
    suspend fun updateEquipment(equipment: com.example.alpha.data.api.Equipment) = database.equipmentDao().update(equipment)
    suspend fun deleteEquipment(equipment: com.example.alpha.data.api.Equipment) = database.equipmentDao().delete(equipment)

    // Element
    suspend fun getAllElements(): List<com.example.alpha.data.api.Element> = database.elementDao().getAll()
    suspend fun getElementById(id: Int): com.example.alpha.data.api.Element? = database.elementDao().getById(id)
    suspend fun getElementsByLabId(labId: Int): List<com.example.alpha.data.api.Element> = database.elementDao().getByLabId(labId)
    suspend fun insertElement(element: com.example.alpha.data.api.Element) = database.elementDao().insert(element)
    suspend fun updateElement(element: com.example.alpha.data.api.Element) = database.elementDao().update(element)
    suspend fun deleteElement(element: com.example.alpha.data.api.Element) = database.elementDao().delete(element)

    // ElementGroup
    suspend fun getAllElementGroups(): List<com.example.alpha.data.api.ElementGroup> = database.elementGroupDao().getAll()
    suspend fun getElementGroupById(id: Int): com.example.alpha.data.api.ElementGroup? = database.elementGroupDao().getById(id)
    suspend fun insertElementGroup(elementGroup: com.example.alpha.data.api.ElementGroup) = database.elementGroupDao().insert(elementGroup)
    suspend fun updateElementGroup(elementGroup: com.example.alpha.data.api.ElementGroup) = database.elementGroupDao().update(elementGroup)
    suspend fun deleteElementGroup(elementGroup: com.example.alpha.data.api.ElementGroup) = database.elementGroupDao().delete(elementGroup)

    // Laboratory
    suspend fun getAllLaboratories(): List<com.example.alpha.data.api.Laboratory> = database.laboratoryDao().getAll()
    suspend fun getLaboratoryById(id: Int): com.example.alpha.data.api.Laboratory? = database.laboratoryDao().getById(id)
    suspend fun insertLaboratory(laboratory: com.example.alpha.data.api.Laboratory) = database.laboratoryDao().insert(laboratory)
    suspend fun updateLaboratory(laboratory: com.example.alpha.data.api.Laboratory) = database.laboratoryDao().update(laboratory)
    suspend fun deleteLaboratory(laboratory: com.example.alpha.data.api.Laboratory) = database.laboratoryDao().delete(laboratory)

    // Request
    suspend fun getAllRequests(): List<com.example.alpha.data.api.Request> = database.requestDao().getAll()
    suspend fun getRequestById(id: Int): com.example.alpha.data.api.Request? = database.requestDao().getById(id)
    suspend fun insertRequest(request: com.example.alpha.data.api.Request) = database.requestDao().insert(request)
    suspend fun updateRequest(request: com.example.alpha.data.api.Request) = database.requestDao().update(request)
    suspend fun deleteRequest(request: com.example.alpha.data.api.Request) = database.requestDao().delete(request)

    // Room
    suspend fun getAllRooms(): List<com.example.alpha.data.api.Room> = database.roomDao().getAll()
    suspend fun getRoomById(id: Int): com.example.alpha.data.api.Room? = database.roomDao().getById(id)
    suspend fun getRoomsByLabId(labId: Int): List<com.example.alpha.data.api.Room> = database.roomDao().getByLabId(labId)
    suspend fun insertRoom(room: com.example.alpha.data.api.Room) = database.roomDao().insert(room)
    suspend fun updateRoom(room: com.example.alpha.data.api.Room) = database.roomDao().update(room)
    suspend fun deleteRoom(room: com.example.alpha.data.api.Room) = database.roomDao().delete(room)

    // Shelf
    suspend fun getAllShelves(): List<com.example.alpha.data.api.Shelf> = database.shelfDao().getAll()
    suspend fun getShelfById(id: Int): com.example.alpha.data.api.Shelf? = database.shelfDao().getById(id)
    suspend fun getShelvesByRoomId(roomId: Int): List<com.example.alpha.data.api.Shelf> = database.shelfDao().getByRoomId(roomId)
    suspend fun insertShelf(shelf: com.example.alpha.data.api.Shelf) = database.shelfDao().insert(shelf)
    suspend fun updateShelf(shelf: com.example.alpha.data.api.Shelf) = database.shelfDao().update(shelf)
    suspend fun deleteShelf(shelf: com.example.alpha.data.api.Shelf) = database.shelfDao().delete(shelf)

    // Terminal
    suspend fun getAllTerminals(): List<com.example.alpha.data.api.Terminal> = database.terminalDao().getAll()
    suspend fun getTerminalById(id: Int): com.example.alpha.data.api.Terminal? = database.terminalDao().getById(id)
    suspend fun insertTerminal(terminal: com.example.alpha.data.api.Terminal) = database.terminalDao().insert(terminal)
    suspend fun updateTerminal(terminal: com.example.alpha.data.api.Terminal) = database.terminalDao().update(terminal)
    suspend fun deleteTerminal(terminal: com.example.alpha.data.api.Terminal) = database.terminalDao().delete(terminal)

    // User
    suspend fun getAllUsers(): List<com.example.alpha.data.api.User> = database.userDao().getAll()
    suspend fun getUserById(id: Int): com.example.alpha.data.api.User? = database.userDao().getById(id)
    suspend fun insertUser(user: com.example.alpha.data.api.User) = database.userDao().insert(user)
    suspend fun updateUser(user: com.example.alpha.data.api.User) = database.userDao().update(user)
    suspend fun deleteUser(user: com.example.alpha.data.api.User) = database.userDao().delete(user)

    // Settings
    suspend fun getSettings(): com.example.alpha.data.api.Settings {
        // Retrieve settings from database or shared preferences
        // Return default settings if not available
        return com.example.alpha.data.api.Settings(language = "en", theme = "light")
    }

    suspend fun saveSettings(settings: com.example.alpha.data.api.Settings) {
        // Save settings to database or shared preferences
    }

    suspend fun login(username: String, password: String): Result<com.example.alpha.data.api.User> {
        return try {
            val response = apiService.login(username, password)
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    database.userDao().insert(user)
                    Result.success(user)
                } else {
                    Result.failure(Exception("User data is null"))
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        // Clear authentication token or user session
    }
}