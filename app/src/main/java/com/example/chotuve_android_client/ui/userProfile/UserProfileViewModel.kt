package com.example.chotuve_android_client.ui.userProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.UserRepository
import com.example.chotuve_android_client.tools.TokenHolder

class UserProfileViewModel : ViewModel() {

    private val repository: UserRepository = UserRepository()

    private val _text = MutableLiveData<String>().apply {
        value = "Welcome, ${TokenHolder.username}"
    }
    val text: LiveData<String> = _text

    private val _num = MutableLiveData<Int>()

    fun getFriendsFromServer() {
        repository.getFriendsInformation(TokenHolder.username, _num);
    }

}