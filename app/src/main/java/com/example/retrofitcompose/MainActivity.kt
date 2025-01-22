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
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitcompose.AppDatabase.AppDatabase
import com.example.retrofitcompose.Navigation.Navigation
import com.example.retrofitcompose.Network.RetrofitInstance
import com.example.retrofitcompose.Repository.PostRepository
import com.example.retrofitcompose.Repository.RoomPostRepository
import com.example.retrofitcompose.Screen.LoginScreen
import com.example.retrofitcompose.ViewModel.AuthSharedViewModel
import com.example.retrofitcompose.ViewModel.AuthViewModelFactory
import com.example.retrofitcompose.ViewModel.PostViewModel
import com.example.retrofitcompose.ViewModel.RoomPostViewModel
import com.example.retrofitcompose.ViewModel.RoomPostViewModelFactory
import com.example.retrofitcompose.ui.theme.RetrofitComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val authViewModel: AuthSharedViewModel by viewModels {
            AuthViewModelFactory(applicationContext)
        }

        val db = AppDatabase.getDatabase(applicationContext)
        val roomPepository = RoomPostRepository(db.postDao())
        val viewModelFactory = RoomPostViewModelFactory(roomPepository)
        val roomViewModel = ViewModelProvider(this, viewModelFactory).get(RoomPostViewModel::class.java)

        val apiService = RetrofitInstance.apiService
        val repository = PostRepository(apiService)
        val postViewModel = PostViewModel(repository, roomViewModel)

        setContent {
            RetrofitComposeTheme {
                Surface (
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Navigation(
                        authViewModel,
                        postViewModel,
                        roomViewModel,
                    )
                }
            }
        }
    }
}
