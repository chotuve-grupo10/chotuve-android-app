package com.example.chotuve_android_client.tools

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.chotuve_android_client.MainActivity
import com.example.chotuve_android_client.data.model.UserCredentials
import com.example.chotuve_android_client.ui.login.*
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AppServerAuthenticator : Authenticator {

    private val UNAUTHORIZED = 401
    private val successResponseCode = 200
    private val TAG = "AuthenticatorOkHttp"
    private val refreshTokenGrantType = "refresh_token"

//    private val _loginResult = MutableLiveData<LoginResult>()
//    private val loginResult: LiveData<LoginResult> = _loginResult

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == UNAUTHORIZED) {
            // me logueo de nuevo para recibir un nuevo Token
            val credentials = TokenHolder.credentials
            when (credentials) {
                is UserCredentials.Password -> {
                    LoginHandler.login(
                        TokenHolder.username,
                        credentials.userLogin.password.toString(),
                        _loginResult,
                        null
                    )
                }
                is UserCredentials.FirebaseToken -> {
                    LoginHandler.loginWithFirebase(
                        TokenHolder.username,
                        credentials.token,
                        _loginResult,
                        null
                    )
                }
            }
            return null
        } else {
            Log.e(
                TAG,
                "This should never happen. NOT Unauthorized error called AppServerAuthenticator"
            )
            return null
        }
    }
}