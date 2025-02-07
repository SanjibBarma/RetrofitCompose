package com.example.retrofitcompose.Repository

import com.example.retrofitcompose.AppDatabase.UserDao
import com.example.retrofitcompose.Model.UserEntity
import kotlinx.coroutines.flow.Flow

class RoomUserRepository(private val userDao: UserDao) {
    suspend fun upsertData(user: UserEntity){
        userDao.upsertData(user)
    }

    fun getAllUser(): Flow<List<UserEntity>>{
        return userDao.getAllUser()
    }

    suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id)
    }

    suspend fun getUserByUsername(name: String): UserEntity? {
        return userDao.getUserByName(name)
    }

    suspend fun deleteUser(id: Int){
        userDao.deleteRow(id)
    }
}