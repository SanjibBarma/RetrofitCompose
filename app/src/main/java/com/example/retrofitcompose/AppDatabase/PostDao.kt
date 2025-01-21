//package com.example.retrofitcompose.AppDatabase
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.example.retrofitcompose.Model.PostEntity
//
//@Dao
//interface PostDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertPosts(posts: List<PostEntity>)
//
//    @Query("SELECT * FROM posts")
//    suspend fun getAllPosts(): List<PostEntity>
//
//    @Query("SELECT * FROM posts WHERE id = :id")
//    suspend fun getPostById(id: Int): PostEntity
//
//    @Query("DELETE FROM posts")
//    suspend fun clearTable()
//}