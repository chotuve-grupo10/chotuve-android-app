package com.example.chotuve_android_client.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chotuve_android_client.R
import com.example.chotuve_android_client.models.LoginResponse
import com.example.chotuve_android_client.ui.register.RegisterActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var gso : GoogleSignInOptions
    private lateinit var googleSignInClient : GoogleSignInClient
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val callbackManager = CallbackManager.Factory.create()
    val googleLoginLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
                println("ACA ENTRA AL ACTIVITY RESULT!!!!")
                //TODO manejar it.resultCode != ActivityResult.RESULT_OK
                val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    println("ACCOUNT::::: " + account?.idToken)
                    login(GoogleAuthProvider.getCredential(account?.idToken, null))
                } catch (e : ApiException) {
                    e.printStackTrace()
                }
                println()
    }
    val registerNewUserLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
                Log.d("LoginActivity", "Resultado de la registracion: " + it.resultCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<TextView>(R.id.register_label)
        val googleSignInButton = findViewById<SignInButton>(R.id.google_sign_in_button);
        val facebookLoginButton = findViewById<LoginButton>(R.id.fb_login_button);
        val loading = findViewById<ProgressBar>(R.id.loading)

        // Facebook
        facebookLoginButton.setPermissions("email")

        facebookLoginButton.registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult?) {
                    Log.d("LoginActivity", "Facebook token: " + loginResult?.accessToken?.token)
                    login(FacebookAuthProvider.getCredential(loginResult?.accessToken!!.token))
                }

                override fun onCancel() {
                    Log.d("LoginActivity", "onCancel")
                }

                override fun onError(error: FacebookException?) {
                    Log.d("LoginActivity", "onError", error)
                }
            })

        // Google
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        loginViewModel = ViewModelProviders.of(this,
                LoginViewModelFactory(application, googleSignInClient))
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
                setResult(Activity.RESULT_OK)

                //Complete and destroy login activity once successful
                finish()
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }

        googleSignInButton.setOnClickListener {
            val signInIntent: Intent = googleSignInClient.signInIntent
            googleLoginLauncher.launch(signInIntent)
        }

        register.setOnClickListener {
            val registerIntent : Intent = Intent(this, RegisterActivity::class.java)
            registerNewUserLauncher.launch(registerIntent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("LoginActivity", "Resultado de Activity en onActivityResult: " + resultCode)
        Log.d("LoginActivity", "requestCode en onActivityResult: " + requestCode)

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateUiWithUser(model: LoginResponse) {
        val welcome = "Your App token is"
        val displayName = model.AppToken
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    fun login(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("firebaseAuthWithGoogle", "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        user.getIdToken(true)
                            .addOnCompleteListener(OnCompleteListener<GetTokenResult> { task ->
                                if (task.isSuccessful) {
                                    val idToken = task.result!!.token
                                    Log.d("firebaseAuthWithGoogle"
                                        ,
                                        "TOKEN:$idToken"
                                    )
                                    loginViewModel.login(idToken!!)
                                } else {
                                    // TODO Handle error -> task.getException();
                                }
                            })
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("firebaseAuthWithGoogle", "signInWithCredential:failure", task.exception)
                }
            }
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
