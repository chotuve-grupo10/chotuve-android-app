package com.example.chotuve_android_client.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.services.LoginService
import com.google.android.gms.auth.api.signin.GoogleSignInClient

const val GOOGLE_SIGN_IN : Int = 9001
const val CHOTUVE_SHARED_PREFS : String = "chotuve-android-app"
const val USERNAME_TAG : String = "username"
const val PASSWORD_TAG : String = "password"
const val EMAIL_AUTHENTICATION : String = "EMAIL_PASSWORD"
const val FIREBASE_AUTHENTICATION : String = "FIREBASE_TOKEN"
const val AUTHENTICATION_MODE = "AUTHENTICATION_MODE"

class LoginViewModel(
    application: Application,
    private val loginService: LoginService,
    private val googleSignInClient: GoogleSignInClient
) :
        AndroidViewModel(application) {

    companion object {
        const val TAG = "LoginViewModel"
    }

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) =
        LoginHandler.login(username, password, _loginResult, this.getApplication())

    fun loginWithFirebase(username : String, firebaseToken : String)  =
        LoginHandler.loginWithFirebase(username, firebaseToken, _loginResult, this.getApplication())

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}
