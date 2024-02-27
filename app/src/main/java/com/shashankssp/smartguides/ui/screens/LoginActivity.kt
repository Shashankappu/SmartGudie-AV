package com.shashankssp.smartguides.ui.screens

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.shashankssp.smartguides.ui.theme.JetpackAuthTheme
import com.shashankssp.smartguides.ui.viewmodels.AuthViewModel



class LoginActivity : ComponentActivity() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var mAuth: FirebaseAuth
    public override fun onStart() {
        super.onStart()

        val currentUser = mAuth.currentUser
        if(currentUser!=null){
            moveToHome()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = Firebase.auth

        setContent {
            JetpackAuthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                    ) {
                        val email = remember { mutableStateOf("") }
                        val password = remember { mutableStateOf("") }
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            text = "Login page ",
                        )
                        Spacer(modifier = Modifier.height(50.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = email.value,
                            onValueChange = {
                                email.value = it
                            },
                            label = { Text(text = "Email") }
                        )
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = password.value, onValueChange = {
                                password.value = it
                            },
                            label = { Text(text = "Password") }
                        )
                        OutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            onClick = {
                            if(!TextUtils.isEmpty(email.value)&& !TextUtils.isEmpty(password.value))
                                viewModel.login(email.value,password.value)
                        }) {
                            Text("Login")
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 20.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextButton(onClick = {
                            moveToCreateAccount()
                        }) {
                            Text(text = "Don't have an Account? Register")
                        }
                    }
                }
            }
        }
        viewModel.loginSuccess.observe(this) { success ->
            if (success) {
                moveToHome()
            } else {
                Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun moveToHome(){
        Intent(this@LoginActivity, HomeActivity::class.java).also{
            startActivity(it)
            finish()
        }
    }
    private fun moveToCreateAccount(){
        Intent(this@LoginActivity, CreateUserActivity::class.java).also{
            startActivity(it)
            finish()
        }
    }
}

