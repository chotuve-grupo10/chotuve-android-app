package com.example.chotuve_android_client.ui.myUserProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.UserRepository
import com.example.chotuve_android_client.models.UsersInformationList
import com.example.chotuve_android_client.tools.TokenHolder

class MyUserProfileViewModel : ViewModel() {

    private val repository: UserRepository = UserRepository()

    private val _text = MutableLiveData<String>().apply {
        // TODO: nice to have aca el fullName, o algo más amigable que el mail
        value = "Este es tu muro, ${TokenHolder.username}"
    }
    val text: LiveData<String> = _text

    private val _friends = MutableLiveData<UsersInformationList>()
    val friends : LiveData<UsersInformationList>
        get() = _friends

    private val _URL = MutableLiveData<String>().apply {
        value = ""
    }
    val URL : LiveData<String>
        get() = _URL

    fun getFriendsFromServer() {
        repository.getFriendsInformation(TokenHolder.username, _friends);
    }

    fun getUserProfilePicture() {
        repository.getUserProfilePicture(_URL)
    }

}