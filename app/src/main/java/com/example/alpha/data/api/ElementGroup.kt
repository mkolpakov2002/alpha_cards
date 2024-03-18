package com.example.alpha.data.api

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "element_groups")
data class ElementGroup(
    @PrimaryKey val id: Int,
    val name: String,
    val statusId: Int,
    val created: String,
    val updated: String
)