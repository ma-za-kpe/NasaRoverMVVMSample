package com.maku.nasarovermvvmsample.ui.testviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maku.nasarovermvvmsample.data.model.Photo
import com.maku.nasarovermvvmsample.data.repo.NasaRepository
import com.maku.nasarovermvvmsample.data.repo.TestRepo
import com.maku.nasarovermvvmsample.utils.sealed.ResponseState
import timber.log.Timber

class TestViewModel(private val testRepo: TestRepo) : ViewModel()  {

    suspend fun testPhotos() {
        testRepo.getNasaRoverData()
    }

}