package com.example.retrofitcompose.Network

import com.example.retrofitcompose.Model.Post
import retrofit2.http.GET

interface ApiService {
    @GET("/posts")
    suspend fun getPosts(): List<Post>
}