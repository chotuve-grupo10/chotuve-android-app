package com.example.chotuve_android_client.ui.myUserProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.chotuve_android_client.R

class EditProfileActivity : AppCompatActivity() {

    private val editProfileViewModel = EditProfileViewModel()
    val TAG = "EditProfileAct"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        editProfileViewModel.getProfileInformation()

        val textView: TextView = findViewById(R.id.textView)
        editProfileViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val phoneNumber = findViewById<EditText>(R.id.editable_phone_number)
        val fullName = findViewById<EditText>(R.id.editable_full_name)
        editProfileViewModel.listed_user.observe(this, Observer {
            phoneNumber.setText( it.phoneNumber.toString() )
            fullName.setText( it.fullName.toString() )
        })

        val sendChanges = findViewById<Button>(R.id.button_send_profile_editions)
        sendChanges.setOnClickListener {
            Log.d(TAG, "About to make some changes")
            editProfileViewModel.sendNewProfileInformation(
                fullName.text.toString(),
                phoneNumber.text.toString(),
                AlertDialog.Builder(this)
            )
        }

    }

}