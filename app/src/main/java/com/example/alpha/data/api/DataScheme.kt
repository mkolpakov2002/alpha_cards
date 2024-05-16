package com.example.alpha.data.api

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class AuthResult(
    val jwtToken: String,
    val user: User
)

@Entity(tableName = "users")
@Serializable
data class User(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val card_id: String? = null,
    val telegram_id: String? = null,
    val phone: String? = null,
    val user_type: Int,
    val active: Boolean,
    val created: String,
    val updated: String? = null
)

sealed interface DataScheme

@Serializable
data class Hardware(
    var id: Int = 0,
    var name: String = "",
    var type: Int = 0,
    var image_link: String = "",
    var specifications: Map<String, JsonElement> = emptyMap(),
    var item_specifications_template: List<ItemSpecificationTemplate>? = emptyList(),
    var created: String = "",
    var updated: String = ""
): DataScheme

@Serializable
data class ItemSpecificationTemplate(
    val name: String = "",
    val type: String = "",
    val allow_multiple: Boolean = false
): DataScheme

@Serializable
data class Item(
    var id: Int = 0,
    var name: String = "",
    var inv_key: String = "",
    var hardware: Int = 0,
    var room: Int? = 0,
    var status: Int = 0,
    var owner: String = "",
    var place: Int? = 0,
    var available: Boolean = false,
    var specifications: Map<String, JsonElement> = emptyMap(),
    var created: String = "",
    var updated: String = ""
): DataScheme

@Serializable
data class Terminal (
    var id: Int = 0,
    var name: String = "",
    var created: String = "",
    var updated: String = ""
): DataScheme

@Serializable
data class Section(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var room: Int = 0,
    var created: String = "",
    var updated: String = ""
): DataScheme

@Serializable
data class Room(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var lab: Int = 0,
    var building: Int = 0,
    var type: Int = 0,
    var created: String = "",
    var updated: String = ""
): DataScheme

@Serializable
data class PlaceItem(
    val name: String,
    val description: String,
    val section: Int,
    val created: String,
    val updated: String,
    val id: Int
): DataScheme

@Serializable
data class LabItem(
    val name: String,
    val created: String,
    val updated: String,
    val id: Int
): DataScheme

@Serializable
data class BuildingItem(
    val name: String,
    val address: String,
    val created: String,
    val updated: String,
    val id: Int
): DataScheme