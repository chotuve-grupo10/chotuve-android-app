package com.example.chotuve_android_client.ui.recovery

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
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
        val recoveryModel = RecoveryViewModel()

        val firstTitle = findViewById<TextView>(R.id.text_first)
        val email = findViewById<TextInputEditText>(R.id.email_recovery)
        val sendToken = findViewById<Button>(R.id.button_send_token)
        val token = findViewById<TextInputEditText>(R.id.token)
        val newPassword = findViewById<TextInputEditText>(R.id.new_password)
        val sendButton = findViewById<Button>(R.id.button_send_new_password)

        recoveryModel.firstText.observe(this, Observer {
            firstTitle.text = it
        })

        sendToken.setOnClickListener {
            recoveryModel.sendTokenForPasswordRecovery(
                email.text.toString(),
                alertDialogBuilder
            )
        }
        sendButton.setOnClickListener{
            recoveryModel.sendNewPassword(
                token.text.toString(),
                newPassword.text.toString(),
                alertDialogBuilder
            )
        }

        recoveryModel.status.observe(this, Observer {
            if (it == RecoveryViewModel.STATUS_INIT) {
                token.setVisibility(View.GONE)
                newPassword.setVisibility(View.GONE)
                sendButton.setVisibility(View.GONE)
            } else if (it == RecoveryViewModel.STATUS_TOKEN_SENT) {
                email.setVisibility(View.GONE)
                sendToken.setVisibility(View.GONE)

                token.setVisibility(View.VISIBLE)
                newPassword.setVisibility(View.VISIBLE)
                sendButton.setVisibility(View.VISIBLE)
            } else if (it == RecoveryViewModel.STATUS_FINISHED) {
                Log.d(TAG, "Fin del flujo. Volvemos a la Login Activity")
                setResult(Activity.RESULT_OK)
                finish()
            }
        })

    }

//    private fun showRecoveryFailed(errorString: String) {
//        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
//    }


}