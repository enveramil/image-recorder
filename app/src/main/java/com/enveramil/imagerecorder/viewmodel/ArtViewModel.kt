package com.enveramil.imagerecorder.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enveramil.imagerecorder.model.ImageResponse
import com.enveramil.imagerecorder.repo.ArtRepositoryInterface
import com.enveramil.imagerecorder.roomdb.Model
import com.enveramil.imagerecorder.util.Resource
import com.enveramil.imagerecorder.view.ImageAPIFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel
     @Inject constructor(
        private val repositoryInterface: ArtRepositoryInterface
    )
    : ViewModel() {



        val artList = repositoryInterface.getArt()

    // Image Api Fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>>
            get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl : LiveData<String>
            get() = selectedImage

    // Image Details Fragment
    private var insertArtMsg = MutableLiveData<Resource<Model>>()
    val insertMessage : LiveData<Resource<Model>>
            get() = insertArtMsg

    fun resetInsertArtMsg(){
        insertArtMsg = MutableLiveData<Resource<Model>>()
    }

    fun setSelectedImage(url : String){
        selectedImage.postValue(url)
    }

    fun deleteImage(art : Model) = viewModelScope.launch{
        repositoryInterface.deleteArt(art)
    }

    fun insertImage(art : Model) = viewModelScope.launch {
        repositoryInterface.insertArt(art)
    }

    fun makeArt(name : String, artistName : String, year : String){

        if (name.isEmpty() || artistName.isEmpty() ||year.isEmpty()){
            insertArtMsg.postValue(Resource.error("all data required",null))
            return
        }

        val yearInt = try {
            year.toInt()
        }catch (e : Exception){
            insertArtMsg.postValue(Resource.error("Year should be number",null))
            return
        }

        val art = Model(name,artistName,yearInt,selectedImage.value ?: "")
        insertImage(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))


    }








    fun searchImage(searchString : String){
        if (searchString.isEmpty()){
            return
        }
        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repositoryInterface.searchImage(searchString)
            images.value = response
        }

    }


















}