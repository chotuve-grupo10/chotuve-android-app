package com.example.chotuve_android_client.ui.register

import android.text.Editable
import android.util.Log
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.data.model.UserCredentials
import com.example.chotuve_android_client.models.UserLogin
import com.example.chotuve_android_client.tools.TokenHolder
import com.example.chotuve_android_client.ui.login.LoginResult
import com.example.chotuve_android_client.ui.login.LoginViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class RegisterViewModel {

    companion object {
        const val TAG = "RegisterViewModel"
        const val MIN_PASSWORD = 5
    }

    private var myCompositeDisposable: CompositeDisposable? = null

    private var _fullName : String? = null
    private var _phoneNumber : String? = null
    private var _email : String? = null
    private var _password : String? = null

    fun register() {
        myCompositeDisposable = CompositeDisposable()
//        loginService.login(username, password, myCompositeDisposable,
//            {
//                Log.d(LoginViewModel.TAG, "Login correcto, se obtiene el token ${it?.AppToken}")
//                TokenHolder.init(
//                    username,
//                    UserCredentials.Password(UserLogin(username, password)),
//                    requireNotNull(it?.AppToken),
//                    requireNotNull(it?.AuthToken))
//                setAuthenticationModeInSharedPreferences("EMAIL_PASSWORD")
//                setCredentialsInSharedPreferences(username, password)
//                _loginResult.value =
//                    LoginResult(success = it)
//            },
//            {
//                it.printStackTrace()
//                _loginResult.value = LoginResult(error = R.string.login_failed)
//            }
//        )
        // registerService
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
        return null;

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
