package com.example.retrofitcompose.Screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.retrofitcompose.ViewModel.AuthSharedViewModel
import com.example.retrofitcompose.ViewModel.RoomUserViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun UserDataPopup(
    viewModel: AuthSharedViewModel,
    onDismiss: () -> Unit,
    userViewModel: RoomUserViewModel
) {
    val userData = userViewModel.userData.collectAsState()

    LaunchedEffect (Unit){
        userViewModel.loadUsers()
    }

    AlertDialog(
        onDismissRequest = {  },
        title = { Text(text = "User List") },
        text = {
           LazyColumn {
               if (userData.value.isEmpty()){
                   item{
                       Text("No user found!")
                   }
               }else{
                   items(userData.value.size){
                       index ->
                       val user = userData.value[index]
                       Row (
                           modifier = Modifier
                               .fillMaxWidth()
                               .padding(8.dp),
                           horizontalArrangement = Arrangement.SpaceBetween,
                           verticalAlignment = Alignment.CenterVertically
                       ){
                           Text("User: ${user.username}, Password: ${user.password}")
                           IconButton(
                               onClick = {
                                   userViewModel.deletePost(user.id)

                                   userViewModel.loadUsers()
                               }
                           ) {
                               Icon(
                                   imageVector = Icons.Default.Delete,
                                   contentDescription = "Delete"
                               )
                           }
                       }
                   }
               }
           }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
