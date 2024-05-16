package com.example.alpha.data.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class HardwareCreateRequest(
    val name: String,
    val type: Int,
    val image_link: String,
    val specifications: Map<String, String>,
    val item_specifications_template: List<ItemSpecificationTemplate>
)

@Serializable
data class HardwareUpdateRequest(
    val name: String,
    val image_link: String,
    val specifications: Map<String, String>
)

@Serializable
data class ItemCreateRequest(
    val id: Int,
    val name: String,
    val inv_key: String,
    val hardware: Int,
    val room: Int,
    val status: Int,
    val owner: String,
    val place: Int?,
    val available: Boolean,
    val specifications: Map<String, JsonElement>,
    val created: String,
    val updated: String
)

@Serializable
data class ItemUpdateRequest(
    val room: Int? = null,
    val status: Int? = null,
    val place: Int? = null,
    val available: Boolean? = null
)

@Serializable
data class TerminalCreateRequest(
    val name: String
)

@Serializable
data class TerminalUpdateRequest(
    val name: String
)

@Serializable
data class SectionCreateRequest(
    val name: String,
    val description: String,
    val room: Int
)

@Serializable
data class SectionUpdateRequest(
    val name: String,
    val description: String
)

@Serializable
data class RoomCreateRequest(
    val name: String,
    val lab: Int,
    val building: Int,
    val type: Int
)

@Serializable
data class RoomUpdateRequest(
    val name: String
)

@Serializable
data class PlaceCreateRequest(
    val name: String,
    val description: String,
    val section: Int
)

@Serializable
data class PlaceUpdateRequest(
    val name: String,
    val description: String
)

@Serializable
data class LabCreateRequest(
    val name: String
)

@Serializable
data class LabUpdateRequest(
    val name: String
)

@Serializable
data class BuildingCreateRequest(
    val name: String,
    val address: String
)

@Serializable
data class BuildingUpdateRequest(
    val name: String,
    val address: String
)

