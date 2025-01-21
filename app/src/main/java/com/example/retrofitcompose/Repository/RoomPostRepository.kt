package com.example.retrofitcompose.Repository

import com.example.retrofitcompose.AppDatabase.PostDao
import com.example.retrofitcompose.Model.PostEntity

class RoomPostRepository(private val postDao: PostDao) {

    suspend fun insertData(postEntity: PostEntity){
        postDao.insert(postEntity)
    }

    suspend fun getDataById(id: Int): PostEntity?{
        return postDao.getById(id)
    }

    suspend fun getAllNotes(): List<PostEntity>{
        return postDao.getAllPosts()
    }

    suspend fun getPostById(id: Int): PostEntity? {
        return postDao.getPostById(id)
    }
}