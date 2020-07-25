package com.example.chotuve_android_client.ui.recovery

import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.R

class RecoveryModel {
    val TAG = "RecoveryModel"
    val _firstText = MutableLiveData<String>().apply {
        this.value = "Quiero que me envíen un mail para recibir un token de recuperación de contraseña"
    }
    val firstText : LiveData<String>
        get() = _firstText

    fun sendTokenForPasswordRecovery(email : String, ad : AlertDialog.Builder) {
        if (emailIsValid(email)) {
            Log.d(TAG, "Sending token for $email")
//            TokenForPasswordRecoveryService
//                .
        }
        else {
            ad.setMessage("El email ingresado es inválido")
            ad.show()
        }
    }

    fun emailIsValid(_email : String) : Boolean {
        if(_email.length == 0) {
            return false
        } else if (! _email.contains("@", ignoreCase = true)) {
            return false
        } else if (! _email.contains(".", ignoreCase = true)) {
            return false
        }
        return true
    }

}