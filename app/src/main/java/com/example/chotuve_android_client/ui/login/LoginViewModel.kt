package com.example.chotuve_android_client.ui.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import com.example.chotuve_android_client.data.Result

import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.services.LoginService
import io.reactivex.disposables.CompositeDisposable

class LoginViewModel(application: Application, private val loginService: LoginService) :
        AndroidViewModel(application) {

    companion object {
        const val TAG = "LoginViewModel"
        const val AUTHENTICATION_MODE = "AUTHENTICATION_MODE"
    }

    private var myCompositeDisposable: CompositeDisposable? = null

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        myCompositeDisposable = CompositeDisposable()
        loginService.login(username, password, myCompositeDisposable,
            {
                Log.d(TAG, "Login correcto, se obtiene el token ${it?.AppToken}")
                setAuthenticationModeInSharedPreferences("EMAIL_PASSWORD")
                setCredentialsInSharedPreferences(username, password)
                _loginResult.value =
                    LoginResult(success = it)
            },
            {
                it.printStackTrace()
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        )
    }

    private fun setAuthenticationModeInSharedPreferences(authenticationMode: String) {
        setSharedPreference(AUTHENTICATION_MODE, authenticationMode)
    }

    private fun setCredentialsInSharedPreferences(username: String, password: String) {
        setSharedPreference("username", username)
        setSharedPreference("password", password)
    }

    private fun setSharedPreference(key: String, value: String) {
        val usernamePreferences = getApplication<Application>()
            .getSharedPreferences(key, Context.MODE_PRIVATE)
        val usernameEditor = usernamePreferences.edit()
        usernameEditor.putString(key, value)
    }

    protected override fun onCleared() {
        super.onCleared()
        myCompositeDisposable?.clear()
    }

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
