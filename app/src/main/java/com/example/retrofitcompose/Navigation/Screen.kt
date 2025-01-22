package com.example.retrofitcompose.Navigation

sealed class Screen (val route: String){
    object PostScreen: Screen("post_screen")
    object LoginScreen: Screen("login_screen")
    object PostDetailScreen: Screen("post_detail")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg->
                append("/$arg")
            }
        }
    }
}