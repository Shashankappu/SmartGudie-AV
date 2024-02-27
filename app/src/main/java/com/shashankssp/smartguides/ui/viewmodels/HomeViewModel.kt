package com.shashankssp.smartguides.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class HomeViewModel:ViewModel() {
    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")
    private var mAuth = Firebase.auth
    fun signOut(){
        mAuth.signOut()
    }

}