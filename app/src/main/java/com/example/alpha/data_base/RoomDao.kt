package com.example.alpha.data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alpha.data.api.Room

@Dao
interface RoomDao {
    @Query("SELECT * FROM rooms")
    fun getAll(): List<com.example.alpha.data.api.Room>

    @Query("SELECT * FROM rooms WHERE id = :id")
    fun getById(id: Int): com.example.alpha.data.api.Room?

    @Query("SELECT * FROM rooms WHERE labId = :labId")
    fun getByLabId(labId: Int): List<com.example.alpha.data.api.Room>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(room: com.example.alpha.data.api.Room)

    @Update
    fun update(room: com.example.alpha.data.api.Room)

    @Delete
    fun delete(room: com.example.alpha.data.api.Room)
}