package com.example.chotuve_android_client.ui.search

import android.text.Editable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chotuve_android_client.data.UserRepository
import com.example.chotuve_android_client.models.UsersInformationList
import com.example.chotuve_android_client.tools.TokenHolder

class SearchViewModel : ViewModel() {

    companion object {
        const val TAG = "SearchViewModel"
    }

    private val repository: UserRepository = UserRepository()

    private val _text = MutableLiveData<String>().apply {
        value = ""
    }
    val text: LiveData<String> = _text

    private val _users = MutableLiveData<UsersInformationList>()
    val users : LiveData<UsersInformationList>
        get() = _users

    fun updateText(this_user : Editable?) {
        _text.value = "Tu búsqueda: " + this_user.toString()
    }

    fun getUsersWithFilter(filter : String?) {
        var string_filter : String?
        if (filter ==  null) {
            string_filter = ""
        } else {
            string_filter = filter
        }

        Log.d(TAG, "I'm about to get users with filter ${string_filter}")
        repository.getUsers(TokenHolder.username, string_filter, _users)
    }


}