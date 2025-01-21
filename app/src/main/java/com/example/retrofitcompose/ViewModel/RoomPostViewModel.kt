package com.example.retrofitcompose.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcompose.Model.PostEntity
import com.example.retrofitcompose.Repository.RoomPostRepository
import kotlinx.coroutines.launch

class RoomPostViewModel(private val repository: RoomPostRepository): ViewModel() {

    private val _postData = mutableStateOf<List<PostEntity>>(emptyList())
    val postData: State<List<PostEntity>> get() = _postData


    init {
        loadPosts()
    }

    fun addData(posts: PostEntity){
        viewModelScope.launch {
            repository.insertData(posts)
            loadPosts()
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _postData.value = repository.getAllNotes()
        }
    }

    fun getPostDataSize(): Int {
        return _postData.value.size
    }

    suspend fun getPostById(postId: Int): PostEntity? {
        return repository.getPostById(postId)
    }

}