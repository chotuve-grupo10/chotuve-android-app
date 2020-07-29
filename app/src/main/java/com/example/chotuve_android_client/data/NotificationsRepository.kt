package com.example.chotuve_android_client.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chotuve_android_client.models.UsersInformationList
import com.example.chotuve_android_client.services.GetRequestsService
import io.reactivex.disposables.CompositeDisposable

class NotificationsRepository {

    companion object {
        const val TAG = "NotificationsRepository"
        private val friendshipRequestsService : GetRequestsService = GetRequestsService()
    }

    fun getFriendshipRequests(user_email : String, _users_requesting : MutableLiveData<UsersInformationList>, _title : MutableLiveData<String>) {
        Log.d(TAG, "Now, getting friendship requests from server...")

        friendshipRequestsService.getRequests(
            user_email,
            CompositeDisposable(),
            {
                if (it != null) {
                    Log.d(UserRepository.TAG, "Tengo esta cantidad de usuarios: " + it.size.toString())
                    _users_requesting.value = it
                    if (it.size > 0) {
                        _title.value = "Tenes nuevas notificaciones!"
                    } else {
                        _title.value = "Parece que no tenes nuevas notificaciones"
                    }
                }
            },
            {
                it.printStackTrace()
                Log.d(TAG, "Error getting friendship requests for NotificationsFragment")
            }
        )

    }

}