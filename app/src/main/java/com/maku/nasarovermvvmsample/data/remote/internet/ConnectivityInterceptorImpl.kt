package com.maku.nasarovermvvmsample.data.remote.internet

import android.content.Context
import android.net.ConnectivityManager
import com.maku.nasarovermvvmsample.internal.NoConnectivityException
import com.maku.nasarovermvvmsample.utils.connectivity.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class ConnectivityInterceptorImpl (context: Context) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!NetworkUtils.getNetworkLiveData(appContext).value!!){
            Timber.d("valuee %s", NetworkUtils.getNetworkLiveData(appContext).value!!)
            throw NoConnectivityException()
        }
        return chain.proceed(chain.request())
    }

//    private fun isOnline(): Boolean {
//        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
//                as ConnectivityManager
//        val networkInfo = connectivityManager.activeNetworkInfo
//        return networkInfo != null && networkInfo.isConnected
//    }
}
