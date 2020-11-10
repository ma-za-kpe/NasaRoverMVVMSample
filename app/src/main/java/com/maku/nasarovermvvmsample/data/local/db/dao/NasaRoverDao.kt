package com.maku.nasarovermvvmsample.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maku.nasarovermvvmsample.data.local.db.entities.NasaRover
import com.maku.nasarovermvvmsample.data.local.db.entities.PHOTO_ID

@Dao
interface NasaRoverDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(mainEntry: NasaRover) //update or insert at the same time

    @Query("select * from photo_details where id = $PHOTO_ID")
    fun getNasaRoverPhotos(): LiveData<NasaRover>

}