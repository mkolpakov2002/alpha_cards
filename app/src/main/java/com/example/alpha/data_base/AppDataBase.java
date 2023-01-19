package com.example.alpha.data_base;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.alpha.data.Card;
import com.example.alpha.data.Location;
import com.example.alpha.data.Shop;

@Database(entities = {Card.class , Shop.class, Location.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public abstract CardDao getCardDao();
    public abstract ShopDao getShopDao();
    public abstract LocationDao getLocationDao();

}
