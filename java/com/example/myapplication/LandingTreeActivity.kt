package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LandingTreeActivity : AppCompatActivity(), TreeDBAdapter.OnClickListener {
    private var name: String? = null
    private var email: String? = null
    private var address: String? = null

    lateinit var model: TreeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_landing_tree)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        name = intent.extras?.getString("username")
        email = intent.extras?.getString("email")
        address = intent.extras?.getString("address")

        val rev: RecyclerView = findViewById(R.id.rev)
        val linLayManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rev.layoutManager = linLayManager

        val newAdapter = TreeDBAdapter(this, this@LandingTreeActivity)
        rev.adapter = newAdapter

        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[TreeViewModel::class.java]
        model.plants.observe(this) { lst ->
            lst.let { newAdapter.updateList(it) }
        }

        val b1: Button = findViewById(R.id.loginBtn)
        b1.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", address)
            val i = Intent(this@LandingTreeActivity, TreeAdd::class.java)
            i.putExtras(bundle)
            startActivity(i)
        }

        val b2: Button = findViewById(R.id.backBtn)
        b2.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", address)
            val i = Intent(this@LandingTreeActivity, LandingActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
        }
    }
    override fun onClick(model: TreeData, position: Int) {
        val bundle = Bundle()
        bundle.putByteArray("image", model.image)
        bundle.putString("name", model.name)
        bundle.putString("username", name)
        bundle.putString("email", email)
        bundle.putString("address", address)
        bundle.putBoolean("tree", true)
        val i = Intent(this@LandingTreeActivity, PetDetailsActivity::class.java)
        i.putExtras(bundle)
        startActivity(i)
    }
}