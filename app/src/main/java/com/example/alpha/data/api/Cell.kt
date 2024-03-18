package com.example.alpha.data.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cells")
data class Cell(
    @PrimaryKey val id: Int,
    val name: String,
    val shelfId: Int,
    val created: String,
    val updated: String
)