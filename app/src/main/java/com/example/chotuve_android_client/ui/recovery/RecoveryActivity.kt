package com.example.chotuve_android_client.ui.recovery

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.example.chotuve_android_client.R
import com.google.android.material.textfield.TextInputEditText

class RecoveryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery)

        val TAG = "RecoveryActivity"
        val alertDialogBuilder = AlertDialog.Builder(this)
        val recoveryModel = RecoveryModel()

        val firstTitle = findViewById<TextView>(R.id.text_first)
        recoveryModel.firstText.observe(this, Observer {
            firstTitle.text = it
        })

        val email = findViewById<TextInputEditText>(R.id.email_recovery)

        val sendToken = findViewById<Button>(R.id.button_send_token)
        sendToken.setOnClickListener {
            val result = recoveryModel.sendTokenForPasswordRecovery(email.text.toString(), alertDialogBuilder)
        }

        val token = findViewById<TextInputEditText>(R.id.token)
        token.setVisibility(View.GONE)
        val newPassword = findViewById<TextInputEditText>(R.id.new_password)
        newPassword.setVisibility(View.GONE)

        val sendButton = findViewById<Button>(R.id.button_send_new_password)
        sendButton.setVisibility(View.GONE)
        sendButton.setOnClickListener{
            Log.d(TAG, "Fin del flujo")
            val respuesta = Intent()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

//    private fun showRecoveryFailed(errorString: String) {
//        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
//    }


}