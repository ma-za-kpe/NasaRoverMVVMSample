package com.maku.nasarovermvvmsample.data.repo

import androidx.lifecycle.LiveData
import com.maku.nasarovermvvmsample.data.local.db.dao.NasaRoverDao
import com.maku.nasarovermvvmsample.data.local.db.entities.NasaRover
import com.maku.nasarovermvvmsample.data.remote.datasource.RemotePhotoDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NasaRepoImpl(private val remotePhotoDataSource: RemotePhotoDataSource,
                   private val nasaRoverDao: NasaRoverDao
) : NasaRepo {
    companion object {
        private val TAG = NasaRepo::class.java.name
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

    override suspend fun getNasaData(): LiveData<NasaRover> {
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
        remotePhotoDataSource.fetchNasaPhotos()
    }

}