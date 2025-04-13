package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.transition.Explode
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PetDetailsActivity : AppCompatActivity() {

    private lateinit var account: Auth0

    private var user = User()

    private var username: String? = null
    private var email: String? = null
    private var address: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pet_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        account = Auth0( getString(R.string.auth0_clientId), getString(R.string.auth0_domain) )

        val bundle = intent.extras
        val img = bundle?.getByteArray("image")
        val name = bundle?.getString("name")
        val desc = bundle?.getString("breed")
        username = bundle?.getString("username")
        email = bundle?.getString("email")
        address = bundle?.getString("address")
        val tree = bundle?.getBoolean("tree")

        val image: ImageView = findViewById(R.id.list_item)
        if (img != null) {
//            val bm: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground)
//            image.setImageBitmap(Bitmap.createScaledBitmap(bm, 100, 100, false))
            val resized = resize(BitmapFactory.decodeByteArray(img, 0, img.size))
            image.setImageBitmap(resized)
            //image.setImageResource(img)
        }
        var txt: TextView = findViewById(R.id.list_item_name)
        txt.text = name
        txt = findViewById(R.id.list_item_desc)
        txt.text = desc

        val area: MaterialCardView = findViewById(R.id.parent_data)
        area.visibility = View.GONE

        val homeBtn : FloatingActionButton = findViewById(R.id.fab)
        val chatBtn : Button = findViewById(R.id.chat)
        val addBtn : Button = findViewById(R.id.add)

        val layout: ConstraintLayout = findViewById(R.id.main)
        if(tree == true){
            layout.background = ContextCompat.getDrawable(this, R.drawable.poster_bg_tree)
            homeBtn.setOnClickListener{
                //Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
                val bundle1 = Bundle()
                bundle1.putString("username", username)
                bundle1.putString("email", email)
                bundle1.putString("address", address)
                val myIntent = Intent(this@PetDetailsActivity, LandingTreeActivity::class.java)
                myIntent.putExtras(bundle1)
                startActivity(myIntent)
            }
            chatBtn.visibility = View.GONE
            addBtn.text = ContextCompat.getString(this, R.string.add_basket1)
        }
        else {
            homeBtn.setOnClickListener {
                //Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
                val bundle1 = Bundle()
                bundle1.putString("username", username)
                bundle1.putString("email", email)
                bundle1.putString("address", address)
                val myIntent = Intent(this@PetDetailsActivity, LandingActivity::class.java)
                myIntent.putExtras(bundle1)
                startActivity(myIntent)
            }
        }

        chatBtn.setOnClickListener{
            //Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
//            if(area.visibility == View.GONE) {
//                area.visibility = View.VISIBLE
//                val area1: TextView = findViewById(R.id.parent_data_1)
//                area1.text = "Parent contact detail: \n$email"
//            }
//            else {
//                area.visibility = View.GONE
//            }
            val bundle1 = Bundle()
            bundle1.putByteArray("image", img)
            bundle1.putString("name", name)
            bundle1.putString("breed", desc)
            bundle1.putString("username", username)
            bundle1.putString("email", email)
            bundle1.putString("address", address)
            val i = Intent(this@PetDetailsActivity, CallActivity::class.java)
            i.putExtras(bundle1)
            startActivity(i)
            //startActivity(Intent(this, AccountActivity::class.java))
        }
        addBtn.setOnClickListener{
            val bundle1 = Bundle()
            bundle1.putByteArray("image", img)
            bundle1.putString("name", name)
            bundle1.putString("breed", desc)
            bundle1.putString("username", username)
            bundle1.putString("email", email)
            bundle1.putString("address", address)
            bundle1.putBoolean("tree", tree!!)
            val i = Intent(this@PetDetailsActivity, InvoiceActivity::class.java)
            i.putExtras(bundle1)
            startActivity(i)
            //Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
            //startActivity(Intent(this, AccountActivity::class.java))
        }

//        val homeBtn : ImageButton = findViewById(R.id.homeBtn)
//        homeBtn.setOnClickListener {
//            startActivity(Intent(this, LandingActivity::class.java))
//        }
//        val accountBtn : ImageButton = findViewById(R.id.accountBtn)
//        accountBtn.setOnClickListener{
//            Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
//            //startActivity(Intent(this, AccountActivity::class.java))
//        }
//        val basketBtn : ImageButton = findViewById(R.id.basketBtn)
//        basketBtn.setOnClickListener{
//            startActivity(Intent(this, BasketActivity::class.java))
//        }
//        val logoutBtn : ImageButton = findViewById(R.id.logoutBtn)
//        logoutBtn.setOnClickListener{
//            logout()
//        }

    }
    private fun logout () {
        WebAuthProvider.logout(account).withScheme(getString(R.string.auth0_scheme)).start(
            this, object : Callback<Void?, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Toast.makeText(this@PetDetailsActivity, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: Void?) {
                    user = User()
                    startActivity(Intent(this@PetDetailsActivity, MainActivity::class.java))
                }
            }
        )
    }
    private fun resize(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, 800, 800, false)
    }
}