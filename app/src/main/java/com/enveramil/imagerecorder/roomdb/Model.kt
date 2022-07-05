package com.enveramil.imagerecorder.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Model(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    var name : String,
    var artistName : String,
    var year : Int,
    var imageUrl : String

) {

}