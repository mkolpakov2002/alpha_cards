package com.example.alpha.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    int userId;

    int userPhoneNumber;

    int userName;

    public User(){

    }

    public int getUserId() {
        return userId;
    }

    public int getUserName() {
        return userName;
    }

    public int getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(int userName) {
        this.userName = userName;
    }

    public void setUserPhoneNumber(int userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }
}
