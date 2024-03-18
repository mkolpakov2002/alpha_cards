package com.example.alpha.data_base


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.alpha.data.api.Building
import com.example.alpha.data.api.Cell
import com.example.alpha.data.api.Element
import com.example.alpha.data.api.ElementGroup
import com.example.alpha.data.api.Equipment
import com.example.alpha.data.api.Laboratory
import com.example.alpha.data.api.Room
import com.example.alpha.data.api.Shelf
import com.example.alpha.data.api.Terminal
import com.example.alpha.data.api.User

@Database(entities = [com.example.alpha.data.api.Equipment::class, com.example.alpha.data.api.Element::class, com.example.alpha.data.api.ElementGroup::class,
    com.example.alpha.data.api.Laboratory::class, Building::class, com.example.alpha.data.api.Room::class, com.example.alpha.data.api.Shelf::class,
    com.example.alpha.data.api.Cell::class, com.example.alpha.data.api.Terminal::class, com.example.alpha.data.api.User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun equipmentDao(): EquipmentDao
    abstract fun elementDao(): ElementDao
    abstract fun elementGroupDao(): ElementGroupDao
    abstract fun requestDao(): RequestDao
    abstract fun laboratoryDao(): LaboratoryDao
    abstract fun buildingDao(): BuildingDao
    abstract fun roomDao(): RoomDao
    abstract fun shelfDao(): ShelfDao
    abstract fun cellDao(): CellDao
    abstract fun terminalDao(): TerminalDao
    abstract fun userDao(): UserDao
}
