package com.maku.nasarovermvvmsample.data.local.db.convertos

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.maku.nasarovermvvmsample.data.model.Rover


class RoverConverters {

    @TypeConverter
    fun roverToString(rover: Rover): String = Gson().toJson(rover)

    @TypeConverter
    fun stringToRover(string: String): Rover = Gson().fromJson(string, Rover::class.java)

}