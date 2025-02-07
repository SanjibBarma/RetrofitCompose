package com.example.retrofitcompose.AppDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.retrofitcompose.Model.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertData(userEntity: UserEntity)

    @Query("SELECT * FROM user_table")
    fun getAllUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUserById(id: Int): UserEntity?

    @Query("SELECT * FROM user_table WHERE username = :name")
    suspend fun getUserByName(name: String): UserEntity?

    @Query("DELETE FROM user_table WHERE id = :id")
    suspend fun deleteRow(id: Int)
}