package com.example.chotuve_android_client.ui.uploadVideo

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.R
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.StorageReference

val RESULT_LOAD_VIDEO = 0;
val APPSPOT_URL = "gs://chotuve-android-app.appspot.com";

class UploadVideoFragment : Fragment() {

    private lateinit var uploadVideoViewModel: UploadVideoViewModel
    private lateinit var alertDialogBuilder: AlertDialog.Builder

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        alertDialogBuilder = AlertDialog.Builder(this.getContext())
        uploadVideoViewModel =
                ViewModelProviders.of(this).get(UploadVideoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_upload, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FireBase button
        view.findViewById<Button>(R.id.firebase_button).setOnClickListener {
//            val homeTextView = view.findViewById<TextView>(R.id.text_upload_video)
//            homeTextView.text = "Getting Firebase storage instance"
            // La posta es que acá se debería llamar a una Clase que se encargue de todo esto.
            this.context?.let { it1 -> FirebaseApp.initializeApp(it1) }
            val editTitleView = view.findViewById<EditText>(R.id.editTextTitle)
            val editDescriptionView = view.findViewById<EditText>(R.id.editTextDescription)
            val publicOrPrivateVideo = view.findViewById<CheckBox>(R.id.checkBoxPrivate)
            uploadVideoViewModel.updateValues(
                editTitleView.text.toString(),
                editDescriptionView.text.toString(),
                publicOrPrivateVideo.isChecked()
            )
            getFileFromGallery();
        }
    }

    fun getFileFromGallery() {
        startActivityForResult(
            uploadVideoViewModel.getFileFromGallery(),
            RESULT_LOAD_VIDEO
        )
    }

    // For the ActivityResult:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            uploadVideoViewModel.dispatcherToFirebase(requestCode, resultCode, data, alertDialogBuilder)
    }
}
