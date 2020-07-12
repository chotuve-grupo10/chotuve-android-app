package com.example.chotuve_android_client.ui.messaging

import android.os.Build
import android.text.Editable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.constraintlayout.solver.widgets.ResolutionNode.REMOVED
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.models.Message
import com.example.chotuve_android_client.tools.TokenHolder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.sql.Timestamp
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class MessagingModel {

    val TAG = "MessagingModel"
    val db = FirebaseFirestore.getInstance()

    var _messages = MutableLiveData<List<Message>>()
    val messages : LiveData<List<Message>>
        get() = _messages

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendNewMessage(editText : Editable, userIdReceivingMessage : String) {
        Log.d(TAG, "Nuevo mensaje es $editText de ${TokenHolder.username} y es para $userIdReceivingMessage")

//        val dateTimeFormatter = DateTimeFormatter
//            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")
//            .withZone(ZoneId.of("AGT"))
//
////        val timestamp = dateTimeFormatter.format(Instant.now())
//        val timestamp = Date().now()

        val argZone = ZoneId.of("AGT")
        val currentDateTime = ZonedDateTime.now(argZone)

        val message = hashMapOf(
            "fromId" to TokenHolder.username,
            "toId" to userIdReceivingMessage,
            "text" to editText.toString(),
            "timestamp" to currentDateTime
        )

        db.collection("messages")
            .add(message)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Message added to Firestore with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document to Firestore Instance!", e)
            }
    }

    fun getMessagesFromFirestore(userIdReceivingMessage : String) {

        db.collection("messages")
//            .whereEqualTo("fromId", TokenHolder.username)
//            .whereEqualTo("toId", userIdReceivingMessage)
            .orderBy("timestamp")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }
                val this_messages = ArrayList<Message>()
                for (doc in value!!) {
                    if ( (doc["fromId"] == TokenHolder.username)
                        && (doc["toId"] == userIdReceivingMessage) ) {
                            this_messages.add(Message(
                                doc["fromId"].toString(),
                                doc["toId"].toString(),
                                doc["text"].toString(),
                                doc["timestamp"].toString()
                            ))
                    }
                    if ( (doc["fromId"] == userIdReceivingMessage)
                        && (doc["toId"] == TokenHolder.username) ) {
                            this_messages.add(Message(
                                doc["fromId"].toString(),
                                doc["toId"].toString(),
                                doc["text"].toString(),
                                doc["timestamp"].toString()
                            ))
                    }
                }
                Log.d(TAG, "Tengo ${this_messages.size} mensajes para displayear")
                _messages.value = this_messages
            }

    }

}