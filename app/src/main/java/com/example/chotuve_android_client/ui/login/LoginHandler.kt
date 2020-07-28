package com.example.chotuve_android_client.ui.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.data.model.UserCredentials
import com.example.chotuve_android_client.models.UserLogin
import com.example.chotuve_android_client.services.LoginService
import com.example.chotuve_android_client.tools.TokenHolder
import io.reactivex.disposables.CompositeDisposable

object LoginHandler {

    private val loginService = LoginService()

    fun login(username: String, password: String, _loginResult : MutableLiveData<LoginResult>, application: Application) {
        loginService.login(
            username,
            password,
            CompositeDisposable(),
            {
                Log.d(LoginViewModel.TAG, "Login correcto, se obtiene el token ${it?.AppToken}")
                TokenHolder.init(
                    username,
                    UserCredentials.Password(UserLogin(username, password)),
                    requireNotNull(it?.AppToken),
                    requireNotNull(it?.AuthToken))
                setAuthenticationModeInSharedPreferences(application, "EMAIL_PASSWORD")
                setCredentialsInSharedPreferences(application, username, password)
                _loginResult.value =
                    LoginResult(success = it)
            },
            {
                it.printStackTrace()
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        )
    }

    fun loginWithFirebase(username : String, firebaseToken : String, _loginResult : MutableLiveData<LoginResult>, application: Application) {
        Log.d(LoginViewModel.TAG, "Username es ${username}")

        loginService.login(
            firebaseToken,
            CompositeDisposable(),
            {
                Log.d(LoginViewModel.TAG, "Login correcto, se obtiene el token ${it?.AppToken}")
                TokenHolder.init(
                    username,
                    UserCredentials.FirebaseToken(firebaseToken),
                    requireNotNull(it?.AppToken),
                    requireNotNull(it?.AuthToken))
                setAuthenticationModeInSharedPreferences(application, "FIREBASE_TOKEN")
                setCredentialsInSharedPreferences(application,"Firebase user", firebaseToken)
                _loginResult.value =
                    LoginResult(success = it)
            },
            {
                it.printStackTrace()
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        )
    }

    private fun setAuthenticationModeInSharedPreferences(application : Application, authenticationMode: String) {
        setSharedPreference(application, AUTHENTICATION_MODE, authenticationMode)
    }

    private fun setCredentialsInSharedPreferences(application : Application, username: String, password: String) {
        setSharedPreference(application, USERNAME_TAG, username)
        setSharedPreference(application, PASSWORD_TAG, password)
    }

    private fun setSharedPreference(application : Application, key: String, value: String) {
        val usernamePreferences = application
            .getSharedPreferences(CHOTUVE_SHARED_PREFS, Context.MODE_PRIVATE)
        val usernameEditor = usernamePreferences.edit()
        usernameEditor.putString(key, value).apply()
    }



}