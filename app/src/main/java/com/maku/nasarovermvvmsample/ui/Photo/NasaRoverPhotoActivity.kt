package com.maku.nasarovermvvmsample.ui.Photo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.maku.nasarovermvvmsample.R
import com.maku.nasarovermvvmsample.databinding.ActivityNasaRoverPhotoBinding
import com.maku.nasarovermvvmsample.utils.view.hide
import com.maku.nasarovermvvmsample.utils.view.show
import com.maku.networkutil.util.NetworkUtil

class NasaRoverPhotoActivity : AppCompatActivity() {

    //databinding
    private lateinit var mViewBinding: ActivityNasaRoverPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialize the binding
        mViewBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_nasa_rover_photo
        )

        mViewBinding.lifecycleOwner = this

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.photoFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        handleNetworkChanges()
    }

     /**
     * Observe network changes i.e. Internet Connectivity
     */
    private fun handleNetworkChanges() {
        NetworkUtil.getNetworkLiveData(applicationContext).observe(this, { isConnected ->
            if (!isConnected) {

                mViewBinding.textViewNetworkStatus.text = getString(R.string.text_no_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    alpha = 0f
                    show()
                    setBackgroundColor(
                        ContextCompat.getColor(
                        context,
                        R.color.colorStatusNotConnected
                    ))
                    animate()
                        .alpha(1f)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(null)
                }
            } else {
                mViewBinding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                mViewBinding.networkStatusLayout.apply {
                    setBackgroundColor( ContextCompat.getColor(
                        context,
                        R.color.colorStatusConnected
                    ))

                    animate()
                        .alpha(0f)
                        .setStartDelay(ANIMATION_DURATION)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        })
    }


    /**
     * Companion object
     */
    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }

}