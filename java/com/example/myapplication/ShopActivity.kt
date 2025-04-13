package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.ByteArrayOutputStream

class ShopActivity : AppCompatActivity(), AccAdapter.OnClickListener {

    private lateinit var accItem: ArrayList<Acc>

    private var name: String? = null
    private var email: String? = null
    private var address: String? = null

    private lateinit var modelShopViewModel: ShopViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shop)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        name = intent.extras?.getString("username")
        email = intent.extras?.getString("email")
        address = intent.extras?.getString("address")
        val rev: RecyclerView = findViewById(R.id.rev)
        accItem = ArrayList()
        accItem.add(Acc(R.drawable.acc_1, "Bed", "Rs. 100"))
        accItem.add(Acc(R.drawable.acc_2, "Leash", "Rs. 100"))
        accItem.add(Acc(R.drawable.acc_3, "Collar", "Rs. 100"))
        accItem.add(Acc(R.drawable.acc_4, "Treats", "Rs. 100"))
        accItem.add(Acc(R.drawable.acc_5, "Rope Toy", "Rs. 100"))
        accItem.add(Acc(R.drawable.acc_6, "Kibble(Dry)", "Rs. 100"))
        val adapter = AccAdapter(accItem, this@ShopActivity)
        val linLayManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rev.layoutManager = linLayManager
        rev.adapter = adapter

        modelShopViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[ShopViewModel::class.java]

        val cartButton: FloatingActionButton = findViewById(R.id.cartFab)
        cartButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", address)
            val i = Intent(this@ShopActivity, CartActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
        }

        val homeBtn : FloatingActionButton = findViewById(R.id.fab)
        homeBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", address)
            val i = Intent(this@ShopActivity, LandingActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
        }
    }

    override fun onClick(position: Int, model: Acc) {
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, model.getImg())
        val buffer = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, buffer)
        val image: ByteArray = buffer.toByteArray()
        modelShopViewModel.addItem(ShopData(0, image, model.getName(), model.getPrice()))
    }
}