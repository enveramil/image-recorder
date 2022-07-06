package com.enveramil.imagerecorder.repo

import androidx.lifecycle.LiveData
import com.enveramil.imagerecorder.api.RetrofitAPI
import com.enveramil.imagerecorder.model.ImageResponse
import com.enveramil.imagerecorder.roomdb.Dao
import com.enveramil.imagerecorder.roomdb.Model
import com.enveramil.imagerecorder.util.Resource
import javax.inject.Inject

class ArtRepository @Inject
constructor(private val artDao : Dao,
            private val retrofitAPI: RetrofitAPI)

 : ArtRepositoryInterface {
    override suspend fun insertArt(art: Model) {
        artDao.insertImage(art)
    }

    override suspend fun deleteArt(art: Model) {
        artDao.deleteImage(art)
    }

    override fun getArt(): LiveData<List<Model>> {
        return artDao.observeData()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("no data",null)
            }else{
                Resource.error("no data",null)
            }

        }catch (e : Exception){
            Resource.error("No data!",null)
        }
    }
}