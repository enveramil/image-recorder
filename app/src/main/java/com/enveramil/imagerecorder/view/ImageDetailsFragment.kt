package com.enveramil.imagerecorder.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.enveramil.imagerecorder.R
import com.enveramil.imagerecorder.adapter.ImageApiAdapter
import com.enveramil.imagerecorder.databinding.ImageDetailsBinding
import com.enveramil.imagerecorder.util.Status
import com.enveramil.imagerecorder.viewmodel.ArtViewModel
import javax.inject.Inject

class ImageDetailsFragment
    @Inject constructor(
        val glide : RequestManager
    )
    : Fragment(R.layout.image_details) {

    private var fragmentBinding : ImageDetailsBinding? = null
    lateinit var viewModel : ArtViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = ImageDetailsBinding.bind(view)
        fragmentBinding = binding
        subscribeToObserver()

        binding.selectedImageViewFromApi.setOnClickListener {
            findNavController().navigate(ImageDetailsFragmentDirections.actionImageDetailsFragmentToImageAPIFragment())
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
        binding.saveButton.setOnClickListener {
            val nameText = binding.nameText.text.toString()
            val artistNameText = binding.artistText.text.toString()
            val yearText = binding.yearText.text.toString()
            viewModel.makeArt(nameText,artistNameText,yearText)
        }



    }

    private fun subscribeToObserver(){

        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer {
            url ->
            fragmentBinding?.let {
                // Glide ile string url'ini imageView içerisine set etmekteyiz.
                glide.load(url).into(it.selectedImageViewFromApi)
            }
        })

        viewModel.insertMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()

                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()

                }

                Status.LOADING -> {

                }
            }
        })


    }



    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}