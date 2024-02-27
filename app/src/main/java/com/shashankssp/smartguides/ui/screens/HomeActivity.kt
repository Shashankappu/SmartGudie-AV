package com.shashankssp.smartguides.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.shashankssp.smartguides.ui.theme.JetpackAuthTheme
import com.shashankssp.smartguides.ui.viewmodels.HomeViewModel

class HomeActivity : ComponentActivity() {
    private lateinit var mAuth :FirebaseAuth
    private val viewModel : HomeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = Firebase.auth
        setContent {
            JetpackAuthTheme {
                Scaffold(
                    modifier = Modifier.background(Color.White),
                    topBar = {
                        CenterAlignedTopAppBar(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            title = { Text(text = "Compose Dashboard") },
                            navigationIcon = { Icon(Icons.Filled.Menu,contentDescription = "menu") }
                        )
                    },
                    content = {
                        BodyView()
                    },
                )
            }
        }
    }
    @Composable
    fun BodyView() {
        return Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 100.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                CardsForDashBoard(name = "Profile", icon = Icons.Filled.Person)
                CardsForDashBoard(name = "Favourites", icon = Icons.Filled.Favorite)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                CardsForDashBoard(name = "New Updates", icon = Icons.Filled.Notifications)
                CardsForDashBoard(name = "Books", icon = Icons.Filled.Place)
            }
            Spacer(modifier = Modifier.height(100.dp))
            Button(
                onClick = {
                    viewModel.signOut();
                    moveToLogin()
                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Logout", color = Color.White)
            }
        }
    }

    @Composable
    fun CardsForDashBoard(name:String, icon: ImageVector){
        return Card(
            modifier = Modifier
                .height(150.dp)
                .width(150.dp)
                .padding(horizontal = 5.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Column {
                    Icon(icon, contentDescription = "nothing",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(text = name)
                }
            }
        }
    }

    private fun moveToLogin(){
        Intent(this@HomeActivity, LoginActivity::class.java).also{
            startActivity(it)
            finish()
        }
    }
}

