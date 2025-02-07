package com.example.retrofitcompose.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcompose.Model.UserEntity
import com.example.retrofitcompose.Repository.RoomUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomUserViewModel(private val roomUserRepository: RoomUserRepository): ViewModel() {

    private val _userData = MutableStateFlow<List<UserEntity>>(emptyList())
    val userData: StateFlow<List<UserEntity>> = _userData


    fun loadUsers(){
        viewModelScope.launch {
            roomUserRepository.getAllUser()
                .collect { user ->
                    _userData.value = user
                }
        }
    }

    fun upsertData(username: String, password: String){
        viewModelScope.launch {
            val existingUser = roomUserRepository.getUserByUsername(username)
            if (existingUser != null){
                //if exist just changing the password
                val updateUser = existingUser.copy(password = password)
                roomUserRepository.upsertData(updateUser);
                println("Username $username is already exist")
            }else{
                //if new user just put all the data
                val newUser = UserEntity(username = username, password = password)
                roomUserRepository.upsertData(newUser)
                println("New user $username is Created")
            }
        }
    }

    suspend fun getUserByUsername(username: String): UserEntity? {
        return roomUserRepository.getUserByUsername(username)
    }

    suspend fun getPostById(id: Int): UserEntity? {
        return roomUserRepository.getUserById(id)
    }

    fun deletePost(id: Int) {
        viewModelScope.launch (Dispatchers.IO){
            roomUserRepository.deleteUser(id)
        }
    }

}