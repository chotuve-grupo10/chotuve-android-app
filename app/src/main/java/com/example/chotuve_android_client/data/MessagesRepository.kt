package com.example.chotuve_android_client.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.models.Message
import com.example.chotuve_android_client.tools.TokenHolder
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MessagesRepository {

    val db = FirebaseFirestore.getInstance()
    val TAG = "MessagesRepository"

    fun sendMessage(message : HashMap<String, String>) {
        db.collection("messages")
            .add(message)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Message added to Firestore with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document to Firestore Instance!", e)
            }

    }

    fun getMessagesFromFirestore(userIdReceivingMessage : String, _messages: MutableLiveData<List<Message>>) {
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
                        Log.d(TAG, "Message: ${doc["text"]}")
                        this_messages.add(
                            Message(
                                doc["fromId"].toString(),
                                doc["toId"].toString(),
                                doc["text"].toString(),
                                doc["timestamp"].toString()
                            )
                        )
                    }
                    else if ( (doc["fromId"] == userIdReceivingMessage)
                        && (doc["toId"] == TokenHolder.username) ) {
                        Log.d(TAG, "Message: ${doc["text"]}")
                        this_messages.add(
                            Message(
                                doc["fromId"].toString(),
                                doc["toId"].toString(),
                                doc["text"].toString(),
                                doc["timestamp"].toString()
                            )
                        )
                    }
                }
                Log.d(TAG, "Tengo ${this_messages.size} mensajes para displayear")
                _messages.value = this_messages
            }
    }

}