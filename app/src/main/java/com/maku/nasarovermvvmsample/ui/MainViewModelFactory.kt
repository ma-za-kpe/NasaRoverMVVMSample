package com.maku.nasarovermvvmsample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maku.nasarovermvvmsample.data.repo.NasaRepo
import com.maku.nasarovermvvmsample.ui.Photo.PhotoViewModel
//
//class MainViewModelFactory (private val apiService: NasaService) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
//            return PhotoViewModel(
//                NasaRepository(apiService)) as T
//        }
//        throw IllegalArgumentException("Unknown class name")
//    }
//}

//creates new instance of objects.
class MainViewModelFactory( private val nasaRepo: NasaRepo): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhotoViewModel(nasaRepo) as T
    }
}
