package com.maku.nasarovermvvmsample.data.remote.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.maku.nasarovermvvmsample.BuildConfig
import com.maku.nasarovermvvmsample.data.local.db.entities.NasaRover
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// base url : https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=cgpkFxlyFjej8Zf6JXfDbNi8vafuDha6vxd0JJhk

interface NasaService {

    @GET("api/v1/rovers/curiosity/photos")
    fun getPhotos(
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Deferred<NasaRover>?

    companion object{
        operator fun invoke(
        ): NasaService {

            val okHttpClient = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NasaService::class.java)
        }

    }

}