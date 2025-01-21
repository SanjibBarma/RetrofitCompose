package com.example.retrofitcompose.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitcompose.Repository.PostRepository

class PostViewModelFactory(
    private val repository: PostRepository,
    private val roomPostViewModel: RoomPostViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(repository, roomPostViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
