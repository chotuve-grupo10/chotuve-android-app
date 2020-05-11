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

val PICK_IMAGE_CODE = 1000;         // Por qué ?!?
val RESULT_LOAD_VIDEO = 0;

class UploadVideoFragment : Fragment() {

    private lateinit var uploadVideoViewModel: UploadVideoViewModel
    private lateinit var alertDialog: AlertDialog.Builder
    private lateinit var storageReference: StorageReference

    private lateinit var urlUploaded : String
    private lateinit var uriTask : Task<Uri>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        alertDialog = AlertDialog.Builder(this.getContext())
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

            // Init op
            getFileFromGallery(view);
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
                alertDialog.setMessage("I have the file. Uploading it to firebase")
                // alertDialog.show()
                uriTask = uploadToFirebase(selectedVideo)
                // TODO: cambiar el mensaje y mostrar la URL
//                task.onSuccessTask({
//                    val uri = storageReference.downloadUrl
//                    alertDialog.setMessage("File correctly uploaded: " + uri.toString())
//                })
                alertDialog.show()

            } else {
                // handle error
                //alertDialog.setMessage("Obviously something went wrong")
                alertDialog.setMessage("You haven't chose a file. Please try again")
            }
        alertDialog.show()
    }

    fun uploadToFirebase(videoUri : Uri) : Task<Uri> {
        this.getContext()?.let {
            FirebaseApp.initializeApp(it)
            Log.i("firebase", "Firebase App initialized")
        };
        // TODO: definir ésta URL como constante
        val firebaseInstance = FirebaseStorage.getInstance("gs://chotuve-android-app.appspot.com")
        storageReference = firebaseInstance.getReference("upload_test/" + UUID.randomUUID())
        val uploadTask = storageReference.putFile(videoUri)

        // sería bueno apagar el boton ahora
        val taskHandlers = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            storageReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val url = task.result
                val url_string = url!!.toString()
                Log.d("This is Firebase Link ", url_string)
            } else {
                // Handle failures
            }
        }
        // y acá lo prenderíamos de nuevo!
        return taskHandlers;
    }
}
