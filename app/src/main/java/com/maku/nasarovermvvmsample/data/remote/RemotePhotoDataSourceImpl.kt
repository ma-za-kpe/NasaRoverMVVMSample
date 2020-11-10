package com.maku.nasarovermvvmsample.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maku.nasarovermvvmsample.data.local.db.entities.NasaRover
import retrofit2.Response
import timber.log.Timber

class RemotePhotoDataSourceImpl(val apiService: NasaService) : RemotePhotoDataSource {
    private val _downloadedfetchNasaPhotos = MutableLiveData<NasaRover>() // can be changed
    override val downloadedfetchNasaPhotos: LiveData<NasaRover>
        get() = _downloadedfetchNasaPhotos

    override suspend fun fetchNasaPhotos(sol: Int, api_key: String, page: Int) {
        val fetchedphotoData = apiService
            .getPhotos(1000, "cgpkFxlyFjej8Zf6JXfDbNi8vafuDha6vxd0JJhk", 1)
        _downloadedfetchNasaPhotos.postValue(fetchedphotoData)
        Timber.d("data " + fetchedphotoData)

    }

}

private fun <T> MutableLiveData<T>.postValue(fetchedphotoData: Response<T>?) {

}
