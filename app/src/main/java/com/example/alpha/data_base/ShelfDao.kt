package com.example.alpha.data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alpha.data.api.Shelf

@Dao
interface ShelfDao {
    @Query("SELECT * FROM shelves")
    fun getAll(): List<com.example.alpha.data.api.Shelf>

    @Query("SELECT * FROM shelves WHERE id = :id")
    fun getById(id: Int): com.example.alpha.data.api.Shelf?

    @Query("SELECT * FROM shelves WHERE roomId = :roomId")
    fun getByRoomId(roomId: Int): List<com.example.alpha.data.api.Shelf>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(shelf: com.example.alpha.data.api.Shelf)

    @Update
    fun update(shelf: com.example.alpha.data.api.Shelf)

    @Delete
    fun delete(shelf: com.example.alpha.data.api.Shelf)
}