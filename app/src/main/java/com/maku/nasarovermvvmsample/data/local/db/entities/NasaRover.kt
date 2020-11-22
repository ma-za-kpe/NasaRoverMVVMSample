package com.maku.nasarovermvvmsample.data.local.db.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.maku.nasarovermvvmsample.data.model.Photo

const val PHOTO_ID = 0
@Entity(tableName = "photo_details")
data class NasaRover(
    @SerializedName("photos")
    val photos: List<Photo>
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = PHOTO_ID
}