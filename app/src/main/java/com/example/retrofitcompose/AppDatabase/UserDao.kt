package com.example.retrofitcompose.AppDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.retrofitcompose.Model.PostEntity
import com.example.retrofitcompose.Model.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun upsertData(userEntity: UserEntity)

    @Query("SELECT * FROM user_table")
    suspend fun getAllUser(): List<UserEntity>

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity?

    @Query("DELETE FROM user_table WHERE id = :id")
    suspend fun deleteRow(id: Int)
}