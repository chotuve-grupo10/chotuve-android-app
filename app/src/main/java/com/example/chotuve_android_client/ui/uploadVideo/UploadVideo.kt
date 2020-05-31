package com.example.chotuve_android_client.ui.uploadVideo

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.MainActivity
import com.example.chotuve_android_client.R
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext

val RESULT_LOAD_VIDEO = 0;
val APPSPOT_URL = "gs://chotuve-android-app.appspot.com";

class UploadVideoFragment : Fragment() {

    private lateinit var uploadVideoViewModel: UploadVideoViewModel
    private lateinit var alertDialogBuilder: AlertDialog.Builder
    private lateinit var alertDialog: AlertDialog
    private lateinit var storageReference: StorageReference
    private lateinit var urlUploaded : String
    // private lateinit var uriTask : Task<Uri>

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
            val homeTextView = view.findViewById<TextView>(R.id.text_upload_video)
            homeTextView.text = "Getting Firebase storage instance"

            // La posta es que acá se debería llamar a una Clase que se encargue de todo esto.
            getFileFromGallery(view);
            // Ahora, teniendo el URL hay que mandárselo al AppServer
        }
    }

    fun getFileFromGallery(view: View) {
        startActivityForResult(
            Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI),
            RESULT_LOAD_VIDEO);
    }

    // For the ActivityResult:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == RESULT_LOAD_VIDEO && resultCode == RESULT_OK && null != data) {
                val selectedVideo: Uri = data.data!!
                alertDialogBuilder.setMessage("Please wait. Uploading video...")
                uploadToFirebase(selectedVideo)
            } else {
                alertDialogBuilder.setMessage("You haven't chose a file. Please try again")
                // alertDialog = alertDialogBuilder.show()
            }
        alertDialog = alertDialogBuilder.show()

    }

    fun uploadToFirebase(videoUri : Uri)  { // : Task<Uri>
        this.getContext()?.let {
            FirebaseApp.initializeApp(it)
            Log.i("firebase", "Firebase App initialized")
        };
        val firebaseInstance = FirebaseStorage.getInstance(APPSPOT_URL)
        // TODO: cuando terminemos la lógica definir el path de videos
        storageReference = firebaseInstance.getReference("upload_test/" + UUID.randomUUID())
        val uploadTask = storageReference.putFile(videoUri)

        val taskHandlers = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // alertDialog.cancel()
                val url = task.result
                urlUploaded = url!!.toString()
                Log.d("This is Firebase Link ", urlUploaded)
                alertDialog.dismiss()
                alertDialog = alertDialogBuilder.setMessage("Finished! The video was uploaded successfully").show()
                // acá mismo hay que mandarle la URL al app_server
            } else {
                alertDialog.dismiss()
                alertDialog = alertDialogBuilder.setMessage("Finished! The video was uploaded successfully").show()
            }
        }
    }
}
