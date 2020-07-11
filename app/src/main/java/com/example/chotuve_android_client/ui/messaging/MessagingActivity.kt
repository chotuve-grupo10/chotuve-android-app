package com.example.chotuve_android_client.ui.messaging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.chotuve_android_client.R

class MessagingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        var messagingModel : MessagingModel = MessagingModel()

        val sendButton = this.findViewById<Button>(R.id.send_button)
        val messageText = this.findViewById<EditText>(R.id.new_message)
        sendButton.setOnClickListener() {
            messagingModel.sendNewMessage(messageText.text)
            messageText.text.clear()
        }
    }

}