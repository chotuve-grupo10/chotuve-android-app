package com.example.chotuve_android_client

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAG = "MyFirebaseMS"

    override fun onNewToken(token : String) {
        super.onNewToken(token)
        Log.d(TAG, "This is my new token for ${TAG} ${token}")
    }

}