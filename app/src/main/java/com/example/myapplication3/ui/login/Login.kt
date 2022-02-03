package com.example.myapplication3.ui.login

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.launch

@Composable
fun Login(
    navController : NavController
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val dataStore = StoreUserName(context)

    val name = dataStore.getUserName.collectAsState(initial = "")
    val pwd = dataStore.getPassword.collectAsState(initial = "")
    val isLogged = dataStore.getIsLoggedIn.collectAsState(initial = false)

    if(isLogged.value == true)
    {
        navController.navigate("home")
    }
    else {

        Surface(modifier = Modifier.fillMaxSize()) {
            val username = rememberSaveable { mutableStateOf("") }
            val password = rememberSaveable { mutableStateOf("") }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .systemBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = username.value,
                    onValueChange = { data -> username.value = data },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = password.value,
                    onValueChange = { data -> password.value = data },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        Log.i("uName", name.value)
                        Log.i("pwd", pwd.value)
                        Log.i("username", username.value)
                        Log.i("password", password.value)
                        //scope.launch { dataStore.saveUserName(username.value) }
                        if (name.value.equals(username.value) && pwd.value.equals(password.value)) {
                            scope.launch { dataStore.saveIsLoggedIn(true) }
                            navController.navigate("home")
                        } else {
                            /*Log.i("uName", name.value)
                        Log.i("pwd", pwd.value)
                        Log.i("username", username.value)
                        Log.i("password", password.value)*/
                            navController.navigate("login")
                        }

                    },
                    enabled = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = "Login")
                }

                //Text(text = pwd.value)

            }

        }
    }

}

