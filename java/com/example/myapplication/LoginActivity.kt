package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val email = findViewById<EditText?>(R.id.editEmail).text
        val pwd = findViewById<EditText?>(R.id.editPwd).text
        val button : Button = findViewById(R.id.loginBtn)
        button.setOnClickListener {
            if(Patterns.EMAIL_ADDRESS.matcher(email).matches() && pwd.isNotEmpty()) {
                val myIntent = Intent(this, LandingActivity::class.java)
                startActivity(myIntent)
            }
            else{
                Toast.makeText(this, "Enter valid Email Address and / or Password ", Toast.LENGTH_LONG).show()
            }
        }
    }
}