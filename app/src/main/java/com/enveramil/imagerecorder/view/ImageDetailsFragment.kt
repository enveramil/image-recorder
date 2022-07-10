package com.enveramil.imagerecorder.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.enveramil.imagerecorder.R
import com.enveramil.imagerecorder.databinding.ImageDetailsBinding
import javax.inject.Inject

class ImageDetailsFragment
    @Inject constructor(
        val glide : RequestManager
    )
    : Fragment(R.layout.image_details) {

    private var fragmentBinding : ImageDetailsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = ImageDetailsBinding.bind(view)
        fragmentBinding = binding

        binding.selectedImageViewFromApi.setOnClickListener {
            findNavController().navigate(ImageDetailsFragmentDirections.actionImageDetailsFragmentToImageAPIFragment())
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}