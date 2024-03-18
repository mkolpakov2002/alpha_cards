package com.example.alpha.data.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "buildings")
data class Building(
    @PrimaryKey val id: Int,
    val name: String,
    val address: String,
    val created: String,
    val updated: String
)