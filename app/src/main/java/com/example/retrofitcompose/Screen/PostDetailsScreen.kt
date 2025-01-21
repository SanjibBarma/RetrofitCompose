//@file:OptIn(ExperimentalMaterial3Api::class)
//
//package com.example.retrofitcompose.Screen
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.retrofitcompose.Model.PostEntity
//
//@Composable
//fun PostDetailsScreen(post: PostEntity, onBackClick: () -> Unit) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Post Details") },
//                navigationIcon = {
//                    IconButton(onClick = onBackClick) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = "Back"
//                        )
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(16.dp)
//        ) {
//            Text("User ID: ${post.userId}")
//            Spacer(modifier = Modifier.height(8.dp))
//            Text("Title: ${post.title}")
//            Spacer(modifier = Modifier.height(8.dp))
//            Text("Body: ${post.body}")
//        }
//    }
//}
