package com.example.chotuve_android_client.ui.register

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.services.RegisterService
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class RegisterViewModel() {

    companion object {
        const val TAG = "RegisterViewModel"
        const val MIN_PASSWORD = 5
    }

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

    private var myCompositeDisposable: CompositeDisposable? = null
    private val registerService = RegisterService()

    private var _fullName : String? = null
    private var _phoneNumber : String? = null
    private var _email : String? = null
    private var _password : String? = null

    fun register() {
        myCompositeDisposable = CompositeDisposable()
        registerService.register(
            _fullName.toString(),
            _phoneNumber.toString(),
            _email.toString(),
            _password.toString(),
            "image01.jpg",
            myCompositeDisposable,
            {
                if (it != null) {
                    Log.d(TAG, "Usuario registrado con éxito: " + it.messageResult.toString())
                    // Me gustaría ahora que me lleve a Login!
                    _registerResult.value =
                        RegisterResult(success = it)
                }
            }, {
                Log.e(TAG, "La operación de registro no funcionó: " + it.toString())
                it.printStackTrace()
                _registerResult.value = RegisterResult(error="Error registrando usuario. Tu solicitud falló")
            })
    }

    fun validateData(): String? {
        Log.d(TAG, "Full Name ${_fullName}. " + "Phone number ${_phoneNumber}. " +
            "Email ${_email}. " + "Password ${_password}.\n")
        if (!itemIsValid(_fullName)) {
            Log.d(TAG, "sí, no es valido el fullName")
            return "Parece que no nos proporcionaste un nombre. Lo necesitamos!"
        }
        else if (!itemIsValid(_phoneNumber)) {
            return "Necesitamos que ingreses un número de contacto"
        }
        else if (!emailIsValid()) {
            return "Vamos a necesitar un email válido"
        }
        else if (!passwordIsValid()) {
            return "Tu contraseña debe tener al menos 5 caracteres"
        }
        register()
        return null
    }

    fun itemIsValid(_item : String?) : Boolean {
        if (_item == null) {
            return false
        } else if (_item.length == 0) {
            return false
        }
        return true
    }

    fun emailIsValid() : Boolean {
        if (_email == null){
            return false
        } else if(_email!!.length == 0) {
            return false
        } else if (! _email!!.contains("@", ignoreCase = true)) {
            return false
        } else if (! _email!!.contains(".", ignoreCase = true)) {
            return false
        }
        return true
    }
    fun passwordIsValid() : Boolean {
        if (_password == null){
            return false
        } else if (_password!!.length < MIN_PASSWORD) {
            return false
        }
        return true
    }

    fun updateValues(
        fullName: Editable?,
        phoneNumber: Editable?,
        email: Editable?,
        password: Editable?
    ) {
        _fullName = fullName.toString()
        _phoneNumber= phoneNumber.toString()
        _email= email.toString()
        _password= password.toString()
    }
}
