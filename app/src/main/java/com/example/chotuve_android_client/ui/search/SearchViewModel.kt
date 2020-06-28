package com.example.chotuve_android_client.ui.search

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.UserRepository
import com.example.chotuve_android_client.models.FriendsInformationList

class SearchViewModel : ViewModel() {

    companion object {
        const val TAG = "SearchViewModel"
    }

    private val repository: UserRepository = UserRepository()

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    private val _users = MutableLiveData<FriendsInformationList>()
    val users : LiveData<FriendsInformationList>
        get() = _users

    fun updateText(this_user : Editable?) {
        _text.value = this_user.toString()
    }

    fun getUsersWithFilter(filter : String?) {
        var string_filter : String? = null
        if (filter ==  null) {
            string_filter = ""
        } else {
            string_filter = filter
        }

        Log.d(TAG, "I'm about to get users with filter ${string_filter}")
        repository.getUsers(string_filter, _users)
    }


}