package com.example.alpha.data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alpha.data.api.Laboratory

@Dao
interface LaboratoryDao {
    @Query("SELECT * FROM laboratories")
    fun getAll(): List<com.example.alpha.data.api.Laboratory>

    @Query("SELECT * FROM laboratories WHERE id = :id")
    fun getById(id: Int): com.example.alpha.data.api.Laboratory?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(laboratory: com.example.alpha.data.api.Laboratory)

    @Update
    fun update(laboratory: com.example.alpha.data.api.Laboratory)

    @Delete
    fun delete(laboratory: com.example.alpha.data.api.Laboratory)
}