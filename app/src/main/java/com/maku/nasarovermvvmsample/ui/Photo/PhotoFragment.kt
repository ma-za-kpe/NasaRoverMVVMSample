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
import com.maku.nasarovermvvmsample.data.model.Photo
import com.maku.nasarovermvvmsample.data.remote.NasaService
import com.maku.nasarovermvvmsample.data.remote.RetrofitService
import com.maku.nasarovermvvmsample.databinding.PhotoFragmentBinding
import com.maku.nasarovermvvmsample.ui.MainViewModelFactory
import com.maku.nasarovermvvmsample.utils.sealed.ResponseState
import com.maku.nasarovermvvmsample.utils.couroutinescope.ScopedFragment
import com.maku.nasarovermvvmsample.utils.list.InfiniteScrollListener
import kotlinx.coroutines.launch
import timber.log.Timber

class PhotoFragment : ScopedFragment() {

    companion object {
        fun newInstance() = PhotoFragment()
    }

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
        viewModel?.nasaPhotos()?.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResponseState.Success -> {
                    Timber.d("result: " + result)
                    renderList(result.data)
                    binding.homePhotosProgressContainer.visibility = View.GONE
                    binding.homePhotosList.visibility = View.VISIBLE
                }
                is ResponseState.InProgress -> {
                    binding.homePhotosProgressContainer.visibility = View.VISIBLE
                    binding.homePhotosList.visibility = View.GONE
                }
                is ResponseState.Error -> {
                    binding.homePhotosProgressContainer.visibility = View.GONE
                    Toast.makeText(activity, result.exception.message, Toast.LENGTH_LONG).show()
                }
            }
        })
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
            viewModel = ViewModelProvider(requireActivity(),
                MainViewModelFactory(RetrofitService.createService(NasaService::class.java))
            ).get(
                PhotoViewModel::class.java)
            viewModel!!.nasaPhotos()
        }
    }

}