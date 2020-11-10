package com.maku.nasarovermvvmsample.ui.Photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maku.nasarovermvvmsample.data.model.Photo
import com.maku.nasarovermvvmsample.databinding.RowPhotosBinding
import com.maku.nasarovermvvmsample.utils.extentions.setImageFromUrlWithProgressBar
import timber.log.Timber
import java.net.URL


class PhotosAdapter(private val photosList: ArrayList<Photo>) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowPhotosBinding.inflate(inflater, parent, false)
        return PhotosViewHolder(binding)
    }

    override fun getItemCount() = photosList.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(photosList[position])
    }

    class PhotosViewHolder(rowBinding: RowPhotosBinding) :
        RecyclerView.ViewHolder(rowBinding.root) {
        private val binding = rowBinding

        fun bind(photo: Photo) {
            Timber.d("photo ${photo.imgSrc}")
            binding.photoBinding = photo
            binding.executePendingBindings()
            binding.rowPhotoRoverImg.setImageFromUrlWithProgressBar(
                photo.imgSrc,
                binding.rowPhotoRoverProgress
            )
            binding.rowPhotoRoverImg.setOnClickListener { view ->
                Timber.d("photo has been clicked")
            }
        }
    }
}