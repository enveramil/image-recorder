package com.enveramil.imagerecorder.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.enveramil.imagerecorder.adapter.ImageApiAdapter
import com.enveramil.imagerecorder.adapter.ImageListedAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val imageRecyclerAdapter : ImageListedAdapter,
    private val glide : RequestManager,
    private val imageApiAdapter : ImageApiAdapter
) : FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImageListedFragment::class.java.name -> ImageListedFragment(imageRecyclerAdapter)
            ImageDetailsFragment::class.java.name -> ImageDetailsFragment(glide)
            ImageAPIFragment::class.java.name -> ImageAPIFragment(imageApiAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}