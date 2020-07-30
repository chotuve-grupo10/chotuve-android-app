package com.example.chotuve_android_client.ui.notifications

import android.media.session.MediaSession
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.NotificationsRepository
import com.example.chotuve_android_client.data.UserRepository
import com.example.chotuve_android_client.models.UsersInformationList
import com.example.chotuve_android_client.tools.TokenHolder

class NotificationsViewModel : ViewModel() {

    private val repository: NotificationsRepository = NotificationsRepository()

    private val _title = MutableLiveData<String>().apply {
        this.value = ""
    }
    val title: LiveData<String> = _title

    private val _users_requesting = MutableLiveData<UsersInformationList>()
    val users_requesting : LiveData<UsersInformationList>
        get() = _users_requesting

    fun getRequestsFromServer() {
        repository.getFriendshipRequests(TokenHolder.username, _users_requesting, _title)
    }

}