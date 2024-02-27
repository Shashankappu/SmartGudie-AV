package com.shashankssp.smartguides.ui.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.database
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel() {
    private var mAuth = Firebase.auth
    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> get() = _loginSuccess
    private val _createAccountSuccess = MutableLiveData<Boolean>()
    val createAccountSuccess: LiveData<Boolean> get() = _createAccountSuccess

    fun login(email: String, password   : String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _loginSuccess.value = task.isSuccessful
            }
    }

    fun createUser(email:String,password:String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val currentUser = mAuth.currentUser
                    currentUser?.let { user ->
                        val database = com.google.firebase.Firebase.database
                        val usersRef = database.getReference("Users")
                        val userNode = usersRef.child(user.uid)
                        userNode.child("email").setValue(email)
                    }
                    _createAccountSuccess.value = task.isSuccessful
                }else{
                    Log.d("CreateAccount","account creation failed")
                }
            }
    }

}