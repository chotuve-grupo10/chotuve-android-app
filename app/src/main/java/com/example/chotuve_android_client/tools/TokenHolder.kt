package com.example.chotuve_android_client.tools

import com.example.chotuve_android_client.data.model.UserCredentials

object TokenHolder {

    lateinit var credentials: UserCredentials
    lateinit var appServerToken: String
    lateinit var authServerToken: String

    fun init(credentials: UserCredentials, appServerToken: String, authServerToken: String) {
        this.credentials = credentials
        this.appServerToken = appServerToken
        this.authServerToken = authServerToken
    }

}