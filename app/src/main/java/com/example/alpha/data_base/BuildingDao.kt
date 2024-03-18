package com.example.alpha.data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alpha.data.api.Building

@Dao
interface BuildingDao {
    @Query("SELECT * FROM buildings")
    fun getAll(): List<Building>

    @Query("SELECT * FROM buildings WHERE id = :id")
    fun getById(id: Int): Building?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(building: Building)

    @Update
    fun update(building: Building)

    @Delete
    fun delete(building: Building)
}