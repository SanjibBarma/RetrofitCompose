package com.example.retrofitcompose.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.retrofitcompose.Screen.LoginScreen
import com.example.retrofitcompose.Screen.PostDetailScreen
import com.example.retrofitcompose.Screen.PostScreen
import com.example.retrofitcompose.ViewModel.AuthSharedViewModel
import com.example.retrofitcompose.ViewModel.PostViewModel
import com.example.retrofitcompose.ViewModel.RoomPostViewModel

@Composable
fun Navigation(
    authViewModel: AuthSharedViewModel,
    postViewModel: PostViewModel,
    roomViewModel: RoomPostViewModel
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route){
        composable(route = Screen.LoginScreen.route){
            LoginScreen(
                viewModel = authViewModel,
                navController = navController
            )
        }

        composable(route = Screen.PostScreen.route){
            PostScreen(
                viewModel = postViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                roomViewModel = roomViewModel,
                navController = navController
            )
        }


        composable(
            route = Screen.PostDetailScreen.route+ "/{key}",
            arguments = listOf(
                navArgument("key"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){ entry ->
            val key = entry.arguments?.getInt("key") ?: -1
            PostDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                key = key.toString(),
                roomViewModel
            )
        }
    }
}