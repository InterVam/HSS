package com.example.hss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView

class WelcomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loginintent = Intent(this, LoginActivity::class.java)
        val registerationintent = Intent(this,RegisterationActivity::class.java)
        val loginButton = findViewById<Button>(R.id.LoginButton)
        val registerationbutton = findViewById<TextView>(R.id.needAccount)
        loginButton.setOnClickListener{
            startActivity(loginintent)
        }
        registerationbutton.setOnClickListener{
            startActivity(registerationintent)
        }
    }

}