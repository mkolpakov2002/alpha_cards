package com.example.alpha.data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alpha.data.api.Request

@Dao
interface RequestDao {
    @Query("SELECT * FROM requests")
    fun getAll(): List<com.example.alpha.data.api.Request>

    @Query("SELECT * FROM requests WHERE id = :id")
    fun getById(id: Int): com.example.alpha.data.api.Request?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(request: com.example.alpha.data.api.Request)

    @Update
    fun update(request: com.example.alpha.data.api.Request)

    @Delete
    fun delete(request: com.example.alpha.data.api.Request)
}