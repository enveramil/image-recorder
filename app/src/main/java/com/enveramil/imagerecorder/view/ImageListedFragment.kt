package com.enveramil.imagerecorder.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.enveramil.imagerecorder.R
import com.enveramil.imagerecorder.databinding.FragmentListImagesBinding

class ImageListedFragment : Fragment(R.layout.fragment_list_images) {
    private var fragmentBinding : FragmentListImagesBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Görünüm oluşturulduktan sonra yapılacak işlemler blogudur.
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentListImagesBinding.bind(view)
        fragmentBinding = binding

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(ImageListedFragmentDirections.actionImageListedFragmentToImageDetailsFragment())
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}