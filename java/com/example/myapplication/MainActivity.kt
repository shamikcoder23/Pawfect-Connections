package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials

class MainActivity : AppCompatActivity() {

    private lateinit var account: Auth0
    private var userIsAuthenticated = false

    private var user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val vv : VideoView = findViewById(R.id.bg)
        val uri : Uri = Uri.parse("android.resource://"+packageName+"/"+R.raw.doggo_joy_jump)
        vv.setVideoURI(uri)
        vv.start()

        account = Auth0( getString(R.string.auth0_clientId), getString(R.string.auth0_domain) )

        val button : Button = findViewById(R.id.loginBtn)
        button.setOnClickListener {
            login()
        }
    }

    private fun login () {
        WebAuthProvider.login(account).withScheme(getString(R.string.auth0_scheme)).start(
            this, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Toast.makeText(this@MainActivity, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: Credentials) {
                    val idToken = result.idToken
                    user = User(idToken)
                    val name = user.getName()
                    val email = user.getEmail()
                    val address = null
                    userIsAuthenticated = true
                    val bundle = Bundle()
                    bundle.putString("username", name)
                    bundle.putString("email", email)
                    bundle.putString("address", address)
                    val myIntent = Intent(this@MainActivity, LandingActivity::class.java)
                    myIntent.putExtras(bundle)
                    startActivity(myIntent)
                }
            }
        )
    }
    fun logout () {
        WebAuthProvider.logout(account).withScheme(getString(R.string.auth0_scheme)).start(
            this, object : Callback<Void?, AuthenticationException>{
                override fun onFailure(error: AuthenticationException) {
                    Toast.makeText(this@MainActivity, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: Void?) {
                    user = User()
                }
            }
        )
    }
}