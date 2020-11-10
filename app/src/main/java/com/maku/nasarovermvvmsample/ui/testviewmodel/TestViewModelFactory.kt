package com.maku.nasarovermvvmsample.ui.testviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maku.nasarovermvvmsample.data.local.db.dao.NasaRoverDao
import com.maku.nasarovermvvmsample.data.remote.NasaService
import com.maku.nasarovermvvmsample.data.remote.RemotePhotoDataSourceImpl
import com.maku.nasarovermvvmsample.data.repo.NasaRepository
import com.maku.nasarovermvvmsample.data.repo.TestRepo
import com.maku.nasarovermvvmsample.ui.Photo.PhotoViewModel

class TestViewModelFactory (private val remotePhotoDataSource: RemotePhotoDataSourceImpl,
                            private val nasaRoverDao: NasaRoverDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            return TestViewModel(
                TestRepo(remotePhotoDataSource, nasaRoverDao)
            ) as T

        }
        throw IllegalArgumentException("Unknown class name")
    }
}