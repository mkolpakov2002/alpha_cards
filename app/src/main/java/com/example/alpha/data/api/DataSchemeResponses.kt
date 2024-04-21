package com.example.alpha.data.api

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val user: User,
    val jwt_token: String
)

@Serializable
class HardwareListResponse(
    val page: Int,
    val per_page: Int,
    val total_pages: Int,
    val total_items: Int,
    val result: List<Hardware>
)

@Serializable
data class HardwareCreateResponse(
    val id: Int,
    val name: String,
    val type: Int,
    val image_link: String,
    val specifications: Map<String, String>,
    val item_specifications_template: List<ItemSpecificationTemplate>,
    val created: String,
    val updated: String
)

@Serializable
data class HardwareRemainsResponse(
    val page: Int,
    val per_page: Int,
    val total_pages: Int,
    val total_items: Int,
    val result: List<String>
)

@Serializable
data class HardwareResponse(
    val id: Int,
    val name: String,
    val type: Int,
    val image_link: String,
    val specifications: Map<String, String>,
    val item_specifications_template: List<ItemSpecificationTemplate>,
    val created: String,
    val updated: String
)

@Serializable
data class HardwareUpdateResponse(
    val id: Int,
    val name: String,
    val type: Int,
    val image_link: String,
    val specifications: Map<String, String>,
    val item_specifications_template: List<ItemSpecificationTemplate>,
    val created: String,
    val updated: String
)

@Serializable
data class DeleteResponse(val detail: String)

@Serializable
data class ItemListResponse(
    val page: Int,
    val per_page: Int,
    val total_pages: Int,
    val total_items: Int,
    val result: List<Item>
)

@Serializable
data class ItemCreateResponse(
    val id: Int,
    val name: String,
    val inv_key: String,
    val hardware: Int,
    val group: Int,
    val status: Int,
    val owner: String,
    val place: Int,
    val available: Boolean,
    val specifications: Map<String, String>,
    val created: String,
    val updated: String
)

@Serializable
data class TerminalListResponse(
    val page: Int,
    val per_page: Int,
    val total_pages: Int,
    val total_items: Int,
    val result: List<Terminal>
)

@Serializable
data class TerminalCreateResponse(
    val id: Int,
    val name: String,
    val created: String,
    val updated: String
)

@Serializable
data class TerminalResponse(
    val id: Int,
    val name: String,
    val created: String,
    val updated: String
)

@Serializable
data class TerminalUpdateResponse(
    val id: Int,
    val name: String,
    val created: String,
    val updated: String
)

@Serializable
data class TerminalDeleteResponse(
    val detail: String
)

@Serializable
data class SectionListResponse(
    val page: Int,
    val per_page: Int,
    val total_pages: Int,
    val total_items: Int,
    val result: List<Section>
)

@Serializable
data class SectionCreateResponse(
    val id: Int,
    val name: String,
    val description: String,
    val room: Int,
    val created: String,
    val updated: String
)

@Serializable
data class SectionResponse(
    val id: Int,
    val name: String,
    val description: String,
    val room: Int,
    val created: String,
    val updated: String
)

@Serializable
data class SectionUpdateResponse(
    val id: Int,
    val name: String,
    val description: String,
    val room: Int,
    val created: String,
    val updated: String
)

@Serializable
data class SectionDeleteResponse(
    val detail: String
)

@Serializable
data class RoomListResponse(
    val page: Int,
    val per_page: Int,
    val total_pages: Int,
    val total_items: Int,
    val result: List<Room>
)

@Serializable
data class RoomCreateResponse(
    val id: Int,
    val name: String,
    val lab: Int,
    val building: Int,
    val type: Int,
    val created: String,
    val updated: String
)

@Serializable
data class RoomResponse(
    val id: Int,
    val name: String,
    val lab: Int,
    val building: Int,
    val type: Int,
    val created: String,
    val updated: String
)

@Serializable
data class RoomUpdateResponse(
    val id: Int,
    val name: String,
    val lab: Int,
    val building: Int,
    val type: Int,
    val created: String,
    val updated: String
)