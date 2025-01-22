package com.example.retrofitcompose.AppDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.retrofitcompose.Model.PostEntity

@Dao
interface PostDao {
    @Insert
    suspend fun insert(postEntity: PostEntity)

    @Query("SELECT * FROM posts_table")
    suspend fun getAllPosts(): List<PostEntity>

    @Query("SELECT * FROM posts_table WHERE id = :id")
    suspend fun getById(id: Int): PostEntity?

    @Query("SELECT * FROM posts_table WHERE id = :id LIMIT 1")
    suspend fun getPostById(id: Int): PostEntity?

    @Query("DELETE FROM posts_table WHERE id = :id")
    suspend fun deleteRow(id: Int)
}