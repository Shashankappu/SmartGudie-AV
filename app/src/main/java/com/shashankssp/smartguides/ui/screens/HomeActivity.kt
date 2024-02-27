package com.shashankssp.smartguides.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.shashankssp.jetpackauth.R
import com.shashankssp.smartguides.ui.theme.JetpackAuthTheme
import com.shashankssp.smartguides.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

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
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        ModalDrawerSheet(
                            modifier = Modifier
                                .width(250.dp)
                                .padding(5.dp, 5.dp)
                        ) {
                            Column(modifier = Modifier.height(150.dp)) {
                                Image( modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp).height(50.dp) ,painter = painterResource(id = R.drawable.boy), contentDescription = "profile image" )
                                Text("Shashank Appu",fontSize = 16.sp , modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                                Text("shashank@gmail.com", fontSize = 14.sp , modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                            }
                            Divider()
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Filled.Home,contentDescription = "places") },
                                label = { Text(text = "Home") },
                                selected = false,
                                onClick = {  }
                            )
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Filled.LocationOn,contentDescription = "places") },
                                label = { Text(text = "Stories") },
                                selected = false,
                                onClick = {  }
                            )
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Filled.Call,contentDescription = "places") },
                                label = { Text(text = "Contact us") },
                                selected = false,
                                onClick = {  }
                            )
                            NavigationDrawerItem(
                                icon = { Icon(Icons.Filled.ExitToApp,contentDescription = "places") },
                                label = { Text(text = "Logout") },
                                selected = false,
                                onClick = {
                                    viewModel.signOut()
                                    moveToLogin()
                                }
                            )
                        }
                    },

                ) {
                    Scaffold(
                        modifier = Modifier.background(Color.White),
                        topBar = {
                            CenterAlignedTopAppBar(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                title = { Text(text = "Compose Dashboard") },
                                navigationIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "menu",
                                       Modifier.clickable {
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    })},
                            )
                        },
                        content = {
                            BodyView()
                        }
                    )
                }
            }
        }
    }
    @Composable
    fun BodyView() {
        return Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CardsForDashBoard(name = "Profile", icon = Icons.Filled.Person)
            Spacer(modifier = Modifier.height(100.dp))
            Button(
                onClick = {
                    Toast.makeText(this@HomeActivity,"Nothing to scan",Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Continue", color = Color.White)
            }
        }
    }

    @Composable
    fun CardsForDashBoard(name:String, icon: ImageVector){
        return Card(
            modifier = Modifier
                .height(250.dp)
                .width(250.dp)
                .padding(horizontal = 5.dp, vertical = 10.dp),
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

