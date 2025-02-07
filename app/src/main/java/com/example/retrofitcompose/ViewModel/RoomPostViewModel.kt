package com.example.retrofitcompose.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcompose.Model.PostEntity
import com.example.retrofitcompose.Repository.RoomPostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomPostViewModel(private val repository: RoomPostRepository): ViewModel() {

    private val _postData = mutableStateOf<List<PostEntity>>(emptyList())
    val postData: State<List<PostEntity>> get() = _postData

    fun upsertData(posts: PostEntity){
        viewModelScope.launch {
            repository.upsertData(posts)
        }
    }

    fun loadPostsFromRoom() {
        viewModelScope.launch {
            repository.getAllPosts()
                .collect{
                    post ->
                    _postData.value = post
                }
        }
    }

    suspend fun getPostById(id: Int): PostEntity? {
        return repository.getPostById(id)
    }

    fun deletePost(id: Int) {
        viewModelScope.launch (Dispatchers.IO){
            repository.deletePost(id)
        }
    }

}