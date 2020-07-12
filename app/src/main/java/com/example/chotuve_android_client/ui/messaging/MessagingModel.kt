package com.example.chotuve_android_client.ui.messaging

import android.os.Build
import android.text.Editable
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.data.MessagesRepository
import com.example.chotuve_android_client.models.Message
import com.example.chotuve_android_client.services.GetServerTimeService
import com.example.chotuve_android_client.tools.TokenHolder
import io.reactivex.disposables.CompositeDisposable
import org.apache.commons.net.ntp.NTPUDPClient
import org.apache.commons.net.ntp.TimeInfo
import java.net.InetAddress
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.thread


class MessagingModel {

//    companion object TimeLookup {
//        const val TAG = "ObjTimeLookup"
//        const val TIME_SERVER = "time-a.nist.gov"
//
//        @Throws(Exception::class)
//        @JvmStatic
//        fun main() {
//            val timeClient = NTPUDPClient()
//            val inetAddress: InetAddress = InetAddress.getByName(TIME_SERVER)
//            val timeInfo: TimeInfo = timeClient.getTime(inetAddress)
//            val returnTime: Long = timeInfo.getReturnTime()
//            val time = Date(returnTime)
//            Log.d(TAG, "Time from $TIME_SERVER: $time")
//        }
//    }

    val TAG = "MessagingModel"
    val messagesRepository = MessagesRepository()

    var _messages = MutableLiveData<List<Message>>()
    val messages : LiveData<List<Message>>
        get() = _messages

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendNewMessage(editText : Editable, userIdReceivingMessage : String) {
        Log.d(TAG, "Nuevo mensaje es $editText de ${TokenHolder.username} y es para $userIdReceivingMessage")

        val currentDateTime = getCurrentDateTime()

        val message = hashMapOf(
            "fromId" to TokenHolder.username,
            "toId" to userIdReceivingMessage,
            "text" to editText.toString(),
            "timestamp" to currentDateTime.toString()
        )

        messagesRepository.sendMessage(message)
    }

    fun getMessages(userIdReceivingMessage : String) {

        messagesRepository.getMessagesFromFirestore(userIdReceivingMessage, _messages)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime() : String? {

        var currentDateTime : String? = null

        val getServerTimeService = GetServerTimeService()
        getServerTimeService.getServerTime(
            CompositeDisposable(),
            {
                currentDateTime = it!!.timestamp
            }, {
                it.printStackTrace()
                Log.d(TAG, "Error getting time from AppServer: ${it.localizedMessage}")
                currentDateTime = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                    .withZone(ZoneId.of("AGT"))
                    .format(Instant.now())
            }
        )
        Log.d(TAG, "CurrentDateTime es ${currentDateTime}")
        return currentDateTime
    }

}


