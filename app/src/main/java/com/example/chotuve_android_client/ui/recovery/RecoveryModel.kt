package com.example.chotuve_android_client.ui.recovery

import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.services.PasswordRecoveryService
import com.example.chotuve_android_client.ui.register.RegisterResult
import com.example.chotuve_android_client.ui.register.RegisterViewModel
import io.reactivex.disposables.CompositeDisposable

class RecoveryModel {
    companion object{
        const val STATUS_INIT = 0
        const val STATUS_TOKEN_SENT = 1
        const val STATUS_FINISHED = 2
        const val ERROR_404 = "HTTP 404 NOT FOUND"
    }

    val TAG = "RecoveryModel"

    val _firstText = MutableLiveData<String>().apply {
        this.value = "Quiero que me envíen un mail para recibir un token de recuperación de contraseña"
    }
    val firstText : LiveData<String>
        get() = _firstText

    val _status = MutableLiveData<Int>().apply {
        this.value = STATUS_INIT
    }
    val status : LiveData<Int>
        get() = _status

    val _email = MutableLiveData<String>().apply { this.value = "" }
    val email : LiveData<String>
        get() = _email

    val passwordRecoveryService = PasswordRecoveryService()

    fun sendTokenForPasswordRecovery(email : String, ad : AlertDialog.Builder) {
        if (emailIsValid(email)) {
            Log.d(TAG, "Sending token for $email")
            ad.setMessage("Procesando tu solicitud...")
            val res = ad.show()
            passwordRecoveryService
                .sendTokenForPasswordRecovery(
                    email,
                    CompositeDisposable(),
                    {
                        res.dismiss()
                        _firstText.value = "Bien. Vas a recibir un mail con tu token para recuperación de contraseña. Ingresalo acá abajo y setea tu nueva contraseña"
                        _status.value = STATUS_TOKEN_SENT
                        _email.value = email
                    },
                    {
                        it.printStackTrace()
                        Log.e(TAG, "El mail no se pudo enviar correctamente: " + it.message)
                        res.dismiss()
                        if (it.message == ERROR_404) {
                            ad.setMessage("El email ingresado es inválido")
                            ad.show()
                        } else {
                            ad.setMessage("Ocurrió un error inesperado")
                            ad.show()
                        }
                    }
                )
        }
        else {
            ad.setMessage("El email ingresado es inválido")
            ad.show()
        }
    }

    fun sendNewPassword(token : String, newPassword : String, ad : AlertDialog.Builder) {
        if( (passwordIsValid(newPassword)) && (email.value != "") ) {
            Log.d(TAG, "Setting new password...")
            ad.setMessage("Procesando tu solicitud...")
            val res = ad.show()
            passwordRecoveryService
                .newPassword(
                    email.value.toString(),
                    newPassword,
                    token,
                    CompositeDisposable(),
                    {
                        _status.value = STATUS_FINISHED
                        res.dismiss()
                    },
                    {
                        it.printStackTrace()
                        Log.e(TAG, "El mail no se pudo enviar correctamente: " + it.message)
                        res.dismiss()
                        ad.setMessage("Ocurrió un error")
                        ad.show()
                    }
                )
        }
        else {
            ad.setMessage("La contraseña debe tener más de 5 caracteres")
            ad.show()
        }

    }

    fun passwordIsValid(_password : String) : Boolean {
        if (_password.length < RegisterViewModel.MIN_PASSWORD) {
            return false
        }
        return true
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