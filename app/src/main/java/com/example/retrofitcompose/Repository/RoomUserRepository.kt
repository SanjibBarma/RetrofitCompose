package com.example.retrofitcompose.Repository

import com.example.retrofitcompose.AppDatabase.UserDao
import com.example.retrofitcompose.Model.UserEntity

class RoomUserRepository(private val userDao: UserDao) {
    suspend fun upsertData(user: UserEntity){
        userDao.upsertData(user)
    }

    suspend fun getAllUser(): List<UserEntity>{
        return userDao.getAllUser()
    }

    suspend fun getUserById(id: Int): UserEntity? {
        return userDao.getUserById(id)
    }

    suspend fun deleteUser(id: Int){
        userDao.deleteRow(id)
    }
}