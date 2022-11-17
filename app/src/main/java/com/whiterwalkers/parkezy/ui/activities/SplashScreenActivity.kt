package com.whiterwalkers.parkezy.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.whiterwalkers.parkezy.R
import com.whiterwalkers.parkezy.ui.utils.DataStore
import com.whiterwalkers.parkezy.ui.utils.REQUEST_TURN_DEVICE_LOCATION_ON
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    val TAG = SplashScreenActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Log.d(TAG, "onCreate")
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA
            )
        )
    }

    private fun loadNextScreen() {
        Log.d(TAG, "loadNextScreen")
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            val action = if (DataStore.isLoggedIn()) {
                MainActivity::class.java.canonicalName
            } else {
                LoginActivity::class.java.canonicalName
            }
            Log.d(TAG, "action $action")
            startActivity(Intent(action))
            finish()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                when {
                    permissions.getOrDefault(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        false
                    ) || permissions.getOrDefault(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        false
                    ) || permissions.getOrDefault(
                        Manifest.permission.CAMERA, false
                    ) -> {
                        Log.d(TAG, "request permission approved")
                        enableLocation()
                    }
                    else -> {
                        Log.d(TAG, "request permission not approved")
                    }
                }
            }

        }

    private fun enableLocation() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            Log.d(TAG, "location setting success")
            loadNextScreen()
        }
        task.addOnCompleteListener {
            Log.d(TAG, "location setting completed")
        }
        task.addOnFailureListener { exception ->
            Log.d(TAG, "location setting failure ${exception.message}")
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(
                        this@SplashScreenActivity,
                        REQUEST_TURN_DEVICE_LOCATION_ON
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                    Log.d(TAG, "catch :: ${sendEx.message}")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "onActivity Result $requestCode")
        loadNextScreen()
    }
}