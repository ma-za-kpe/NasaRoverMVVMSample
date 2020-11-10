package com.maku.nasarovermvvmsample.data.local.db.convertos

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.maku.nasarovermvvmsample.data.model.Photo

class LIstConvertor {

    @TypeConverter
    fun listToJson(value: List<Photo>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Photo>::class.java).toList()
}