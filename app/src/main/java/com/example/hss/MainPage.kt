package com.example.hss

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView


class MainPage : AppCompatActivity() {
    private lateinit var  currentfragment :Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        supportFragmentManager.beginTransaction().replace(R.id.nav_container,HomePage()).commit()
        val bottomnav : NavigationBarView = findViewById(R.id.bottom_navbar)
        bottomnav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homePage->{
                    currentfragment = HomePage()
                }
                R.id.camera->{
                    currentfragment = Camera()
                }
                R.id.logout->{
                    startActivity(Intent(this, WelcomePage::class.java))
                    finishAffinity()
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.nav_container,currentfragment).commit()
            true
        }
    }


}