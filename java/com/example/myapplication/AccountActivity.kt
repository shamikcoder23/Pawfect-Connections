package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import java.util.Locale

class AccountActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val e1: EditText = findViewById(R.id.editTextText3)
        e1.visibility = View.GONE
        var isVis = false
        val txt: TextView = findViewById(R.id.textView6)

        val add: TextView = findViewById(R.id.locationAccessGiven)
        add.visibility = View.GONE
        val add1: SearchView = findViewById(R.id.search)
        add1.visibility = View.GONE

        var name = intent.extras?.getString("username")
        val email = intent.extras?.getString("email")
        val useraddress = intent.extras?.getString("address")

        if(name.equals(null)){
            e1.visibility = View.VISIBLE
            isVis = true
            txt.visibility = View.GONE
        }
        else {
            txt.text = "Hi, $name"
        }
        val txt2 : TextView = findViewById(R.id.textView7)
        txt2.text = "Your email: \n$email"

//        if(useraddress.equals(null)){
//            add1.visibility = View.VISIBLE
//        }
//        else{
//            add.visibility = View.VISIBLE
//            add.text = "Your address: \n$useraddress"
//        }

//        val b1: ImageButton = findViewById(R.id.addressBtn)
//        b1.setOnClickListener {
//            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//            if (ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
//                ActivityCompat.requestPermissions(this, permissions, 201)
//                return@setOnClickListener
//            }
//            fusedLocationProviderClient.lastLocation.addOnSuccessListener { loc: Location? ->
//                if (loc != null) {
//                    val geo = Geocoder(this, Locale.getDefault())
//                    val address = geo.getFromLocation(loc.latitude, loc.longitude, 1) { lst ->
//                        if(lst.isEmpty())
//                            Toast.makeText(this, R.string.loc_fail, Toast.LENGTH_LONG).show()
//                        else{
//                            useraddress = lst[0].getAddressLine(0)
//                        }
//                    }
//                }
//                else
//                    Toast.makeText(this, R.string.loc_fail, Toast.LENGTH_LONG).show()
//            }.addOnFailureListener{ e: Exception ->
//                e.printStackTrace()
//                Toast.makeText(this, R.string.loc_fail, Toast.LENGTH_LONG).show()
//            }
//        }

        val btn : Button = findViewById(R.id.button)
        btn.setOnClickListener {
            if(isVis)
                name = e1.text.toString().trim()
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", useraddress)
            val i = Intent(this@AccountActivity, LandingActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            201 -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    return
                else
                    Toast.makeText(this, R.string.loc_fail, Toast.LENGTH_LONG).show()
            }
        }
    }
}