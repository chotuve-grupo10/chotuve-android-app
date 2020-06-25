package com.example.chotuve_android_client.ui.userProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserProfileViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Welcome, some random user in here we would put your mail/username"
    }
    val text: LiveData<String> = _text

}