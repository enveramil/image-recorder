package com.enveramil.imagerecorder.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(artModel : Model)

    @Delete
    suspend fun deleteImage(deleteData : Model)

    @Query("SELECT * FROM arts")
    fun observeData() : LiveData<List<Model>>
}