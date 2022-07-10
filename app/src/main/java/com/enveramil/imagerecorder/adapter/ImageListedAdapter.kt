package com.enveramil.imagerecorder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.enveramil.imagerecorder.R
import com.enveramil.imagerecorder.roomdb.Model
import javax.inject.Inject

class ImageListedAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<ImageListedAdapter.ImageListedViewHolder>() {

    class ImageListedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    private val diffUtil = object : DiffUtil.ItemCallback<Model>(){
        // İki liste arasındaki farklılıkları karşılaştırır.

        override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
            return  oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
            return  oldItem == newItem
        }

    }

    private val listDiffer = AsyncListDiffer(this, diffUtil)

    var images : List<Model>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_row,parent,false)
        return ImageListedViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageListedViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageViewFromApi)
        val nameTextView = holder.itemView.findViewById<TextView>(R.id.nameOfImageTextView)
        val artistTextView = holder.itemView.findViewById<TextView>(R.id.artistNameOfImageTextView)
        val yearTextView = holder.itemView.findViewById<TextView>(R.id.yearOfImageTextView)
        val images = images[position]
        holder.itemView.apply {
            nameTextView.text = "Name: ${images.name}"
            artistTextView.text = "Artist Name: ${images.artistName}"
            yearTextView.text = "Year: ${images.year}"
            glide.load(images.imageUrl).into(imageView)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

}