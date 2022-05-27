package com.example.hss

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_home_page.view.*


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

        var id = 0
        val humandetectedref = FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir").child("alarm")
        createNotificationChannel()
        val humanListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue<String>()
                if (post == "yes"){
                id += 1
                NotificationManagerCompat.from(applicationContext).notify(id,builder.build())}
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        humandetectedref.addValueEventListener(humanListener)
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "humandetect"
            val descriptionText = "detects human"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("d", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    var builder = NotificationCompat.Builder(this, "d")
        .setSmallIcon(R.drawable.ic_baseline_photo_camera_24)
        .setContentTitle("Beware!!")
        .setContentText("There was a person detected")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    }

