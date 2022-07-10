package com.enveramil.imagerecorder.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.enveramil.imagerecorder.R
import com.enveramil.imagerecorder.adapter.ImageApiAdapter
import javax.inject.Inject

class ImageAPIFragment @Inject constructor(
    val imageApiAdapter : ImageApiAdapter
) : Fragment(R.layout.fragment_image_api) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}