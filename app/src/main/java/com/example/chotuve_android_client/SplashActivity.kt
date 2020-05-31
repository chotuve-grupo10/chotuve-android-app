package com.example.chotuve_android_client

import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.chotuve_android_client.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
    //    val accountManager = AccountManager.get(this)
    //    val accounts = accountManager.getAccountsByType("com.chotuve")
    //    if (accounts.isEmpty()) {
//            redirectTo(LoginActivity::class.java)
    //    } else {
           redirectTo(MainActivity::class.java)
    //    }
    }

    private fun redirectTo(activity: Class<out Activity>) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }
}
