package com.example.chotuve_android_client.ui.messaging

import android.text.Editable
import android.util.Log

class MessagingModel {

    val TAG = "MessagingModel"

    fun sendNewMessage(editText : Editable) {
        Log.d(TAG, "Nuevo mensaje es ${editText.toString()}")
    }
    
}