package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AboutActivity : AppCompatActivity() {

    private var username: String? = null
    private var email: String? = null
    private var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle = intent.extras
        username = bundle?.getString("username")
        email = bundle?.getString("email")
        address = bundle?.getString("address")

        val homeBtn : FloatingActionButton = findViewById(R.id.fab)
        homeBtn.setOnClickListener{
            //Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
            val bundle1 = Bundle()
            bundle1.putString("username", username)
            bundle1.putString("email", email)
            bundle1.putString("address", address)
            val myIntent = Intent(this@AboutActivity, LandingActivity::class.java)
            myIntent.putExtras(bundle1)
            startActivity(myIntent)
        }
    }
}