package com.example.chotuve_android_client.ui.myUserProfile

import android.media.session.MediaSession
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.models.ListedUser
import com.example.chotuve_android_client.services.UserProfileService
import com.example.chotuve_android_client.tools.TokenHolder
import com.example.chotuve_android_client.ui.recovery.RecoveryModel
import io.reactivex.disposables.CompositeDisposable

class EditProfileViewModel {

    companion object {
        const val STATUS_INIT = 0
        const val STATUS_CHANGED = 1
    }

        val TAG = "EditProfileViewModel"
    private val _text = MutableLiveData<String>().apply {
        // TODO: nice to have aca el fullName, o algo más amigable que el mail
        value = "Tus datos personales, ${TokenHolder.username}. Desde acá podés editar tu información personal"
    }
    val text: LiveData<String>
        get() = _text

    val _status = MutableLiveData<Int>().apply {
        this.value = STATUS_INIT
    }
    val status : LiveData<Int>
        get() = _status


    private val _listed_user = MutableLiveData<ListedUser>().apply {
        this.value = ListedUser(TokenHolder.username, "Nombre", "Número de teléfono", "")
    }
    val listed_user : LiveData<ListedUser>
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
                Log.e(TAG, "No se pudo recibir correctamente la información de usuario: " + it.message)
            }
        )
    }

    fun sendNewProfileInformation(fullName : String, phoneNumber : String, ad : AlertDialog.Builder) {
        if( changesHaveBeenMade(fullName, phoneNumber) ) {
            Log.d(TAG, "New fullname ${fullName}, new phoneNumber ${phoneNumber}")
            UserProfileService()
                .putNewUserDataInformation(
                    TokenHolder.appServerToken,
                    TokenHolder.username,
                    ListedUser(_listed_user.value!!.email, fullName, phoneNumber, _listed_user.value!!.profilePicture),
                    CompositeDisposable(),
                    {
                        if (it != null) {
                            _status.value = STATUS_CHANGED
                        }
                    },
                    {
                        it.printStackTrace()
                        Log.e(TAG, "No se pudo modificar correctamente la información de usuario: " + it.message)
                        ad.setMessage("Ocurrió un error")
                        ad.show()
                    }
                )
        } else {
            ad.setMessage("No hay cambios que guardar!")
            ad.show()
        }
    }

    fun changesHaveBeenMade(fullName : String, phoneNumber : String) : Boolean {
        if( (fullName != _listed_user.value!!.fullName) || (phoneNumber != _listed_user.value!!.phoneNumber) ) {
            return true
        } else {
            return false
        }
    }

}