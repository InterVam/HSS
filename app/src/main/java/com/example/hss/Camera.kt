package com.example.hss

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Switch
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_camera.view.*
import java.lang.Thread.sleep

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Camera.newInstance] factory method to
 * create an instance of this fragment.
 */
class Camera : Fragment() {
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
    public lateinit var vidurl : String
    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewOfLayout = inflater.inflate(R.layout.fragment_camera, container, false)
        val myWebView = viewOfLayout.findViewById<WebView>(R.id.webview)
        val RightButt: Button = viewOfLayout.findViewById(R.id.RIGHT)
        val LeftButt: Button = viewOfLayout.findViewById(R.id.LEFT)
        val UPButt: Button = viewOfLayout.findViewById(R.id.UP)
        val DownButt: Button =viewOfLayout.findViewById(R.id.DOWN)
        val ResetButt: Button =viewOfLayout.findViewById(R.id.reset)
        val viewswitch =viewOfLayout.findViewById<SwitchMaterial>(R.id.viewswitch)
        myWebView.webViewClient = object : WebViewClient(){
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String?
            ): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }
        val vidref = FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir").child("url")

        val urlListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val post = dataSnapshot.getValue<String>().toString()
                vidurl = post
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        vidref.get().addOnCompleteListener() { task->if(task.isSuccessful){
            val snapshot = task.result
            vidurl = snapshot.getValue(String::class.java).toString()
            myWebView.loadUrl(vidurl)
            myWebView.setInitialScale(1)
            myWebView.settings.javaScriptEnabled = true
            myWebView.settings.allowContentAccess = true
            myWebView.settings.domStorageEnabled = true
            myWebView.settings.loadWithOverviewMode = true
            myWebView.settings.useWideViewPort = true
            FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir").child("flag").setValue(0)
        }
        }
        vidref.addValueEventListener(urlListener)
        viewswitch.setOnCheckedChangeListener { compoundButton, b ->

            if (!b) { FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir").child("flag").setValue(1)
            myWebView.loadUrl("https://asinine-chipmunk-2344.dataplicity.io/?action=stream")
            myWebView.setInitialScale(1)
            myWebView.settings.javaScriptEnabled = true
            myWebView.settings.allowContentAccess = true
            myWebView.settings.domStorageEnabled = true
            myWebView.settings.loadWithOverviewMode = true
            myWebView.settings.useWideViewPort = true
        }
        else if(b){
            FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir").child("flag").setValue(0)
            myWebView.loadUrl(vidurl)
            myWebView.setInitialScale(1)
            myWebView.settings.javaScriptEnabled = true
            myWebView.settings.allowContentAccess = true
            myWebView.settings.domStorageEnabled = true
            myWebView.settings.loadWithOverviewMode = true
            myWebView.settings.useWideViewPort = true
        } }

        ResetButt.setOnClickListener { resetback() }

        UPButt.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                GoUp()
            } else if (event.action == MotionEvent.ACTION_UP) {
                Reset()
            }
            true
        })


        RightButt.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                GoRight()
            } else if (event.action == MotionEvent.ACTION_UP) {
                Reset()
            }
            true
        })
        DownButt.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                GoDown()
            } else if (event.action == MotionEvent.ACTION_UP) {
                Reset()
            }
            true
        })
        LeftButt.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                GoLeft()
            } else if (event.action == MotionEvent.ACTION_UP) {
                Reset()
            }
            true
        })
        return viewOfLayout
    }
    var ref = FirebaseDatabase.getInstance().getReference("Control").child("-N03tDv1MvVoC7OG1mir")
    private fun Reset(){
        ref.child("Hstring").setValue("Null")
        ref.child("Vstring").setValue("Null")

    }
    private fun GoRight(){

        ref.child("Hstring").setValue("right")
    }
    private fun GoLeft(){

        ref.child("Hstring").setValue("left")
    }
    private fun GoUp(){

        ref.child("Vstring").setValue("up")
    }
    private fun GoDown(){

        ref.child("Vstring").setValue("down")
    }
    private fun resetback(){
        ref.child("Initial").setValue("yes")
        SystemClock.sleep(1000)
        ref.child("Initial").setValue("no")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Camera.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Camera().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}