package com.maku.nasarovermvvmsample.data.remote.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maku.nasarovermvvmsample.data.local.db.entities.NasaRover
import com.maku.nasarovermvvmsample.data.remote.service.NasaService
import timber.log.Timber
import java.io.IOException

class RemotePhotoDataSourceImpl(val apiService: NasaService) : RemotePhotoDataSource {
    private val _downloadedfetchNasaPhotos = MutableLiveData<NasaRover>() // can be changed
    override val downloadedfetchNasaPhotos: LiveData<NasaRover>
        get() = _downloadedfetchNasaPhotos

    override suspend fun fetchNasaPhotos() {

        try {
            val fetchedphotoData = apiService
                .getPhotos(1000, "cgpkFxlyFjej8Zf6JXfDbNi8vafuDha6vxd0JJhk", 1)
                ?.await()
            _downloadedfetchNasaPhotos.postValue(fetchedphotoData)
            Timber.d("data %s", fetchedphotoData)
        } catch (exception: IOException){
            Timber.d("exception %s", exception)
        }
    }

}
