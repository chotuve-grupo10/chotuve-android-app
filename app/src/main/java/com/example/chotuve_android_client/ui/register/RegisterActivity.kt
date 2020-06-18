package com.example.chotuve_android_client.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chotuve_android_client.R
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

        val register = findViewById<Button>(R.id.register)

        register.setOnClickListener {
            registerViewModel.updateValues(fullName.text, phoneNumber.text, email.text, password.text)
            val result : String? = registerViewModel.validateData()
            Log.d(TAG, "Result es ${result}")
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
