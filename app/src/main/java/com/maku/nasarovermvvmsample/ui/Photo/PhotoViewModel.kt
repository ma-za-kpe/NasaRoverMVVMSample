package com.maku.nasarovermvvmsample.ui.Photo

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maku.nasarovermvvmsample.data.model.Photo
import com.maku.nasarovermvvmsample.data.repo.NasaRepository
import com.maku.nasarovermvvmsample.utils.sealed.ResponseState
import timber.log.Timber

class PhotoViewModel(private val nasaRepository: NasaRepository) : ViewModel() {

    private val photos = MutableLiveData<ResponseState<ArrayList<Photo>>>()
    private var currentPage = 1
    var listState: Parcelable? = null //to save and restore rv's adapter

    fun getCurrentPage() = currentPage

    suspend fun nasaPhotos(): MutableLiveData<ResponseState<ArrayList<Photo>>> {
        if (currentPage == 1) {
            photos.postValue(ResponseState.InProgress)
        }

        val response = nasaRepository.getPhotosFromApi(1000, currentPage)
        Timber.d("response: " + response)

        response.let {
            when(it){
                is ResponseState.Success -> {
                    val photosList = it.extractData
                    Timber.d("response: " + photosList)
                    photosList?.let { result ->
                        if (currentPage == 1) { //set photos for first page
                            photos.postValue(ResponseState.Success(result))
                        } else { //add photos to current list
                            val currentPhotos: ArrayList<Photo>? = photos.value?.extractData
                            if (currentPhotos == null || currentPhotos.isEmpty()) {
                                photos.postValue(ResponseState.Success(result))
                            } else {
                                currentPhotos.addAll(result)
                                photos.postValue(ResponseState.Success(currentPhotos))
                            }
                        }
                    }
                }
                else -> {
                    Timber.d("error in: " + it)
                    photos.postValue(it)
                }
            }
        }
     return photos
    }

    suspend fun loadDataNextPage() {
        currentPage++
        nasaPhotos()
    }
}