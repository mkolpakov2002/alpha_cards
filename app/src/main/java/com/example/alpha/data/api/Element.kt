package com.example.alpha.data.api

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "elements")
data class Element(
    @PrimaryKey val id: Int,
    val name: String,
    val equipmentId: Int,
    val statusId: Int,
    val labId: Int,
    val created: String,
    val updated: String
)