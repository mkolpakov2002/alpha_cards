package com.example.alpha.data.api

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "shelves")
data class Shelf(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val roomId: Int,
    val created: String,
    val updated: String
)