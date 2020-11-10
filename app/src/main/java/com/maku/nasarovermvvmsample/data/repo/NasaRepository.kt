package com.maku.nasarovermvvmsample.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maku.nasarovermvvmsample.BuildConfig
import com.maku.nasarovermvvmsample.data.local.db.dao.NasaRoverDao
import com.maku.nasarovermvvmsample.data.local.db.entities.NasaRover
import com.maku.nasarovermvvmsample.data.model.Photo
import com.maku.nasarovermvvmsample.data.remote.NasaService
import com.maku.nasarovermvvmsample.data.remote.RemotePhotoDataSource
import com.maku.nasarovermvvmsample.data.repo.BaseRepository.Companion.handleException
import com.maku.nasarovermvvmsample.utils.sealed.ResponseState
import com.maku.nasarovermvvmsample.data.repo.BaseRepository.Companion.handleSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber

class NasaRepository(val apiService: NasaService) {

    private val _downloadedfetchNasaPhotos = MutableLiveData<NasaRover>() // can be changed
     val downloadedfetchNasaPhotos: LiveData<NasaRover>
        get() = _downloadedfetchNasaPhotos

    companion object {
        private val TAG = NasaRepository::class.java.name
        const val GENERAL_ERROR_CODE = 499
    }

    private var apiKey: String = BuildConfig.API_KEY

    suspend fun getPhotosFromApi(sol: Int, page: Int): ResponseState<ArrayList<Photo>>? {
        var result: ResponseState<ArrayList<Photo>> = handleSuccess(arrayListOf())
        try {
            val response = apiService.getPhotos(sol, apiKey, page)
            response?.let {
                it.body()?.photos?.let { photosResponse ->
                    val arrayList = ArrayList(photosResponse)
                    result = handleSuccess(arrayList)
                    Timber.d("response: " + result)
                }
                it.errorBody()?.let { responseErrorBody ->
                    if (responseErrorBody is HttpException) {
                        responseErrorBody.response()?.code()?.let { errorCode ->
                            result = handleException(errorCode)
                            Timber.e("error: " + responseErrorBody)
                        }
                    } else result = handleException(GENERAL_ERROR_CODE)
                }
            }
        } catch (error: Exception) {
            Timber.e("Error: ${error.message}")
            return null
        }
        return result
    }

}