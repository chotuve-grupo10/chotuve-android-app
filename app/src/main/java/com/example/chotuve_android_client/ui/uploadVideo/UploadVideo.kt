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
import com.example.chotuve_android_client.R
import com.google.firebase.FirebaseApp
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

val PICK_IMAGE_CODE = 1000;         // Por qué ?!?

class UploadVideoFragment : Fragment() {

    private lateinit var uploadVideoViewModel: UploadVideoViewModel
    private lateinit var alertDialog: AlertDialog.Builder
    private lateinit var storageReference: StorageReference

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        alertDialog = AlertDialog.Builder(this.getContext())
        uploadVideoViewModel =
                ViewModelProviders.of(this).get(UploadVideoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_upload, container, false)

//        val textView: TextView = root.findViewById(R.id.text_upload_video)
//        uploadVideoViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FireBase button
        view.findViewById<Button>(R.id.firebase_button).setOnClickListener {
            val homeTextView = view.findViewById<TextView>(R.id.text_home)
            homeTextView.text = "Getting Firebase storage instance"

            // Init op
            getFileFromGallery(view);
        }
    }

    fun getFileFromGallery(view: View) {
        val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        val RESULT_LOAD_IMAGE = 0;
        startActivityForResult(i, 0);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val RESULT_LOAD_IMAGE = 0;
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                val selectedImage: Uri = data.data!!
                alertDialog.setMessage("Apparently, I have the file!")
                uploadToFirebase(selectedImage)
            } else {
                // handle error
                alertDialog.setMessage("Obviously something went wrong")
            }
        alertDialog.show()
    }

    fun uploadToFirebase(videoUri : Uri) {
        this.getContext()?.let {
            FirebaseApp.initializeApp(it)
            Log.i("firebase", "Firebase App initialized")
        };
        val firebaseInstance = FirebaseStorage.getInstance("gs://chotuve-android-app.appspot.com")
        storageReference = firebaseInstance.getReference("upload_test/" + UUID.randomUUID())
        val uploadTask = storageReference.putFile(videoUri)
    }

}
