package com.maku.nasarovermvvmsample.data.repo

import androidx.lifecycle.LiveData
import com.maku.nasarovermvvmsample.data.local.db.dao.NasaRoverDao
import com.maku.nasarovermvvmsample.data.local.db.entities.NasaRover
import com.maku.nasarovermvvmsample.data.remote.RemotePhotoDataSource
import com.maku.nasarovermvvmsample.data.remote.RemotePhotoDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TestRepo (private val remotePhotoDataSource: RemotePhotoDataSourceImpl,
                private val nasaRoverDao: NasaRoverDao
) {

    companion object {
        private val TAG = NasaRepository::class.java.name
        const val GENERAL_ERROR_CODE = 499
    }

    init {
        //observe forever because repos do not have a lifecycle
        remotePhotoDataSource.downloadedfetchNasaPhotos.observeForever{newphotos ->
            //persist the weather data in room (CACHING)
            persistFetchedPhotos(newphotos)
        }
    }

    private fun persistFetchedPhotos(newphotos: NasaRover) {
        //Global scope doesnt return anything, and lifecycle changes dont affect it
        GlobalScope.launch(Dispatchers.IO) {
            nasaRoverDao.upsert(newphotos)
        }
    }

    suspend fun getNasaRoverData(): LiveData<NasaRover> {
        initRoverData()
        //withcontext returns something
        return withContext(Dispatchers.IO) {
            return@withContext nasaRoverDao.getNasaRoverPhotos()
        }
    }

    // network call which will initiate the first cashing of data inside the database
    private suspend fun initRoverData() {
        fetchPhoto()
    }

    //
    private suspend fun fetchPhoto() {
        remotePhotoDataSource.fetchNasaPhotos(1000, "cgpkFxlyFjej8Zf6JXfDbNi8vafuDha6vxd0JJhk", 1)
    }

}