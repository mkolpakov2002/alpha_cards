package com.example.alpha.data_base;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.alpha.data.Card;

@Database(entities = {Card.class /*, AnotherEntityType.class, AThirdEntityType.class */}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public abstract CardDao getCardDao();

}
