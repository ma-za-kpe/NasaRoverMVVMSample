package com.maku.nasarovermvvmsample.ui.Photo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.maku.nasarovermvvmsample.R
import com.maku.nasarovermvvmsample.data.local.db.entities.NasaRover
import com.maku.nasarovermvvmsample.data.model.Photo
import com.maku.nasarovermvvmsample.databinding.PhotoFragmentBinding
import com.maku.nasarovermvvmsample.ui.viewmodel.MainViewModelFactory
import com.maku.nasarovermvvmsample.utils.localcouroutinescope.ScopedFragment
import com.maku.nasarovermvvmsample.utils.list.InfiniteScrollListener
import com.maku.nasarovermvvmsample.utils.sealed.NetworkState
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import timber.log.Timber
import java.io.IOException

class PhotoFragment : ScopedFragment(), KodeinAware {

    companion object {
        fun newInstance() = PhotoFragment()
    }

    override val kodein by closestKodein()

    private val viewModelFactory: MainViewModelFactory by instance()
    private var viewModel: PhotoViewModel? = null
    private lateinit var binding: PhotoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.photo_fragment, container, false)
        binding.lifecycleOwner = requireActivity()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //check internet availability before doin any of this...

        initUiBindings()
        initViewModels()
        initObservers()
    }

    private fun initUiBindings() {
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.homePhotosList.run {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
    }

    private fun initObservers() = launch {

        val todayWeather = viewModel?.nasa?.await()
        todayWeather?.observe(viewLifecycleOwner, Observer { result ->
            if (result == null){
                Timber.d("data is null AF")
                return@Observer
            } //return from observer becauce the db could be empty
            Timber.d("nasa today %s", result.toString())
            fetchNasaData(result)
//            when (arrayList as ArrayList<Photo>) {
//                is ResponseState.Success<*> -> {
//                    Timber.d("result: " + arrayList)
//                    renderList(arrayList)
//                    binding.homePhotosProgressContainer.visibility = View.GONE
//                    binding.homePhotosList.visibility = View.VISIBLE
//                }
//                is ResponseState.InProgress -> {
//                    binding.homePhotosProgressContainer.visibility = View.VISIBLE
//                    binding.homePhotosList.visibility = View.GONE
//                }
//                is ResponseState.Error -> {
//                    binding.homePhotosProgressContainer.visibility = View.GONE
//                    Toast.makeText(activity, result.exception.message, Toast.LENGTH_LONG).show()
//                }
//            }
        })
    }

    private fun fetchNasaData(arrayList: NasaRover) : NetworkState {
            return try {
                if (arrayList != null) {
                    Timber.d("nasa not nulllllllllll %s", arrayList)
                    val photos = arrayList.photos
                    renderList(photos as ArrayList<Photo>)
                    NetworkState.Success(arrayList)
                } else {
                    NetworkState.InvalidData
                }

            } catch (error : IOException) {
                NetworkState.NetworkException(error.message!!)
            }
        }

    private fun renderList(data: ArrayList<Photo>) {
        if (data.isNotEmpty()) {
            //when screen starts
            if (viewModel?.getCurrentPage() == 1 || binding.homePhotosList.adapter?.itemCount == 0) {
                setRecyclerData(data)
            } else { //when load more
                if (binding.homePhotosList.adapter == null) { //after load more
                    setRecyclerData(data)
                }
                binding.homePhotosList.adapter?.notifyDataSetChanged()
            }
            //load state of rv
            if (viewModel?.listState != null) {
                binding.homePhotosList.layoutManager?.onRestoreInstanceState(viewModel?.listState)
                viewModel?.listState = null
            }
        } else {
            showSnackBarMessage()
        }
    }

    private fun setRecyclerData(photos: ArrayList<Photo>) {
        with(binding.homePhotosList) {
            adapter = PhotosAdapter(photos)
            addOnScrollListener(
                InfiniteScrollListener({
                   launch {
                       fetchMoreData()
                   }
                },
                layoutManager as LinearLayoutManager)
            )
        }
    }

    private suspend fun fetchMoreData() {
        viewModel?.loadDataNextPage()
    }

    private fun showSnackBarMessage() {
        Toast.makeText(requireContext(), "No more data", Toast.LENGTH_SHORT).show()
    }

    private fun initViewModels()= launch {
        if (null == viewModel) {
            //presavation of viewmodels is a job of the viewmodelprovider
            viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(PhotoViewModel::class.java)
        }
    }

}