package com.maku.nasarovermvvmsample.ui.Photo.Photodetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maku.nasarovermvvmsample.R
import com.maku.nasarovermvvmsample.data.model.Photo
import com.maku.nasarovermvvmsample.utils.localcouroutinescope.ScopedFragment
import timber.log.Timber

class PhotoDetailFragment : ScopedFragment() {

    private lateinit var photo: Photo
    private var shortAnimationDuration: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        loadArguments()
    }

    private fun loadArguments() {
        arguments?.getStringArrayList("photo")?.let {
            photo
            Timber.d("photoooo " + photo)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_detail, container, false)
    }

    companion object {
        private val TAG = PhotoDetailFragment::class.java.name
        const val PHOTO_ARG = "PHOTO_ARG"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PhotoDetailFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}