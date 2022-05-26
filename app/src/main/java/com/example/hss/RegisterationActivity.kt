package com.example.hss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class RegisterationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)
        val loginintent = Intent(this, MainPage::class.java)
        var accountname = findViewById<TextInputEditText>(R.id.regaccountname)
        var password = findViewById<TextInputEditText>(R.id.regpassword)
        var systemid = findViewById<TextInputEditText>(R.id.regsystemid)
        var username = findViewById<TextInputEditText>(R.id.regusername)
        val register = findViewById<Button>(R.id.RegisterButton)
        register.setOnClickListener {
            val accountnamestring = accountname.editableText.toString()
            val passwordstring = password.editableText.toString()
            val usernamestring = username.editableText.toString()
            val systemidstring = systemid.editableText.toString()
            val ref = FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir")
            ref.child("Account Password").setValue(passwordstring)
            ref.child("Home Account Name").setValue(accountnamestring)
            ref.child("Username").setValue(usernamestring)
            ref.child("SystemID").setValue(systemidstring)
            startActivity(loginintent)
            finish()
        }
    }
}