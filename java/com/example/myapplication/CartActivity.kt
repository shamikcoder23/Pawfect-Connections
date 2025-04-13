package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CartActivity : AppCompatActivity(), ShopDBAdapter.OnClickListener {

    private lateinit var modelShopViewModel: ShopViewModel

    private var username: String? = null
    private var email: String? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle = intent.extras
        username = bundle?.getString("username")
        email = bundle?.getString("email")
        val address = bundle?.getString("address")

        val rev: RecyclerView = findViewById(R.id.rev)
        val linLayManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rev.layoutManager = linLayManager
        val adp = ShopDBAdapter(this, this@CartActivity)
        rev.adapter = adp

        modelShopViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[ShopViewModel::class.java]
        modelShopViewModel.items.observe(this) { lst ->
            lst.let {
                adp.updateList(it)
            }
        }

        val t1: TextView = findViewById(R.id.textView5)
        val t2: TextView = findViewById(R.id.check)
        val q = modelShopViewModel.c
        t1.text = "Your cart contains $q items"
        val total: Int = q * 100
        t2.text = "Subtotal: Rs $total"

        val b: Button = findViewById(R.id.chat1)
        b.setOnClickListener {
            Toast.makeText(this@CartActivity, R.string.none, Toast.LENGTH_LONG).show()
        }

        val accountBtn : FloatingActionButton = findViewById(R.id.fab)
        accountBtn.setOnClickListener{
            //Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
            val bundle1 = Bundle()
            bundle1.putString("username", username)
            bundle1.putString("email", email)
            bundle1.putString("address", address)
            val myIntent = Intent(this, ShopActivity::class.java)
            myIntent.putExtras(bundle1)
            startActivity(myIntent)
        }
    }

    override fun onClick(model: ShopData, position: Int) {
        modelShopViewModel.remItem(ShopData(model.id, model.image, model.name, model.price))
        val bundle1 = Bundle()
        bundle1.putString("username", username)
        bundle1.putString("email", email)
        val myIntent = Intent(this, ShopActivity::class.java)
        myIntent.putExtras(bundle1)
        startActivity(myIntent)
    }
}