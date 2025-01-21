package com.example.retrofitcompose.Helper

import com.example.retrofitcompose.Model.Post
import com.google.gson.Gson
import com.google.gson.GsonBuilder

fun formatPostsAsJson(posts: List<Post>): String {
    val gson = GsonBuilder().setPrettyPrinting().create()
    return gson.toJson(posts)
}