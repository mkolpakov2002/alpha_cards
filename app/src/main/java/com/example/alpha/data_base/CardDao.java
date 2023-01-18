package com.example.alpha.data_base;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.alpha.data.Card;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface CardDao {
    // Добавление в бд
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Card... item);

//    // Удаление из бд
//    @Query("DELETE FROM cards WHERE cardId = :id")
//    void delete(int id);
//
//    @Query("DELETE FROM cards")
//    void deleteAll();

    @Update
    void update(Card item);

    // Получение всех Person из бд
    @Query("SELECT * FROM cards")
    LiveData<List<Card>> getAll();
}