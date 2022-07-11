package com.enveramil.imagerecorder.view

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.enveramil.imagerecorder.R
import com.enveramil.imagerecorder.adapter.ImageApiAdapter
import com.enveramil.imagerecorder.databinding.FragmentImageApiBinding
import com.enveramil.imagerecorder.util.Status
import com.enveramil.imagerecorder.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageAPIFragment @Inject constructor(
    private val imageApiAdapter : ImageApiAdapter
) : Fragment(R.layout.fragment_image_api) {

    lateinit var viewModel : ArtViewModel
    private var fragmentBinding : FragmentImageApiBinding ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        // Kullanıcı search işlemi kısmına yazı yazmaya başladığında kendiliğinden arama yapması lazım
        var job : Job?=null
        binding.searchEditText.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.searchImage(it.toString())
                    }
                }
            }
        }
        subscribeToObserver()

        // Attach to recyclerView
        binding.imageRecyclerView.adapter = imageApiAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        imageApiAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }
    }



    private fun subscribeToObserver(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map {
                        imageResult ->
                        imageResult.previewURL
                    }

                    imageApiAdapter.allImages = urls ?: listOf()

                    fragmentBinding?.progressBar?.visibility = View.GONE


                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                    fragmentBinding?.progressBar?.visibility = View.GONE

                }

                Status.LOADING -> {
                    fragmentBinding?.progressBar?.visibility = View.VISIBLE

                }

            }
        })
    }


}