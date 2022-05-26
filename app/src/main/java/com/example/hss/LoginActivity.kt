package com.example.hss

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val loginintent = Intent(this, MainPage::class.java)
        var userflagexist = false
        var passwordflagexist = false
        var username = findViewById<TextInputEditText>(R.id.username)
        var usernamestring : String
        var passwordstring : String
        var password = findViewById<TextInputEditText>(R.id.password)
        val loginbutton =findViewById<Button>(R.id.LoginButton)
        loginbutton.setOnClickListener {
            usernamestring = username.editableText.toString()
            passwordstring = password.editableText.toString()
            val ref = FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir")
            ref.get().addOnCompleteListener() { task->if(task.isSuccessful){
                val snapshot = task.result
                val usernamedb = snapshot.child("Username").getValue(String::class.java)
                if(usernamestring == usernamedb){
                    userflagexist=true
                    println(userflagexist)
                }
            }
                ref.get().addOnCompleteListener() { task->if(task.isSuccessful){
                    val snapshot = task.result
                    val passworddb = snapshot.child("Account Password").getValue(String::class.java)
                    if(passwordstring == passworddb){
                        passwordflagexist=true
                        println(passwordflagexist)
                    }
                }
                 if(passwordflagexist and userflagexist){
                     startActivity(loginintent)
                     finishAffinity()
                 }else{
                     Toast.makeText(this,"Check username and password",Toast.LENGTH_SHORT).show()
                 }
            }
        }

    }
}
}