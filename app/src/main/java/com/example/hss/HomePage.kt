package com.example.hss

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_home_page.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomePage.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomePage : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



    }
    private lateinit var viewOfLayout: View
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewOfLayout = inflater!!.inflate(R.layout.fragment_home_page, container, false)

        val motionref = FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir").child("pir")
        val motionListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue<String>()
                if (post == "yes"){
                    viewOfLayout.motiondetection.text = "DETECTED"
                }
                else{
                    viewOfLayout.motiondetection.text = "UNDETECTED"
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        motionref.addValueEventListener(motionListener)

        val soundref = FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir").child("soundFlag")
        val soundListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue<Int>()
                if (post == 1){
                    viewOfLayout.sounddetection.text = "DETECTED"
                }
                else{
                    viewOfLayout.sounddetection.text = "UNDETECTED"
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        val tempref = FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir").child("dhtT")
        val tempListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue<String>()

                    viewOfLayout.temprature.text = post + " °C"


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        val humref = FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir").child("dhtH")
        val humListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue<String>()

                viewOfLayout.humidity.text = post + " %"


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        motionref.addValueEventListener(motionListener)
        soundref.addValueEventListener(soundListener)
        tempref.addValueEventListener(tempListener)
        humref.addValueEventListener(humListener)

        viewOfLayout.arm.setOnClickListener {
           FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir").child("arm").setValue(1)
        }
        viewOfLayout.disarm.setOnClickListener {
            FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir").child("arm").setValue(0)
        }


        return viewOfLayout

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomePage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}