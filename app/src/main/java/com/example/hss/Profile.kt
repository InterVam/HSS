package com.example.hss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login_page.*

class Profile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val name = findViewById<TextView>(R.id.name)
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
            ref1.child("Locking System Password").setValue(passstr)
        }
    }
}