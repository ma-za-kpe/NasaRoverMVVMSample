package com.maku.nasarovermvvmsample

import android.app.Application
import com.maku.nasarovermvvmsample.data.local.NasaLocalDB
import com.maku.nasarovermvvmsample.data.remote.service.NasaService
import com.maku.nasarovermvvmsample.data.remote.datasource.RemotePhotoDataSource
import com.maku.nasarovermvvmsample.data.remote.datasource.RemotePhotoDataSourceImpl
import com.maku.nasarovermvvmsample.data.repo.NasaRepo
import com.maku.nasarovermvvmsample.data.repo.NasaRepoImpl
import com.maku.nasarovermvvmsample.ui.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import timber.log.Timber

class Nasa : Application(), KodeinAware {
    override fun onCreate() {
        super.onCreate()
        //timber
        Timber.plant(Timber.DebugTree())
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@Nasa))

        bind() from singleton { NasaLocalDB(instance()) }
        bind() from singleton { instance<NasaLocalDB>().nasaRoverDao()}

        bind() from singleton { NasaService() }
        bind<RemotePhotoDataSource>() with singleton { RemotePhotoDataSourceImpl(instance()) }

        bind<NasaRepo>() with singleton { NasaRepoImpl(instance(), instance()) }

        bind() from provider { MainViewModelFactory(instance()) }
    }

}