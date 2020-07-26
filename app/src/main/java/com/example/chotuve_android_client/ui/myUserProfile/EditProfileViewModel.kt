package com.example.chotuve_android_client.ui.myUserProfile

import android.app.Activity
import android.content.Intent
import android.media.session.MediaSession
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.models.ListedUser
import com.example.chotuve_android_client.services.UserProfileService
import com.example.chotuve_android_client.tools.FirebaseStorageHandler
import com.example.chotuve_android_client.tools.TokenHolder
import com.example.chotuve_android_client.ui.recovery.RecoveryModel
import com.example.chotuve_android_client.ui.uploadVideo.RESULT_LOAD_VIDEO
import com.example.chotuve_android_client.ui.uploadVideo.UploadVideoViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class EditProfileViewModel {
    // TODO: env var these
    val IMAGE_LOCATION = "profile_pictures/"
    val APPSPOT_URL = "gs://chotuve-android-app.appspot.com"
    val TAG = "EditProfileViewModel"

    private lateinit var storageReference: StorageReference
    private lateinit var uuidFileName: String
    private lateinit var alertDialog: android.app.AlertDialog

    private val firebaseStorageHandler = FirebaseStorageHandler()

    companion object {
        const val STATUS_INIT = 0
        const val STATUS_CHANGED = 1
        const val RESULT_LOAD_IMAGE = 1
    }

    private val _text = MutableLiveData<String>().apply {
        // TODO: nice to have aca el fullName, o algo más amigable que el mail
        value =
            "Tus datos personales, ${TokenHolder.username}. Desde acá podés editar tu información personal"
    }
    val text: LiveData<String>
        get() = _text

    val _status = MutableLiveData<Int>().apply {
        this.value = STATUS_INIT
    }
    val status: LiveData<Int>
        get() = _status


    private val _listed_user = MutableLiveData<ListedUser>().apply {
        this.value = ListedUser(TokenHolder.username, "Nombre", "Número de teléfono", "")
    }
    val listed_user: LiveData<ListedUser>
        get() = _listed_user


    fun getProfileInformation() {
        val userProfileService = UserProfileService()
        userProfileService.getUserDataInformation(
            TokenHolder.appServerToken,
            TokenHolder.username,
            CompositeDisposable(),
            {
                Log.d(TAG, "User information received properly ${it!!.fullName}, ${it.phoneNumber}")
                _listed_user.value = it
            },
            {
                it.printStackTrace()
                Log.e(
                    TAG,
                    "No se pudo recibir correctamente la información de usuario: " + it.message
                )
            }
        )
    }

    fun sendNewProfileInformation(fullName: String, phoneNumber: String, ad: AlertDialog.Builder) {
//        if (changesHaveBeenMade(fullName, phoneNumber)) {
        Log.d(TAG, "New fullname ${fullName}, new phoneNumber ${phoneNumber}")
        UserProfileService()
            .putNewUserDataInformation(
                TokenHolder.appServerToken,
                TokenHolder.username,
                ListedUser(
                    _listed_user.value!!.email,
                    fullName,
                    phoneNumber,
                    _listed_user.value!!.profilePicture
                ),
                CompositeDisposable(),
                {
                    if (it != null) {
                        _status.value = STATUS_CHANGED
                    }
                },
                {
                    it.printStackTrace()
                    Log.e(
                        TAG,
                        "No se pudo modificar correctamente la información de usuario: " + it.message
                    )
                    ad.setMessage("Ocurrió un error")
                    ad.show()
                }
            )
    }

    fun getFileFromGallery() =
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

    fun dispatcherToFirebase(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        alertDialogBuilder: android.app.AlertDialog.Builder
    ) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            val selectedImage: Uri = data.data!!
            alertDialogBuilder.setMessage("Aguarde un instante. Subiendo la imagen...")
            uploadImageToFirebase(selectedImage, alertDialogBuilder)
        } else {
            alertDialogBuilder.setMessage("Parece que no elegiste ningún archivo.. No importa, podes intentarlo de nuevo")
        }
        alertDialog = alertDialogBuilder.show()
    }

    fun uploadImageToFirebase(selectedImage: Uri, ad: android.app.AlertDialog.Builder) {
        val storageReference: StorageReference

        Log.d(TAG, "Firebase App initialized")
        val firebaseInstance = FirebaseStorage.getInstance(APPSPOT_URL)

        val uuidFileName = UUID.randomUUID().toString()
        storageReference = firebaseInstance.getReference(IMAGE_LOCATION + uuidFileName)
        val uploadTask = storageReference.putFile(selectedImage)

        uploadTask.continueWithTask { task ->
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
                val _urlUploaded = url!!.toString()
                Log.d(TAG, "This is Firebase Link for Profile Picture ${_urlUploaded}")
                _listed_user.value = ListedUser(
                    _listed_user.value!!.email,
                    _listed_user.value!!.fullName,
                    _listed_user.value!!.phoneNumber,
                    _urlUploaded)
                alertDialog.dismiss()
                alertDialog =
                    ad.setMessage("Listo! Subimos la imagen correctamente. No te olvides de apretar el botón rojo para aceptar tus cambios!")
                        .show()
            } else {
                Log.d(TAG,"Error with Firebase. Apparently the video could not be sent")
                alertDialog.dismiss()
                alertDialog =
                    ad.setMessage("Ups. Algo salió mal con uno de nuestros proveedores de servicios. Porfavor, ¿podrías intentarlo nuevamente?")
                        .show()
            }
        }
    }

}