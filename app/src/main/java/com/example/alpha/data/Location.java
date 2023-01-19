package com.example.alpha.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Location {
    @PrimaryKey(autoGenerate = true)
    int locationId;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}
