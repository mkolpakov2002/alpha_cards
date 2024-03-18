package com.example.alpha.data_base

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.alpha.data.api.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAll(): List<com.example.alpha.data.api.User>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getById(id: Int): com.example.alpha.data.api.User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: com.example.alpha.data.api.User)

    @Update
    fun update(user: com.example.alpha.data.api.User)

    @Delete
    fun delete(user: com.example.alpha.data.api.User)
}