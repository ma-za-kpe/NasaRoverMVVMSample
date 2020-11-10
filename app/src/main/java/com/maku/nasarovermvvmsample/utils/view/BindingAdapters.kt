package com.maku.nasarovermvvmsample.utils.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.maku.nasarovermvvmsample.utils.extentions.setImageFromUrl

class BindingAdapters {
    companion object {
        @BindingAdapter("android:image_url")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, auctionImageUrl: String) {
            imageView.setImageFromUrl(auctionImageUrl)
        }
    }
}