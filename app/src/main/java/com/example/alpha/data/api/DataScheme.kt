package com.example.alpha.data.api

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class AuthResult(
    val jwtToken: String,
    val user: User
)

@Serializable
data class User(
    val id: Int,
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

@Serializable
class Hardware {
    var id: Int = 0
    var name: String = ""
    var type: Int = 0
    var image_link: String = ""
    var specifications: String = ""
    var item_specifications_template: String = ""
    var created: String = ""
    var updated: String = ""
}

@Serializable
data class ItemSpecificationTemplate(
    val name: String,
    val type: String,
    val allow_multiple: Boolean
)

@Serializable
class Item {
    var id: Int = 0
    var name: String = ""
    var inv_key: String = ""
    var hardware: Int = 0
    var group: Int = 0
    var status: Int = 0
    var owner: String = ""
    var place: Int = 0
    var available: Boolean = false
    var specifications: String = ""
    var created: String = ""
    var updated: String = ""
}

@Serializable
class Terminal {
    var id: Int = 0
    var name: String = ""
    var created: String = ""
    var updated: String = ""
}

@Serializable
data class Section(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var description: String = "",
    var room: Int = 0,
    var created: String = "",
    var updated: String = ""
)

@Serializable
data class Room(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var lab: Int = 0,
    var building: Int = 0,
    var type: Int = 0,
    var created: String = "",
    var updated: String = ""
)