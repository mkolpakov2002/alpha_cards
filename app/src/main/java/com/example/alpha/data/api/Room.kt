package com.example.alpha.data.api

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "rooms")
data class Room(
    @PrimaryKey val id: Int,
    val name: String,
    val labId: Int,
    val buildingId: Int,
    val typeId: Int,
    val created: String,
    val updated: String
)