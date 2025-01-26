package com.example.retrofitcompose.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val password: String
)
