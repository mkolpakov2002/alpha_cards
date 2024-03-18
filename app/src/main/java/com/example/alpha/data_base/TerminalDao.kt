package com.example.alpha.data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alpha.data.api.Terminal

@Dao
interface TerminalDao {
    @Query("SELECT * FROM terminals")
    fun getAll(): List<com.example.alpha.data.api.Terminal>

    @Query("SELECT * FROM terminals WHERE id = :id")
    fun getById(id: Int): com.example.alpha.data.api.Terminal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(terminal: com.example.alpha.data.api.Terminal)

    @Update
    fun update(terminal: com.example.alpha.data.api.Terminal)

    @Delete
    fun delete(terminal: com.example.alpha.data.api.Terminal)
}