package com.example.retrofitcompose.Repository

import com.example.retrofitcompose.AppDatabase.PostDao
import com.example.retrofitcompose.Model.PostEntity

class RoomPostRepository(private val postDao: PostDao) {

    suspend fun upsertData(postEntity: PostEntity){
        postDao.upsertData(postEntity)
    }

    suspend fun getAllPosts(): List<PostEntity>{
        return postDao.getAllPosts()
    }

    suspend fun getPostById(id: Int): PostEntity? {
        return postDao.getPostById(id)
    }

    suspend fun deletePost(id: Int){
        postDao.deleteRow(id)
    }
}