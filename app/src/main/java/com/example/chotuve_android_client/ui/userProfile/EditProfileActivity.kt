package com.example.chotuve_android_client.ui.userProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.chotuve_android_client.R
import com.squareup.picasso.Picasso

class EditProfileActivity : AppCompatActivity() {

    private lateinit var alertDialogBuilder: android.app.AlertDialog.Builder
    private val editProfileViewModel =
        EditProfileViewModel()
    private val TAG = "EditProfileAct"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        // TODO: puede que tenga que inicializar FirebaseApp
        alertDialogBuilder = android.app.AlertDialog.Builder(this)

        editProfileViewModel.getProfileInformation()

        val textView: TextView = findViewById(R.id.textView)
        editProfileViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val phoneNumber = findViewById<EditText>(R.id.editable_phone_number)
        val fullName = findViewById<EditText>(R.id.editable_full_name)
        val profilePicture = findViewById<ImageView>(R.id.profile_picture)
        editProfileViewModel.listed_user.observe(this, Observer {
            phoneNumber.setText(it.phoneNumber.toString())
            fullName.setText(it.fullName.toString())
            if (it.profilePicture != "") {
                Picasso
                    .get()
                    .load(it.profilePicture)
                    .into(profilePicture)
            }
        })

        val uploadProfilePicture = findViewById<Button>(R.id.button_upload_picture)
        uploadProfilePicture.setOnClickListener {
            getProfilePictureFromGallery()
        }

        val sendChanges = findViewById<Button>(R.id.button_send_profile_editions)
        sendChanges.setOnClickListener {
            Log.d(TAG, "About to make some changes")
            editProfileViewModel.sendNewProfileInformation(
                fullName.text.toString(),
                phoneNumber.text.toString(),
                AlertDialog.Builder(this)
            )
        }

        editProfileViewModel.status.observe(this, Observer {
            if (it == EditProfileViewModel.STATUS_CHANGED) {
                finish()
            }
        })


    }

    fun getProfilePictureFromGallery() {
        startActivityForResult(
            editProfileViewModel.getFileFromGallery(),
            EditProfileViewModel.RESULT_LOAD_IMAGE
        )
    }

    // For the ActivityResult:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editProfileViewModel.dispatcherToFirebase(requestCode, resultCode, data, alertDialogBuilder)
    }
}
