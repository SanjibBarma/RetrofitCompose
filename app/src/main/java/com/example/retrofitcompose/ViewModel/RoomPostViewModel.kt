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

    fun upsertData(posts: PostEntity){
        viewModelScope.launch {
            val existPost = repository.getPostById(posts.id)
            if (existPost != null){
                println("Post id ${posts.id} is already exist")
            }else{
                repository.upsertData(posts)
                loadPosts()
            }
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            _postData.value = repository.getAllPosts()
        }
    }

    suspend fun getPostById(id: Int): PostEntity? {
        return repository.getPostById(id)
    }

    suspend fun deletePost(id: Int) {
        repository.deletePost(id)
        loadPosts()
    }

}