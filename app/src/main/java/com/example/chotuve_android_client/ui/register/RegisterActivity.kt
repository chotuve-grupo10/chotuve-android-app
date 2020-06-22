package com.example.chotuve_android_client.ui.register

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {

    companion object {
        const val TAG = "RegisterActivity"
        const val FULLNAME = 0
        const val PHONENUMBER = 1
        const val EMAIL = 2
        const val PASSWORD = 3
    }

    private lateinit var registerViewModel : RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        registerViewModel = RegisterViewModel()

        val fullName = findViewById<TextInputEditText>(R.id.full_name_register)
        val phoneNumber = findViewById<TextInputEditText>(R.id.phone_number)
        val email = findViewById<TextInputEditText>(R.id.email)
        val password = findViewById<TextInputEditText>(R.id.password)

        registerViewModel.registerResult.observe(this, Observer { registerResult->

            if (registerResult.error != null) {
                showLoginFailed(registerResult.error)
            }
            if (registerResult.success != null) {
                Log.d(TAG, "Ahora nos vamos al activity de login")
                // Comento lo que estaba haciendo antes!
//                val i = Intent(this, LoginActivity::class.java)
//                finish()  // supongo que esto rompía, pero en realidad me lo robé del loginActivity :(
//                startActivity(i)
                val respuesta = Intent()
                respuesta.putExtra("email", email.text)
                respuesta.putExtra("password", password.text)
                setResult(Activity.RESULT_OK, respuesta)
                finish()
            }
        })


        val register = findViewById<Button>(R.id.register)

        register.setOnClickListener {
            registerViewModel.updateValues(fullName.text, phoneNumber.text, email.text, password.text)
            val result : String? = registerViewModel.validateData()
            if (result != null) {
                showLoginFailed(result)
            }
        }

    }

    private fun showLoginFailed(errorString: String) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
