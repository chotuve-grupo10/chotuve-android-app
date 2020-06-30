package com.example.chotuve_android_client.ui.uploadVideo

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.tools.TokenHolder
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.FirebaseApp

val RESULT_LOAD_VIDEO = 0;
val APPSPOT_URL = "gs://chotuve-android-app.appspot.com";
val PERMISSION_ID = 42
val TAG = "UploadVideoFragm"
class UploadVideoFragment : Fragment() {

    private lateinit var uploadVideoViewModel: UploadVideoViewModel
    private lateinit var alertDialogBuilder: AlertDialog.Builder
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var mLastLocation: Location? = null
    private var LATITUDE: String? = null
    private var LONGITUDE: String? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alertDialogBuilder = AlertDialog.Builder(this.getContext())
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this.context!!)

        uploadVideoViewModel =
            ViewModelProviders.of(this).get(UploadVideoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_upload, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ask for permissions
        if (!checkPermissions()) {
            askUserForPermissions()
        }

        // FireBase button
        view.findViewById<Button>(R.id.firebase_button).setOnClickListener {
            this.context?.let { it1 -> FirebaseApp.initializeApp(it1) }

            val permissions = checkPermissions()
            Log.d(TAG, "Permission is " + permissions.toString())
            if (permissions) {
                getLastLocation()
            }

            val editTitleView = view.findViewById<EditText>(R.id.editTextTitle)
            val editDescriptionView = view.findViewById<EditText>(R.id.editTextDescription)
            val publicOrPrivateVideo = view.findViewById<CheckBox>(R.id.checkBoxPrivate)
            val username = TokenHolder.username
            uploadVideoViewModel.updateValues(
                editTitleView.text.toString(),
                editDescriptionView.text.toString(),
                username,
                LATITUDE,
                LONGITUDE,
                publicOrPrivateVideo.isChecked()
            )
            getFileFromGallery();
        }
    }

    fun getFileFromGallery() {
        startActivityForResult(
            uploadVideoViewModel.getFileFromGallery(),
            RESULT_LOAD_VIDEO
        )
    }

    // For the ActivityResult:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        uploadVideoViewModel.dispatcherToFirebase(requestCode, resultCode, data, alertDialogBuilder)
    }

    @SuppressLint("WrongConstant")
    private fun checkPermissions(): Boolean {
        if (checkSelfPermission(
                this.context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(
                this.context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun askUserForPermissions() {
        // herramienta propia del Fragment (o la activity)
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Granted. Start getting the location information
                if (isLocationEnabled()) {
                    Log.d(TAG, "Permission is granted and location is enabled")
                    getLastLocation()
                } else {
                    Log.d(TAG, "Permission is granted BUT location is not enabled")
                }
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        // Depende del contexto
        var locationManager: LocationManager =
            this.context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        // El objeto FusedLocationClient depende del contexto
        mFusedLocationClient.lastLocation
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result
                    LATITUDE = mLastLocation!!.latitude.toString()
                    LONGITUDE = mLastLocation!!.longitude.toString()
                } else {
                    Log.w(TAG, "getLastLocation:exception", task.exception)

                }
            }
    }

}
