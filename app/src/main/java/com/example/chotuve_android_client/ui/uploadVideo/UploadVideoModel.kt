package com.example.chotuve_android_client.ui.uploadVideo

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.models.VideoToUpload
import com.example.chotuve_android_client.services.UploadVideoService
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class UploadVideoViewModel : ViewModel() {

    private lateinit var storageReference: StorageReference
    private lateinit var urlUploaded : String
    private lateinit var alertDialog: AlertDialog

    private var _title = MutableLiveData<String>()
    private var _description = MutableLiveData<String>()
    private var _publicOrPrivate = MutableLiveData<Boolean>()

    companion object {
        const val TAG = "UploadVideoViewModel"
    }

    fun getFileFromGallery() = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)

    fun dispatcherToFirebase(
        requestCode : Int,
        resultCode : Int,
        data : Intent?,
        alertDialogBuilder: AlertDialog.Builder) {
        if (requestCode == RESULT_LOAD_VIDEO && resultCode == Activity.RESULT_OK && null != data) {
            val selectedVideo: Uri = data.data!!
            alertDialogBuilder.setMessage("Aguarde un instante. Subiendo el video...")
            uploadToFirebase(selectedVideo, alertDialogBuilder)
        } else {
            alertDialogBuilder.setMessage("Parece que no elegiste ningún archivo.. No importa, podes intentarlo de nuevo")
        }
        alertDialog = alertDialogBuilder.show()
    }

    fun uploadToFirebase(
        videoUri : Uri,
        alertDialogBuilder: AlertDialog.Builder) {

        Log.i("firebase", "Firebase App initialized")
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

                val myCompositeDisposable = CompositeDisposable()
                val uploadVideoService = UploadVideoService()

                // TODO: agregar nombre del archivo, usuario de la sesión y ubicación
                val video: VideoToUpload = VideoToUpload(
                    description = _description.value,
                    isPrivate = _publicOrPrivate.value,
                    title = _title.value,
                    url = urlUploaded,
                    user = "usuario_hardcodeado"
                )

                uploadVideoService.uploadVideo(
                    video,
                    myCompositeDisposable,
                    {
                        Log.d(TAG, "Video was correctly sent to Servers: ${it?.result}")
                        alertDialog.dismiss()
                        alertDialog =
                            alertDialogBuilder.setMessage("Listo! El video fue subido exitosamente!")
                                .show()
                    },
                    {
                        it.printStackTrace()
                        Log.d(
                            TAG,
                            "Error sending video to servers. Attention! This video was however uploaded to Firebase, and its reference got lost in the ether"
                        )
                        alertDialog.dismiss()
                        alertDialog =
                            alertDialogBuilder.setMessage("Ups. Algo salió mal. Porfavor, ¿podrías intentarlo nuevamente?")
                                .show()
                    }
                )
                // acá mismo hay que mandarle la URL al app_server
            } else {
                Log.d(TAG, "Error with Firebase. Apparently the video could not be sent")
                alertDialog.dismiss()
                alertDialog =
                    alertDialogBuilder.setMessage("Ups. Algo salió mal con uno de nuestros proveedores de servicios. Porfavor, ¿podrías intentarlo nuevamente?")
                        .show()

            }
        }
    }

    public fun updateValues(
                title : String?,
                description : String?,
                publicOrPrivateVideo : Boolean
            ) {
        _title.value = title
        _description.value = description
        _publicOrPrivate.value = publicOrPrivateVideo

        Log.d(TAG, "El título será ${title}")
        Log.d(TAG, "La descripción será ${description}")
        Log.d(TAG, "El video es privado ${publicOrPrivateVideo}")
    }

}