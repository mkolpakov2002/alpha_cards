package com.example.alpha.data.api

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "laboratories")
data class Laboratory(
    @PrimaryKey val id: Int,
    val name: String,
    val created: String,
    val updated: String
)