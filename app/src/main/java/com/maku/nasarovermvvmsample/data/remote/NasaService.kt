package com.maku.nasarovermvvmsample.data.remote

import com.maku.nasarovermvvmsample.data.local.db.entities.NasaRover
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

// base url : https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=cgpkFxlyFjej8Zf6JXfDbNi8vafuDha6vxd0JJhk

interface NasaService {

    @GET("api/v1/rovers/curiosity/photos")
    suspend fun getPhotos(
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int): Response<NasaRover>?

}