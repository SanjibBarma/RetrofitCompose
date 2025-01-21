package com.example.retrofitcompose.ViewModel

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcompose.Utils.SharedPrefHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(private val context: Context) : ViewModel() {

    private val sharedPreferenceHelper = SharedPrefHelper(context)

    val users = mutableStateListOf<Pair<String, String>>()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            val allUsers = withContext(Dispatchers.IO) {
                sharedPreferenceHelper.getAllUsers()
            }
            users.clear()
            users.addAll(allUsers)
            println("Users loaded: $users")
        }
    }

    fun saveData(key: String, value: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sharedPreferenceHelper.saveData(key, value)
            }
            loadUsers()
        }
    }

    fun deleteData(key: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sharedPreferenceHelper.deleteData(key)
            }
            loadUsers()
        }
    }

    fun clearData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sharedPreferenceHelper.clearData()
            }
            loadUsers()
        }
    }

    fun getData(key: String): String? {
        return sharedPreferenceHelper.getData(key)
    }

}
