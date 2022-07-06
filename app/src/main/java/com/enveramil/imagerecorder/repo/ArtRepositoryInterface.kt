package com.enveramil.imagerecorder.repo

import androidx.lifecycle.LiveData
import com.enveramil.imagerecorder.model.ImageResponse
import com.enveramil.imagerecorder.roomdb.Model
import com.enveramil.imagerecorder.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art : Model)

    suspend fun deleteArt(art: Model)

    fun getArt() : LiveData<List<Model>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>

}