package com.example.retrofitcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitcompose.AppDatabase.AppDatabase
import com.example.retrofitcompose.Helper.ConnectivityObserver
import com.example.retrofitcompose.Navigation.Navigation
import com.example.retrofitcompose.Network.RetrofitInstance
import com.example.retrofitcompose.Repository.PostRepository
import com.example.retrofitcompose.Repository.RoomPostRepository
import com.example.retrofitcompose.Repository.RoomUserRepository
import com.example.retrofitcompose.ViewModel.AuthSharedViewModel
import com.example.retrofitcompose.ViewModel.AuthViewModelFactory
import com.example.retrofitcompose.ViewModel.PostViewModel
import com.example.retrofitcompose.ViewModel.RoomPostViewModel
import com.example.retrofitcompose.ViewModel.RoomPostViewModelFactory
import com.example.retrofitcompose.ViewModel.RoomUserViewModel
import com.example.retrofitcompose.ViewModel.RoomUserViewModelFactory
import com.example.retrofitcompose.ui.theme.RetrofitComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val authViewModel: AuthSharedViewModel by viewModels {
            AuthViewModelFactory(applicationContext)
        }

        val db = AppDatabase.getDatabase(applicationContext)
        val roomPostRepository = RoomPostRepository(db.postDao())
        val viewModelFactory = RoomPostViewModelFactory(roomPostRepository)
        val roomPostViewModel = ViewModelProvider(this, viewModelFactory).get(RoomPostViewModel::class.java)

        val roomUserRepository = RoomUserRepository(db.userDao())
        val userModelFactory = RoomUserViewModelFactory(roomUserRepository)
        val userViewModel = ViewModelProvider(this, userModelFactory).get(RoomUserViewModel::class.java)


        val apiService = RetrofitInstance.apiService
        val repository = PostRepository(apiService)
        val connectivityObserver = ConnectivityObserver(applicationContext)
        val postViewModel = PostViewModel(repository, roomPostViewModel, connectivityObserver)

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
                        roomPostViewModel,
                        userViewModel
                    )
                }
            }
        }
    }
}
