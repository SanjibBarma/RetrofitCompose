package com.example.retrofitcompose.Repository

import com.example.retrofitcompose.Network.ApiService
import com.example.retrofitcompose.Model.Post

class PostRepository(private val apiService: ApiService) {

    suspend fun fetchPosts(): List<Post>{
        return apiService.getPosts()
    }
}