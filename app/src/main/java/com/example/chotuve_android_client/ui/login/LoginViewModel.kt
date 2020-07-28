package com.example.chotuve_android_client.ui.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel

import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.data.model.UserCredentials
import com.example.chotuve_android_client.models.UserLogin
import com.example.chotuve_android_client.services.LoginService
import com.example.chotuve_android_client.tools.TokenHolder
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.disposables.CompositeDisposable

const val GOOGLE_SIGN_IN : Int = 9001
const val CHOTUVE_SHARED_PREFS : String = "chotuve-android-app"
const val USERNAME_TAG : String = "username"
const val PASSWORD_TAG : String = "password"
const val FIREBASE_USER : String = "Firebase user"

class LoginViewModel(
    application: Application,
    private val loginService: LoginService,
    private val googleSignInClient: GoogleSignInClient
) :
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

    public fun login(username: String, password: String) {
        myCompositeDisposable = CompositeDisposable()
        loginService.login(username, password, myCompositeDisposable,
            {
                Log.d(TAG, "Login correcto, se obtiene el token ${it?.AppToken}")
                TokenHolder.init(
                    username,
                    UserCredentials.Password(UserLogin(username, password)),
                    requireNotNull(it?.AppToken),
                    requireNotNull(it?.AuthToken))
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

    // TODO refactor (ver que se repite la logica del metodo anterior)
    public fun loginWithFirebase(username : String, firebaseToken : String) {
        Log.d(TAG, "Username es ${username}")
        myCompositeDisposable = CompositeDisposable()
        loginService.login(firebaseToken, myCompositeDisposable,
            {
                Log.d(TAG, "Login correcto, se obtiene el token ${it?.AppToken}")
                TokenHolder.init(
                    username,
                    UserCredentials.FirebaseToken(firebaseToken),
                    requireNotNull(it?.AppToken),
                    requireNotNull(it?.AuthToken))
                setAuthenticationModeInSharedPreferences("FIREBASE_TOKEN")
                setCredentialsInSharedPreferences("Firebase user", firebaseToken)
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
        setSharedPreference(USERNAME_TAG, username)
        setSharedPreference(PASSWORD_TAG, password)
    }

    private fun setSharedPreference(key: String, value: String) {
        val usernamePreferences = getApplication<Application>()
            .getSharedPreferences(CHOTUVE_SHARED_PREFS, Context.MODE_PRIVATE)
        val usernameEditor = usernamePreferences.edit()
        usernameEditor.putString(key, value).apply()
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
