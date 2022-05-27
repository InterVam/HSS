package com.example.hss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login_page.*

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val name = findViewById<TextView>(R.id.name)
        val picbutt = findViewById<Button>(R.id.pic)
        val passin = findViewById<TextInputEditText>(R.id.lockpass)
        val passbutt = findViewById<Button>(R.id.changepassword)
        val ref = FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir")
        ref.get().addOnCompleteListener() { task->if(task.isSuccessful){
            val snapshot = task.result
            val namestring = snapshot.child("Home Account Name").getValue(String::class.java)
            name.text = namestring
            }
        }
        passbutt.setOnClickListener {
            val ref1 = FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir")
           val passstr = passin.editableText.toString()
            if (passstr.length != 6) {
                Toast.makeText(this,"Enter a 6 digit password",Toast.LENGTH_SHORT).show()
            }
            else ref1.child("Locking System Password").setValue(passstr)
        }
        picbutt.setOnClickListener {
            startActivity(Intent(this, Pictures::class.java))
        }
    }
}