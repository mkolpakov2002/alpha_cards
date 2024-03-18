package com.example.alpha.data.api

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "requests")
data class Request(
    @PrimaryKey val id: Int,
    val statusId: Int,
    val labId: Int,
    val created: String,
    val updated: String
)