package com.example.chotuve_android_client.ui.myUserProfile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.models.ListedUser
import com.example.chotuve_android_client.services.UserProfileService
import com.example.chotuve_android_client.tools.TokenHolder
import io.reactivex.disposables.CompositeDisposable

class EditProfileViewModel {

    val TAG = "EditProfileViewModel"
    private val _text = MutableLiveData<String>().apply {
        // TODO: nice to have aca el fullName, o algo más amigable que el mail
        value = "Tus datos personales, ${TokenHolder.username}. Desde acá podés editar tu información personal"
    }
    val text: LiveData<String>
        get() = _text

    private val _listed_user = MutableLiveData<ListedUser>().apply {
        this.value = ListedUser(TokenHolder.username, "Nombre", "Número de teléfono", "")
    }
    val listed_user : LiveData<ListedUser>
        get() = _listed_user

//    private val _new_phone_number = MutableLiveData<String>().apply { this.value = listed_user.value!!.phoneNumber.toString() }
//    val new_phone_number : LiveData<String>
//        get() = _new_phone_number
//
//    private val _new_full_name = MutableLiveData<String>().apply { this.value = listed_user.value!!.fullName.toString() }
//    val new_full_name : LiveData<String>
//        get() = _new_full_name

    fun getProfileInformation() {
        val userProfileService = UserProfileService()
        userProfileService.getUserDataInformation(
            CompositeDisposable(),
            {
                Log.d(TAG, "User information received properly ${it!!.fullName}, ${it.phoneNumber}")
                _listed_user.value = it
//                _new_phone_number.value = it.phoneNumber
//                _new_full_name.value = it.fullName
            },
            {
                it.printStackTrace()
                Log.e(TAG, "No se pudo recibir correctamente la información de usuario: " + it.message)
            }
        )
    }

    fun sendNewProfileInformation(fullName : String, phoneNumber : String) {
        if( (fullName != _listed_user.value!!.fullName) || (phoneNumber != _listed_user.value!!.phoneNumber) ) {
//            val listedUser = ListedUser(TokenHolder.username, fullName, phoneNumber, _listed_user.value!!.profilePicture.toString())
            Log.d(TAG, "New fullname ${fullName}, new phoneNumber ${phoneNumber}")
        }

    }

}