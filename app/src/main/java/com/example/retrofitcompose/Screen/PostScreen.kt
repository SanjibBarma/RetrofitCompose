import android.provider.CalendarContract.Colors
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retrofitcompose.Navigation.Screen
import com.example.retrofitcompose.ViewModel.PostViewModel
import com.example.retrofitcompose.ViewModel.RoomPostViewModel
import com.example.retrofitcompose.ui.theme.PurpleGrey80
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    viewModel: PostViewModel,
    onBackClick: () -> Unit,
    roomViewModel: RoomPostViewModel,
    navController: NavController
) {

    BackHandler {  }

    val context = LocalContext.current
    val posts = viewModel.posts.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    val roomPostData = roomViewModel.postData.value
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post List") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
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

            when {
                isLoading.value -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                errorMessage.value != null -> {
                    Text(
                        text = errorMessage.value ?: "",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                posts.value.isNotEmpty() -> {

                }
            }

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

                                            //Toast.makeText(context, "${roomPost.title} is clicked", Toast.LENGTH_SHORT).show()
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
}
