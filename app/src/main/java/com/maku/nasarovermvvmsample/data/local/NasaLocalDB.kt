package com.maku.nasarovermvvmsample.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maku.nasarovermvvmsample.data.local.db.dao.NasaRoverDao
import com.maku.nasarovermvvmsample.data.local.db.convertos.CameraConverters
import com.maku.nasarovermvvmsample.data.local.db.convertos.LIstConvertor
import com.maku.nasarovermvvmsample.data.local.db.convertos.RoverConverters
import com.maku.nasarovermvvmsample.data.local.db.entities.NasaRover

@Database(
    entities = [NasaRover::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoverConverters::class, CameraConverters::class, LIstConvertor::class)
abstract class NasaLocalDB: RoomDatabase() {
    abstract fun nasaRoverDao(): NasaRoverDao

    companion object {
        @Volatile private var instance: NasaLocalDB? = null //all threads will have immediate access to this property - volatile
        private val LOCK = Any() //dummy object to make sure no two threads are doing the same thing

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                NasaLocalDB::class.java, "NasaRoverDB.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}