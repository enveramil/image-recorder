package com.enveramil.imagerecorder.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Model::class], version = 1)
 abstract class AppDatabase : RoomDatabase() {
    abstract fun artDao() : Dao
}