package com.example.alpha.data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alpha.data.api.Cell

@Dao
interface CellDao {
    @Query("SELECT * FROM cells")
    fun getAll(): List<com.example.alpha.data.api.Cell>

    @Query("SELECT * FROM cells WHERE id = :id")
    fun getById(id: Int): com.example.alpha.data.api.Cell?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cell: com.example.alpha.data.api.Cell)

    @Update
    fun update(cell: com.example.alpha.data.api.Cell)

    @Delete
    fun delete(cell: com.example.alpha.data.api.Cell)
}