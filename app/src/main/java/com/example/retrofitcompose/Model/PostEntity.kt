package com.example.retrofitcompose.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "posts_table")
data class PostEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val userId: Int,
    val title: String,
    val value: String,
)
