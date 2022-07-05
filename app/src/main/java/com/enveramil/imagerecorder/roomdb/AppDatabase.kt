package com.enveramil.imagerecorder.roomdb

import androidx.room.Database

@Database(entities = [Model::class], version = 1)
abstract class AppDatabase {
    abstract fun artDao() : Dao
}