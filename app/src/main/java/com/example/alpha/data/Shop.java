package com.example.alpha.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Shop {
    @PrimaryKey(autoGenerate = true)
    int shopId;


    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }
}
