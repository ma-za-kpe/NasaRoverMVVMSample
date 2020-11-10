package com.maku.nasarovermvvmsample.data.model


import com.google.gson.annotations.SerializedName

data class Rover(
    @SerializedName("id")
    val id: Int, // 5
    @SerializedName("landing_date")
    val landingDate: String, // 2012-08-06
    @SerializedName("launch_date")
    val launchDate: String, // 2011-11-26
    @SerializedName("name")
    val name: String, // Curiosity
    @SerializedName("status")
    val status: String // active
)