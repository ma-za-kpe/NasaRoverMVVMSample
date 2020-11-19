package com.maku.nasarovermvvmsample.data.remote.datasource

import androidx.lifecycle.LiveData
import com.maku.nasarovermvvmsample.data.local.db.entities.NasaRover
import com.maku.nasarovermvvmsample.data.model.Photo

interface RemotePhotoDataSource {
    val downloadedfetchNasaPhotos: LiveData<NasaRover> // Live data data type, which can be observed for change in the repository class.

    suspend fun fetchNasaPhotos( //Asynchronous code ??? Runs separate from the main code.
)
}