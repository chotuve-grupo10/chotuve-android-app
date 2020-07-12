package com.example.chotuve_android_client.ui.messaging

import android.os.Build
import android.text.Editable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.data.MessagesRepository
import com.example.chotuve_android_client.models.Message
import com.example.chotuve_android_client.tools.TokenHolder
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


class MessagingModel {

    val TAG = "MessagingModel"
    val messagesRepository = MessagesRepository()

    var _messages = MutableLiveData<List<Message>>()
    val messages : LiveData<List<Message>>
        get() = _messages

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendNewMessage(editText : Editable, userIdReceivingMessage : String) {
        Log.d(TAG, "Nuevo mensaje es $editText de ${TokenHolder.username} y es para $userIdReceivingMessage")

        val currentDateTime = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneId.of("AGT"))
            .format(Instant.now())

        val message = hashMapOf(
            "fromId" to TokenHolder.username,
            "toId" to userIdReceivingMessage,
            "text" to editText.toString(),
            "timestamp" to currentDateTime
        )

        messagesRepository.sendMessage(message)
    }

    fun getMessages(userIdReceivingMessage : String) {

        messagesRepository.getMessagesFromFirestore(userIdReceivingMessage, _messages)
    }

}