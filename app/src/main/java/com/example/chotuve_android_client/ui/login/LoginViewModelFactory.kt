package com.example.chotuve_android_client.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chotuve_android_client.services.LoginService
import com.google.android.gms.auth.api.signin.GoogleSignInClient

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(
    val app: Application,
    val googleSignInClient: GoogleSignInClient
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                application = app,
                googleSignInClient = googleSignInClient,
                loginService = LoginService()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
