package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InvoiceActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_invoice)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bundle = intent.extras
        val img = bundle?.getByteArray("image")
        val name = bundle?.getString("name")
        val desc = bundle?.getString("breed")
        val username = bundle?.getString("username")
        val email = bundle?.getString("email")
        val address = bundle?.getString("address")
        val tree = bundle?.getBoolean("tree")

        val t1: TextView = findViewById(R.id.textView6)
        t1.text = "Name: $username"
        val t2: TextView = findViewById(R.id.textView7)
        t2.text = "Email: $email"
//        val t3: TextView = findViewById(R.id.textView8)
//        t3.text = "Address: $address"
        val image: ImageView = findViewById(R.id.list_item_img)
        if (img != null) {
            val resized = resize(BitmapFactory.decodeByteArray(img, 0, img.size))
            image.setImageBitmap(resized)
        }
        val t4: TextView = findViewById(R.id.list_item_name)
        t4.text = name
        val t5: TextView = findViewById(R.id.list_item_breed)
        t5.text = desc

        val b: Button = findViewById(R.id.chat1)
        b.setOnClickListener {
            Toast.makeText(this@InvoiceActivity, R.string.none, Toast.LENGTH_LONG).show()
        }

        val homeBtn : FloatingActionButton = findViewById(R.id.fab)
        val layout: ConstraintLayout = findViewById(R.id.main)
        val card: CardView = findViewById(R.id.item_card)
        if(tree == true) {
            layout.background = ContextCompat.getDrawable(this, R.drawable.login_button_2)
            card.setBackgroundColor(ContextCompat.getColor(this, R.color.bg))
            homeBtn.setOnClickListener{
                val bundle1 = Bundle()
                bundle1.putString("username", username)
                bundle1.putString("email", email)
                bundle1.putString("address", address)
                val myIntent = Intent(this@InvoiceActivity, LandingTreeActivity::class.java)
                myIntent.putExtras(bundle1)
                startActivity(myIntent)
            }
        }
        else{
            homeBtn.setOnClickListener{
                //Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
                val bundle1 = Bundle()
                bundle1.putString("username", username)
                bundle1.putString("email", email)
                bundle1.putString("address", address)
                val myIntent = Intent(this@InvoiceActivity, LandingActivity::class.java)
                myIntent.putExtras(bundle1)
                startActivity(myIntent)
            }
        }

    }
    private fun resize(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, 300, 300, false)
    }
}