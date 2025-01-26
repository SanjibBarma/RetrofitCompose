package com.example.retrofitcompose.ViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcompose.Model.UserEntity
import com.example.retrofitcompose.Repository.RoomUserRepository
import kotlinx.coroutines.launch

class RoomUserViewModel(private val roomUserRepository: RoomUserRepository): ViewModel() {

    private val _userData = mutableStateOf<List<UserEntity>>(emptyList())
    val userData: State<List<UserEntity>> get() = _userData


    init {
        loadAllUser()
    }

    fun upsertData(user: UserEntity){
        viewModelScope.launch {
            val existPost = roomUserRepository.getUserById(user.id)
            if (existPost != null){
                println("Post id ${user.id} is already exist")
            }else{
                roomUserRepository.upsertData(user)
                loadAllUser()
            }
        }
    }

    suspend fun getPostById(id: Int): UserEntity? {
        return roomUserRepository.getUserById(id)
    }

    suspend fun deletePost(id: Int) {
        roomUserRepository.deleteUser(id)
        loadAllUser()
    }

    private fun loadAllUser() {
        viewModelScope.launch {
            _userData.value = roomUserRepository.getAllUser()
        }
    }
}