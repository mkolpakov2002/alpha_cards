package com.example.alpha.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "card")
public class Card {
    @PrimaryKey(autoGenerate = true)
    int cardId;

    public Card(){

    }
    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getCardId() {
        return cardId;
    }
}
