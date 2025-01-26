@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.retrofitcompose.Screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.retrofitcompose.Compose.CustomAppBar
import com.example.retrofitcompose.Model.PostEntity
import com.example.retrofitcompose.ViewModel.RoomPostViewModel

@Composable
fun PostDetailScreen(
//    roomPost: PostEntity,
    onBackClick: () -> Unit,
    key: String?,
    roomViewModel: RoomPostViewModel
) {

    var roomPost by remember { mutableStateOf<PostEntity?>(null) }
    LaunchedEffect (key){
        if (key != null) {
            roomPost = roomViewModel.getPostById(key.toInt())
        }
    }

    Scaffold(
        topBar = {
//            TopAppBar(
//                title = { Text("Post") },
//                navigationIcon = {
//                    IconButton(onClick = onBackClick) {
//                        Icon(
//                            imageVector = Icons.Default.ArrowBack,
//                            contentDescription = "Back"
//                        )
//                    }
//                }
//            )
            CustomAppBar(
                "Post",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding()
        ) {
            Card (
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(Color.White),
            ){
                Column (
                    modifier = Modifier
                        .padding(16.dp)
                ){
                    Text(
                        text = roomPost?.title ?: "No Title Found",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = roomPost?.value ?: "No Body Found",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black,
                    )
                }
            }
        }
    }
}
