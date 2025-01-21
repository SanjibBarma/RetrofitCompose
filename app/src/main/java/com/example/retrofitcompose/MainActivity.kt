package com.example.retrofitcompose

import PostScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.retrofitcompose.Network.RetrofitInstance
import com.example.retrofitcompose.Repository.PostRepository
import com.example.retrofitcompose.Screen.LoginScreen
import com.example.retrofitcompose.ViewModel.AuthViewModel
import com.example.retrofitcompose.ViewModel.AuthViewModelFactory
import com.example.retrofitcompose.ViewModel.PostViewModel
import com.example.retrofitcompose.ui.theme.RetrofitComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val apiService = RetrofitInstance.apiService
        val repository = PostRepository(apiService)
        val viewModel = PostViewModel(repository)

        val authViewModel: AuthViewModel by viewModels {
            AuthViewModelFactory(applicationContext)
        }


        setContent {
            RetrofitComposeTheme {
                val isLoggedIn = remember { mutableStateOf(false) }

                Surface (
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ){
                    if (isLoggedIn.value) {
                        PostScreen(
                            viewModel = viewModel,
                            onBackClick = { isLoggedIn.value = false }
                        )
                    } else {
                        // Show Login Screen
                        LoginScreen(
                            viewModel = authViewModel,
                            onLoginClick = { isLoggedIn.value = true }
                        )
                    }
                }
            }
        }
    }
}
