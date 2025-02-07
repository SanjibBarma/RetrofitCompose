package com.example.retrofitcompose.AppDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitcompose.Model.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertData(postEntity: PostEntity)

    @Query("SELECT * FROM posts_table")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts_table WHERE id = :id")
    suspend fun getPostById(id: Int): PostEntity?

    @Query("DELETE FROM posts_table WHERE id = :id")
    suspend fun deleteRow(id: Int)
}