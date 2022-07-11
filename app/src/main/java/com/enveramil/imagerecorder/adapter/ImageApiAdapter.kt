package com.enveramil.imagerecorder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.enveramil.imagerecorder.R
import com.enveramil.imagerecorder.roomdb.Model
import com.enveramil.imagerecorder.viewmodel.ArtViewModel
import javax.inject.Inject

class ImageApiAdapter @Inject constructor(var glide : RequestManager)
    : RecyclerView.Adapter<ImageApiAdapter.ImageApiHolder>() {

    class ImageApiHolder(itemView : View) : RecyclerView.ViewHolder(itemView){}

    private var onClickItemListener : ((String) -> Unit) ?= null
    private var viewModel : ArtViewModel?=null

    private val diffUtil = object : DiffUtil.ItemCallback<String>(){
        // İki liste arasındaki farklılıkları karşılaştırır.

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return  oldItem == newItem
        }

    }

    private val listDiffer = AsyncListDiffer(this, diffUtil)

    var allImages : List<String>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageApiHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_api_row,parent,false)
        return ImageApiHolder(view)
    }

    fun setOnItemClickListener(listener : (String)-> Unit){
        onClickItemListener = listener
    }

    override fun onBindViewHolder(holder: ImageApiHolder, position: Int) {
        var imageView = holder.itemView.findViewById<ImageView>(R.id.onlyImageView)
        val url = allImages[position]
        holder.itemView.apply {
           glide.load(url).into(imageView)
            setOnClickListener {
                onClickItemListener?.let {
                    it(url)

                }
            }

        }



    }

    override fun getItemCount(): Int {
        return allImages.size
    }
}