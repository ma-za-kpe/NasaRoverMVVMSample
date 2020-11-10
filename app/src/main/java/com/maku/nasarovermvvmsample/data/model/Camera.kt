package com.maku.nasarovermvvmsample.data.model


import com.google.gson.annotations.SerializedName

data class Camera(
    @SerializedName("full_name")
    val fullName: String, // Front Hazard Avoidance Camera
    @SerializedName("id")
    val id: Int, // 20
    @SerializedName("name")
    val name: String, // FHAZ
    @SerializedName("rover_id")
    val roverId: Int // 5
)