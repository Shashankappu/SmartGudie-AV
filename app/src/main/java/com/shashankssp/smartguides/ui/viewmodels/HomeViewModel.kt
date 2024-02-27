package com.shashankssp.smartguides.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeViewModel:ViewModel() {
    private var mAuth = Firebase.auth

    fun signOut(){
        mAuth.signOut()
    }
}