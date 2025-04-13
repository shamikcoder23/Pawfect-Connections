package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainTreeActivity : AppCompatActivity() {

    private var name: String? = null
    private var email: String? = null
    private var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_tree)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        name = intent.extras?.getString("username")
        email = intent.extras?.getString("email")
        address = intent.extras?.getString("address")

        val b1: Button = findViewById(R.id.loginBtn)
        b1.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", address)
            val i = Intent(this@MainTreeActivity, LandingTreeActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
        }

        val b2: Button = findViewById(R.id.backBtn)
        b2.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", address)
            val i = Intent(this@MainTreeActivity, LandingActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
        }
    }
}