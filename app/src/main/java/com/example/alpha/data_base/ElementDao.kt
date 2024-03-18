package com.example.alpha.data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alpha.data.api.Element

@Dao
interface ElementDao {
    @Query("SELECT * FROM elements")
    fun getAll(): List<com.example.alpha.data.api.Element>

    @Query("SELECT * FROM elements WHERE id = :id")
    fun getById(id: Int): com.example.alpha.data.api.Element?

    @Query("SELECT * FROM elements WHERE labId = :labId")
    fun getByLabId(labId: Int): List<com.example.alpha.data.api.Element>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(element: com.example.alpha.data.api.Element)

    @Update
    fun update(element: com.example.alpha.data.api.Element)

    @Delete
    fun delete(element: com.example.alpha.data.api.Element)
}