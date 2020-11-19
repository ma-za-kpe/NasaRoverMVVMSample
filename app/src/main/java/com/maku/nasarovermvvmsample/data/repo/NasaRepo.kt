package com.maku.nasarovermvvmsample.data.repo

import androidx.lifecycle.LiveData
import com.maku.nasarovermvvmsample.data.local.db.entities.NasaRover

interface NasaRepo {
    // suspend enables us to call the function from a coroutine
    suspend fun getNasaData(): LiveData<NasaRover>
}