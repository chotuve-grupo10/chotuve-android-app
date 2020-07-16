package com.example.chotuve_android_client.tools

import com.example.chotuve_android_client.data.model.UserCredentials

object TokenHolder {

    lateinit var username: String
    lateinit var credentials: UserCredentials
    lateinit var appServerToken: String
    lateinit var authServerToken: String

    fun init(username: String, credentials: UserCredentials, appServerToken: String,
             authServerToken: String) {
        this.username = username
        this.credentials = credentials
        this.appServerToken = appServerToken
        this.authServerToken = authServerToken
    }

}