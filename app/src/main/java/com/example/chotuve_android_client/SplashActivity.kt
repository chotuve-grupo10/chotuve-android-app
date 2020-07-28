package com.example.chotuve_android_client

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.chotuve_android_client.ui.login.*

const val DEFAULT_VALUE = "0"
class SplashActivity : AppCompatActivity() {

    val loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val TAG = "SplashActivity"

        setContentView(R.layout.activity_splash)
//        val accountManager = AccountManager.get(this)
//        val accounts = accountManager.getAccountsByType("com.chotuve")
//        if (accounts.isEmpty()) {
//            redirectTo(LoginActivity::class.java)
////            loginLauncher.launch(Intent(this, LoginActivity::class.java))
//        } else {
//           redirectTo(MainActivity::class.java)
//        }

        val loginMethod =  getFromSharedPreferences(AUTHENTICATION_MODE)
        Log.d(TAG, "This is login method from SharedPreferences ${loginMethod}")
        val username = getFromSharedPreferences(USERNAME_TAG)
        Log.d(TAG,"This is username SharedPreferences ${username}")
        val password = getFromSharedPreferences(PASSWORD_TAG)
        Log.d(TAG,"This is password SharedPreferences ${password}")
        if (loginMethod == EMAIL_AUTHENTICATION) {
            LoginHandler.login(username.toString(), password.toString(), _loginResult, this.application)
        } else if (loginMethod == FIREBASE_AUTHENTICATION) {
            LoginHandler.loginWithFirebase(username.toString(), password.toString(), _loginResult, this.application)
        } else {
            loginLauncher.launch(Intent(this, LoginActivity::class.java))
        }

        loginResult.observe(this, Observer {
            if (it.error != null) {
                loginLauncher.launch(Intent(this, LoginActivity::class.java))
            }
            if (it.success != null) {
                setResult(Activity.RESULT_OK)
                //Complete and destroy login activity once successful
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
    }

    fun getFromSharedPreferences(key : String) : String? {
        return application
            .getSharedPreferences(CHOTUVE_SHARED_PREFS, Context.MODE_PRIVATE)
            .getString(key, DEFAULT_VALUE)
    }
}
