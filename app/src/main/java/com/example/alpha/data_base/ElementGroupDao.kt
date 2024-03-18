package com.example.alpha.data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alpha.data.api.ElementGroup

@Dao
interface ElementGroupDao {
    @Query("SELECT * FROM element_groups")
    fun getAll(): List<com.example.alpha.data.api.ElementGroup>

    @Query("SELECT * FROM element_groups WHERE id = :id")
    fun getById(id: Int): com.example.alpha.data.api.ElementGroup?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(elementGroup: com.example.alpha.data.api.ElementGroup)

    @Update
    fun update(elementGroup: com.example.alpha.data.api.ElementGroup)

    @Delete
    fun delete(elementGroup: com.example.alpha.data.api.ElementGroup)
}