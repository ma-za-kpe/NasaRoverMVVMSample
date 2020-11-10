package com.maku.nasarovermvvmsample.data.local.db.convertos

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.maku.nasarovermvvmsample.data.model.Camera

class CameraConverters {

    @TypeConverter
    fun cameraToString(camera: Camera): String = Gson().toJson(camera)

    @TypeConverter
    fun stringToCamera(string: String): Camera = Gson().fromJson(string, Camera::class.java)

}