package com.example.wishlist.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wishlist.entity.Wish

@Database(entities = [Wish::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dao(): Dao
}