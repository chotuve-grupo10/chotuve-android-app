package com.example.chotuve_android_client.data.model

import com.example.chotuve_android_client.models.UserLogin

sealed  class UserCredentials {
    data class Password (val credentials: UserLogin) : UserCredentials()

}