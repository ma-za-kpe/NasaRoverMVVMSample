package com.maku.nasarovermvvmsample.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("camera")
    val camera: Camera,
    @SerializedName("earth_date")
    val earthDate: String, // 2015-05-30
    @SerializedName("id")
    val id: Int, // 102693
    @SerializedName("img_src")
    val imgSrc: String, // http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG
    @SerializedName("rover")
    val rover: Rover,
    @SerializedName("sol")
    val sol: Int // 1000
)