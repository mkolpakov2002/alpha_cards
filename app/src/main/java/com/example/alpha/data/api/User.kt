package com.example.alpha.data.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Int,
    val name: String,
    val entity: String,
    val scope: List<String>,
    val created: String,
    val updated: String
)
