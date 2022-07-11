package com.enveramil.imagerecorder.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enveramil.imagerecorder.R
import com.enveramil.imagerecorder.adapter.ImageListedAdapter
import com.enveramil.imagerecorder.databinding.FragmentListImagesBinding
import com.enveramil.imagerecorder.viewmodel.ArtViewModel
import javax.inject.Inject

class ImageListedFragment @Inject constructor(
    val imageRecyclerAdapter : ImageListedAdapter
) : Fragment(R.layout.fragment_list_images) {
    private var fragmentBinding : FragmentListImagesBinding? = null
    lateinit var viewModel : ArtViewModel
    private var swipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedImage = imageRecyclerAdapter.images[layoutPosition]
            viewModel.deleteImage(selectedImage)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Görünüm oluşturulduktan sonra yapılacak işlemler blogudur.
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentListImagesBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()
        binding.recyclerView.adapter = imageRecyclerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerView)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(ImageListedFragmentDirections.actionImageListedFragmentToImageDetailsFragment())
            viewModel.setSelectedImage("")
        }
    }

    private fun subscribeToObservers(){
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            imageRecyclerAdapter.images = it
        })
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}