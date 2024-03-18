package com.example.alpha.data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alpha.data.api.Equipment

@Dao
interface EquipmentDao {
    @Query("SELECT * FROM equipment")
    fun getAll(): List<com.example.alpha.data.api.Equipment>

    @Query("SELECT * FROM equipment WHERE id = :id")
    fun getById(id: Int): com.example.alpha.data.api.Equipment?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(equipment: com.example.alpha.data.api.Equipment)

    @Update
    fun update(equipment: com.example.alpha.data.api.Equipment)

    @Delete
    fun delete(equipment: com.example.alpha.data.api.Equipment)
}