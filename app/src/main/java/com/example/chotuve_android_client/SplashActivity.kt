package com.example.chotuve_android_client

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.chotuve_android_client.data.model.UserCredentials
import com.example.chotuve_android_client.ui.login.*


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
val DEFAULT_VALUE = "0"
class SplashActivity : AppCompatActivity() {

    val loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

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

        val username = application
            .getSharedPreferences(CHOTUVE_SHARED_PREFS, Context.MODE_PRIVATE)
            .getString(USERNAME_TAG "0")
        Log.d(TAG,"This is username SharedPreferences ${username}")
        if (username != DEFAULT_VALUE) {
            val password = application
                .getSharedPreferences(CHOTUVE_SHARED_PREFS, Context.MODE_PRIVATE)
                .getString(PASSWORD_TAG, "0")
            Log.d(TAG,"This is password SharedPreferences ${password}")

//            val loginViewModel = LoginViewModel()
//            if (username == FIREBASE_USER) {
//                Log.d(TAG, "About to login with firebase")
//            }

        }

        loginLauncher.launch(Intent(this, LoginActivity::class.java))

    }

    private fun redirectTo(activity: Class<out Activity>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }


//    private fun getCredentialsInSharedPreferences(username: String, password: String) {
//        setSharedPreference("username", username)
//        setSharedPreference("password", password)
//    }

}
