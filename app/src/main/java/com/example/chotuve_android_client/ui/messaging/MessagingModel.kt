package com.example.chotuve_android_client.ui.messaging

import android.os.Build
import android.text.Editable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bolts.Task.delay
import com.example.chotuve_android_client.data.MessagesRepository
import com.example.chotuve_android_client.models.Message
import com.example.chotuve_android_client.services.GetServerTimeService
import com.example.chotuve_android_client.tools.TokenHolder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class MessagingModel : ViewModel() {

    val TAG = "MessagingModel"
    val messagesRepository = MessagesRepository()

    var _messages = MutableLiveData<List<Message>>()
    val messages : LiveData<List<Message>>
        get() = _messages

    var _currentDateTime : String? = null

    fun getMessages(userIdReceivingMessage : String) {
        messagesRepository.getMessagesFromFirestore(userIdReceivingMessage, _messages)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendNewMessage(editText : Editable, userIdReceivingMessage : String) {

        val currentDateTime = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneId.of("AGT"))
            .format(Instant.now())
        Log.d(TAG, "CurrentDateTime del mensaje ${currentDateTime}")

        val message = hashMapOf(
            "fromId" to TokenHolder.username,
            "toId" to userIdReceivingMessage,
            "text" to editText.toString(),
            "timestamp" to currentDateTime.toString()
        )
        messagesRepository.sendMessage(message)
    }

/*
// Acá dejo los intentos de usar la hora del server todos ellos fallidos tan lleno de sueños rotos //
    @RequiresApi(Build.VERSION_CODES.O)
//    suspend fun sendNewMessage(editText : Editable, userIdReceivingMessage : String)  {
    suspend fun getServerTime() : String? {
        var currentDateTime : String? = null
        val getServerTimeService = GetServerTimeService()
        getServerTimeService.getServerTime(
            CompositeDisposable(),
            {
                if (it != null) {
                    currentDateTime = it.timestamp
                    Log.d(TAG, "CurrentDateTime del server es ${it.timestamp}")
                }
            }, {
                it.printStackTrace()
                Log.d(TAG, "Error getting time from AppServer: ${it.localizedMessage}")
                currentDateTime = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                    .withZone(ZoneId.of("AGT"))
                    .format(Instant.now())
                Log.d(TAG, "CurrentDateTime del mensaje ${currentDateTime}")
            }
        )
        Log.d(TAG, "CurrentDateTime del mensaje ${currentDateTime}")
        return currentDateTime
    }

    @RequiresApi(Build.VERSION_CODES.O)
//    fun sendMessageToRepository(editText : Editable, userIdReceivingMessage : String, currentDateTime : String?) {
    fun sendNewMessage(editText : Editable, userIdReceivingMessage : String) =
    runBlocking {
        Log.d(TAG, "Nuevo mensaje es $editText de ${TokenHolder.username} y es para $userIdReceivingMessage")
        launch(Dispatchers.IO) {
            val currentDateTime : String? = async { getServerTime() }.await()
            val message = hashMapOf(
                "fromId" to TokenHolder.username,
                "toId" to userIdReceivingMessage,
                "text" to editText.toString(),
                "timestamp" to currentDateTime.toString()
            )
            messagesRepository.sendMessage(message)
        }
    }
*/
}


