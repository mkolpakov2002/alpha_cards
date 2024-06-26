package com.example.alpha.data.api

import android.util.Log
import com.example.alpha.App
import com.example.alpha.BuildConfig
import com.example.alpha.ui.permissions.permission.PermissionManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponsePipeline
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

class ApiClient(private val baseUrl: String = "https://1789.nas.helow19274.ru", private val token: String) {

    private val client = HttpClient {
        install(HttpTimeout) {
            requestTimeoutMillis = 100000
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

        install(DefaultRequest) {
            header("accept", "application/json")
            header("Authorization", "Bearer $token")
        }
        HttpResponseValidator {
            validateResponse { response ->
                if (response.status == HttpStatusCode.Unauthorized) {
                    PermissionManager.setAuthIsGranted(false, null)
                }
            }
        }
    }

    suspend fun getHardwareList(page: Int, perPage: Int): HardwareListResponse {
        val response = client.get("$baseUrl/hardware") {
            parameter("page", page)
            parameter("per_page", perPage)
        }
        return response.body()
    }

    suspend fun createHardware(request: HardwareCreateRequest): HardwareCreateResponse {
        val response = client.post("$baseUrl/hardware") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getHardwareRemains(page: Int, perPage: Int): HardwareRemainsResponse {
        val response = client.get("$baseUrl/hardware/remains") {
            parameter("page", page)
            parameter("per_page", perPage)
        }
        return response.body()
    }

    suspend fun getHardwareById(id: Int): HardwareResponse {
        val response = client.get("$baseUrl/hardware/$id")
        return response.body()
    }

    suspend fun updateHardware(id: Int, request: HardwareUpdateRequest): HardwareUpdateResponse {
        val response = client.patch("$baseUrl/hardware/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun deleteHardware(id: Int): DeleteResponse {
        val response = client.delete("$baseUrl/hardware/$id")
        return response.body()
    }

    suspend fun getItemList(page: Int, perPage: Int): ItemListResponse {
        val response = client.get("$baseUrl/item") {
            parameter("page", page)
            parameter("per_page", perPage)
        }
        return response.body()
    }

    suspend fun createItem(request: ItemCreateRequest): ItemCreateResponse {
        val response = client.post("$baseUrl/item") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getItemById(id: Int): Item {
        val response = client.get("$baseUrl/item/$id")
        return response.body()
    }

    suspend fun updateItem(id: Int, request: ItemUpdateRequest): Item {
        val response = client.patch("$baseUrl/item/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun deleteItem(id: Int): DeleteResponse {
        val response = client.delete("$baseUrl/item/$id")
        return response.body()
    }

    suspend fun getTerminalList(
        page: Int = 1,
        perPage: Int = 50,
        filter: List<String>? = null,
        sort: List<String>? = null
    ): TerminalListResponse {
        val response = client.get("$baseUrl/terminal") {
            parameter("page", page)
            parameter("per_page", perPage)
            filter?.let { parameter("filter_", it) }
            sort?.let { parameter("sort", it) }
        }
        return response.body()
    }

    suspend fun createTerminal(request: TerminalCreateRequest): TerminalCreateResponse {
        val response = client.post("$baseUrl/terminal") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getTerminalById(id: Int): TerminalResponse {
        val response = client.get("$baseUrl/terminal/$id")
        return response.body()
    }

    suspend fun updateTerminal(id: Int, request: TerminalUpdateRequest): TerminalUpdateResponse {
        val response = client.patch("$baseUrl/terminal/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun deleteTerminal(id: Int): TerminalDeleteResponse {
        val response = client.delete("$baseUrl/terminal/$id")
        return response.body()
    }

    suspend fun getSectionList(
        page: Int = 1,
        perPage: Int = 50,
        filter: List<String>? = null,
        sort: List<String>? = null
    ): SectionListResponse {
        val response = client.get("$baseUrl/section") {
            parameter("page", page)
            parameter("per_page", perPage)
            filter?.let { parameter("filter_", it) }
            sort?.let { parameter("sort", it) }
        }
        return response.body()
    }

    suspend fun createSection(request: SectionCreateRequest): SectionCreateResponse {
        val response = client.post("$baseUrl/section") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getSectionById(id: Int): SectionResponse {
        val response = client.get("$baseUrl/section/$id")
        return response.body()
    }

    suspend fun updateSection(id: Int, request: SectionUpdateRequest): SectionUpdateResponse {
        val response = client.patch("$baseUrl/section/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun deleteSectionItem(id: Int): SectionDeleteResponse {
        val response = client.delete("$baseUrl/section/$id")
        return response.body()
    }

    suspend fun getRoomList(
        page: Int = 1,
        perPage: Int = 50,
        filter: List<String>? = null,
        sort: List<String>? = null
    ): RoomListResponse {
        val response = client.get("$baseUrl/room") {
            parameter("page", page)
            parameter("per_page", perPage)
            filter?.let { parameter("filter_", it) }
            sort?.let { parameter("sort", it) }
        }
        return response.body()
    }

    suspend fun createRoom(request: RoomCreateRequest): RoomCreateResponse {
        val response = client.post("$baseUrl/room") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getRoomById(id: Int): RoomResponse {
        val response = client.get("$baseUrl/room/$id")
        return response.body()
    }

    suspend fun updateRoom(id: Int, request: RoomUpdateRequest): RoomUpdateResponse {
        val response = client.patch("$baseUrl/room/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getPlaceItemList(
        page: Int = 1,
        perPage: Int = 50,
        filter: List<String>? = null,
        sort: List<String>? = null
    ): PlaceListResponse {
        val response = client.get("$baseUrl/place") {
            parameter("page", page)
            parameter("per_page", perPage)
            filter?.let { parameter("filter_", it) }
            sort?.let { parameter("sort", it) }
        }
        return response.body()
    }

    suspend fun createPlaceItem(request: PlaceCreateRequest): PlaceCreateResponse {
        val response = client.post("$baseUrl/place") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getPlaceItemById(id: Int): PlaceResponse {
        val response = client.get("$baseUrl/place/$id")
        return response.body()
    }

    suspend fun updatePlaceItem(id: Int, request: PlaceUpdateRequest): PlaceUpdateResponse {
        val response = client.patch("$baseUrl/place/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun deletePlaceItem(id: Int): PlaceDeleteResponse {
        val response = client.delete("$baseUrl/place/$id")
        return response.body()
    }

    suspend fun getLabItemList(
        page: Int = 1,
        perPage: Int = 50,
        filter: List<String>? = null,
        sort: List<String>? = null
    ): LabListResponse {
        val response = client.get("$baseUrl/lab") {
            parameter("page", page)
            parameter("per_page", perPage)
            filter?.let { parameter("filter_", it) }
            sort?.let { parameter("sort", it) }
        }
        return response.body()
    }

    suspend fun createLabItem(request: LabCreateRequest): LabCreateResponse {
        val response = client.post("$baseUrl/lab") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getLabItemById(id: Int): LabResponse {
        val response = client.get("$baseUrl/lab/$id")
        return response.body()
    }

    suspend fun updateLabItem(id: Int, request: LabUpdateRequest): LabUpdateResponse {
        val response = client.patch("$baseUrl/lab/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun deleteLabItem(id: Int): LabDeleteResponse {
        val response = client.delete("$baseUrl/lab/$id")
        return response.body()
    }

    suspend fun getBuildingItemList(
        page: Int = 1,
        perPage: Int = 50,
        filter: List<String>? = null,
        sort: List<String>? = null
    ): BuildingListResponse {
        val response = client.get("$baseUrl/building") {
            parameter("page", page)
            parameter("per_page", perPage)
            filter?.let { parameter("filter_", it) }
            sort?.let { parameter("sort", it) }
        }
        return response.body()
    }

    suspend fun createBuildingItem(request: BuildingCreateRequest): BuildingCreateResponse {
        val response = client.post("$baseUrl/building") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun getBuildingItemById(id: Int): BuildingResponse {
        val response = client.get("$baseUrl/building/$id")
        return response.body()
    }

    suspend fun updateBuildingItem(id: Int, request: BuildingUpdateRequest): BuildingUpdateResponse {
        val response = client.patch("$baseUrl/building/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    suspend fun deleteBuildingItem(id: Int): BuildingDeleteResponse {
        val response = client.delete("$baseUrl/building/$id")
        return response.body()
    }

}