package com.example.chotuve_android_client.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.UserRepository
import com.example.chotuve_android_client.models.UsersInformationList
import com.example.chotuve_android_client.tools.TokenHolder

class NotificationsViewModel : ViewModel() {

//    private val repository: NotificationsRepository = NotificationsRepository()

    private val _text = MutableLiveData<String>().apply {
        value = "Cuando tengas solicitudes de amistad las vas a ver acá"
    }
    val text: LiveData<String> = _text

    private val _friends = MutableLiveData<UsersInformationList>()
    val friends : LiveData<UsersInformationList>
        get() = _friends

}