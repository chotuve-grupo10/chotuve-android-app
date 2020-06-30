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
//        val throwableAsJson = error_throwable.response().errorBody()?.toString()
        val throwableAsString = error_throwable.response().errorBody()!!.charStream().readText()
        Log.d(TAG, "Error body es ${throwableAsString}")
        try {
            message = JSONObject(throwableAsString)[key].toString()
        } catch (e: Exception) {
            message = "An unknown error occured. Could not reach server message"
        }
    }
}