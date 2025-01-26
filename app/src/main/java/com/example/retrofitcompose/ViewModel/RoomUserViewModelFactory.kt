package com.example.retrofitcompose.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitcompose.Repository.RoomUserRepository

class RoomUserViewModelFactory(private val repository: RoomUserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomUserViewModel::class.java)) {
            return RoomUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}