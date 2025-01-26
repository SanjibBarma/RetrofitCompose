package com.example.retrofitcompose.Screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retrofitcompose.Compose.CustomAppBar
import com.example.retrofitcompose.Helper.UIState
import com.example.retrofitcompose.Navigation.Screen
import com.example.retrofitcompose.ViewModel.PostViewModel
import com.example.retrofitcompose.ViewModel.RoomPostViewModel
import kotlinx.coroutines.launch

@Composable
fun PostScreen(
    viewModel: PostViewModel,
    onBackClick: () -> Unit,
    roomViewModel: RoomPostViewModel,
    navController: NavController
) {

    BackHandler {  }

    val context = LocalContext.current
    val postState = viewModel.posts.collectAsState()

    val roomPostData = roomViewModel.postData.value
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomAppBar(
                "Post List",
//                onBackClick = onBackClick
                null
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    viewModel.loadPosts();
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Get Data")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (roomPostData.isNotEmpty()){
                when(val state = postState.value){
                    is UIState.Error -> {
                        Text(
                            text = state.exception.message ?: "Unknown error",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
//                        Toast.makeText(context, "Data error: $state", Toast.LENGTH_SHORT).show()
                    }
                    is UIState.Loading -> {
//                        Toast.makeText(context, "Data loading: $state", Toast.LENGTH_SHORT).show()

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is UIState.Success -> {
//                        Toast.makeText(context, "Data success...", Toast.LENGTH_SHORT).show()

                        LazyColumn {
                            items(roomPostData){roomPost ->

                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Card(
                                        shape = RoundedCornerShape(8.dp),
                                        colors = CardDefaults.cardColors(Color.White),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {

                                        Row (
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ){
                                            Text(
                                                text = roomPost.title,
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = Color.Black,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(8.dp)
                                                    .weight(1f)
                                                    .clickable {
                                                        navController.navigate(Screen.PostDetailScreen.withArgs(roomPost.id.toString()))
                                                    },
                                            )

                                            IconButton(onClick = {
                                                coroutineScope.launch {
                                                    roomViewModel.deletePost(roomPost.id)
                                                }
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Default.Delete,
                                                    contentDescription = "Delete",
                                                    tint = Color.Black
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
            }else{
                Text(
                    text = "Room data is empty!",
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}
