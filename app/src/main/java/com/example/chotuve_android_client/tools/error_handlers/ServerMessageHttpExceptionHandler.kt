package com.example.chotuve_android_client.tools.error_handlers

import android.util.Log
import org.json.JSONObject
import retrofit2.HttpException

class ServerMessageHttpExceptionHandler(
    val error_throwable : HttpException,
    val key : String
) {
    var message = "An error occured"

    init {
        val TAG = "ServerMessageError"
        val throwableAsJson = error_throwable.response().errorBody()?.string()
        Log.d(TAG, "Error body es ${throwableAsJson}")
        message = JSONObject(throwableAsJson)[key].toString()
    }
}