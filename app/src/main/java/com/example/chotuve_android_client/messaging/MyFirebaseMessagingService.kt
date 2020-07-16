package com.example.chotuve_android_client.messaging

import android.util.Log
import com.example.chotuve_android_client.services.AddTokenService
import com.example.chotuve_android_client.tools.TokenHolder
import com.example.chotuve_android_client.ui.uploadVideo.UploadVideoViewModel
import com.google.firebase.messaging.FirebaseMessagingService
import io.reactivex.disposables.CompositeDisposable

class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAG = "MyFirebaseMS"

    override fun onNewToken(token : String) {
        super.onNewToken(token)
        Log.d(TAG, "This is my new token for ${TAG} ${token}")
        addTokenToUser(token)
    }

    fun addTokenToUser(token : String) {
        val service = AddTokenService()
        service.addTokenToUser(
            TokenHolder.username,
            token,
            CompositeDisposable(),
            {
                if (it != null) {
                    Log.d(TAG, "Successfully assigned token to user ${TokenHolder.username}")
                }
            },
            {
                it.printStackTrace()
                Log.d(TAG,"Error assigning token to user ${TokenHolder.username}")
            }
        )
    }

}