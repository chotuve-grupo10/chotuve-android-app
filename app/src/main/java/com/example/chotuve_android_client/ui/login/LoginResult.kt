package com.example.chotuve_android_client.ui.login

import com.example.chotuve_android_client.models.LoginResponse

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoginResponse? = null,
    val error: Int? = null
)
