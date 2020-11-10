package com.maku.nasarovermvvmsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.maku.nasarovermvvmsample.R
import com.maku.nasarovermvvmsample.data.local.db.dao.NasaRoverDao
import com.maku.nasarovermvvmsample.data.remote.NasaService
import com.maku.nasarovermvvmsample.data.remote.RemotePhotoDataSourceImpl
import com.maku.nasarovermvvmsample.data.remote.RetrofitService
import com.maku.nasarovermvvmsample.ui.Photo.PhotoViewModel
import com.maku.nasarovermvvmsample.ui.testviewmodel.TestViewModel
import com.maku.nasarovermvvmsample.ui.testviewmodel.TestViewModelFactory
import com.maku.nasarovermvvmsample.utils.couroutinescope.ScopedActivity
import kotlinx.coroutines.launch
import timber.log.Timber

class TestActivity : ScopedActivity() {

    private var viewModel: TestViewModel? = null
    private var dao: NasaRoverDao? = null
    private val viewModelFactory: TestViewModelFactory = TestViewModelFactory(RemotePhotoDataSourceImpl(RetrofitService.createService(NasaService::class.java)),
        dao!!
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initViewModels()

       launch {
           test()
       }
    }

    private suspend fun test() {
        val testPhoto = viewModel?.testPhotos()
        Timber.d("test photos today %s", testPhoto)

    }

    private fun initViewModels() {
        if (null == viewModel) {
            viewModel = ViewModelProvider(this, viewModelFactory).get(TestViewModel::class.java)
        }
    }
}