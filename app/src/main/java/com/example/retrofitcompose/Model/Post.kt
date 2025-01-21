package com.example.retrofitcompose.Model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

fun formatPosts(posts: List<Post>): String {
    return posts.joinToString(separator = ",\n") { post ->
        """
        {userId=${post.userId}, id=${post.id}, title=${post.title}, body=${post.body}}
        """
    }.let { "[\n$it\n]" }
}