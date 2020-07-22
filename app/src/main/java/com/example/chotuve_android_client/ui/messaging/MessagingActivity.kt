package com.example.chotuve_android_client.ui.messaging

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.tools.TokenHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_messaging.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

const val BLANK_STRING = ""

class MessagingActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        val messagingModel = MessagingModel()

        val userIdReceivingMessage : String? = intent.getStringExtra("user_to_message_id")
        val userFullNameReceivingMessage : String? = intent.getStringExtra("user_to_message_full_name")

        setMessagingActivityTitle(userFullNameReceivingMessage.toString())

        messagingModel.getMessages(userIdReceivingMessage.toString())
        messagingModel.messages.observe(this, Observer {
            val adapter = GroupAdapter<GroupieViewHolder>()
            for (message in it) {
                if (message.fromId == TokenHolder.username) {
                    adapter.add(ChatFromItem(message.text))
                }
                else {
                    adapter.add(ChatToItem(message.text))
                }
            }
            recyclerView2.also {
                it.layoutManager = LinearLayoutManager(this.applicationContext)
                it.adapter = adapter
                it.getAdapter()?.getItemCount()?.let { it1 -> it.smoothScrollToPosition(it1) };

            }
        })

        val sendButton = this.findViewById<Button>(R.id.send_button)
        val messageText = this.findViewById<EditText>(R.id.new_message)
        sendButton.setOnClickListener() {
            if (messageText.text.toString() != BLANK_STRING) {
                sendMessage(messagingModel, messageText.text, userIdReceivingMessage!!)
                messageText.text.clear()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(messagingModel: MessagingModel, editText: Editable, userIdReceivingMessage: String) {
        messagingModel.sendNewMessage(editText, userIdReceivingMessage!!)
        recyclerView2.getAdapter()?.getItemCount()?.let { it1 ->
            recyclerView2.smoothScrollToPosition(
                it1
            )
        };
    }

    fun setMessagingActivityTitle(userName : String) {
        supportActionBar?.title = userName
    }

}

class ChatFromItem(val text: String): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_from_row.text = text
    }
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text
    }
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}
