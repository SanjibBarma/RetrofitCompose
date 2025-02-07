package com.example.retrofitcompose.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.retrofitcompose.Navigation.Screen
import com.example.retrofitcompose.ViewModel.AuthSharedViewModel
import com.example.retrofitcompose.ViewModel.RoomUserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: AuthSharedViewModel,
    navController: NavController,
    userViewModel: RoomUserViewModel
) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPopup by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var isProgressLoading by remember { mutableStateOf(false) }
    var coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier
        .fillMaxSize()
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Username Icon"
                    )
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon"
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = "Toggle Password Visibility"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button (
                onClick = {
                    isProgressLoading = true

                    if (username.isEmpty() || password.isEmpty()){
                        Toast.makeText(context, "Input valid username and password", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    kotlinx.coroutines.GlobalScope.launch(Dispatchers.Main) {
                        val existingUser = userViewModel.getUserByUsername(username)
                        if (existingUser != null){
                            isProgressLoading = false
                            if (existingUser.password == password){
                                navController.navigate(Screen.PostScreen.route)
                            }else{
                                Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            isProgressLoading = false
                            Toast.makeText(context, "Username is not found!", Toast.LENGTH_SHORT).show()
                        }

                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    isProgressLoading = true

                    if (username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
                        isProgressLoading = false
                    }else{

                        kotlinx.coroutines.GlobalScope.async (Dispatchers.IO){
                            userViewModel.upsertData(username, password);
                        }

                        coroutineScope.async {
                            val existingUser = userViewModel.getUserByUsername(username)
                            if (existingUser?.username.equals(username)){
                                Toast.makeText(context, "User is updated", Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(context, "New user saved", Toast.LENGTH_SHORT).show()
                            }

                            delay(500)
                            username = ""
                            password = ""
                            isProgressLoading = false
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Up")
            }

            if (isProgressLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator()
            }
        }

        Button(
            onClick = { showPopup = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(100.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),

            ) {
            Text("User", color = MaterialTheme.colorScheme.onPrimary)
        }

        if (showPopup) {
            UserDataPopup(
                viewModel,
                onDismiss = { showPopup = false },
                userViewModel
            )
        }
    }
}
