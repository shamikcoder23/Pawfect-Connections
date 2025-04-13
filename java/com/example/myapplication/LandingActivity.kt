package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LandingActivity : AppCompatActivity(), PetDbAdapter.OnClickListener {

    private lateinit var account: Auth0

    private var user = User()

    lateinit var model: PetViewModel

    private var name: String? = null
    private var email: String? = null
    private var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_landing)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        account = Auth0( getString(R.string.auth0_clientId), getString(R.string.auth0_domain) )
        name = intent.extras?.getString("username")
        email = intent.extras?.getString("email")
        address = intent.extras?.getString("address")

        val rev: RecyclerView = findViewById(R.id.rev)
        val linLayManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rev.layoutManager = linLayManager

        val newAdapter = PetDbAdapter(this, this@LandingActivity)
        rev.adapter = newAdapter
        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[PetViewModel::class.java]
        model.allData.observe(this) { lst ->
            lst.let { newAdapter.updateList(it) }
        }

        val opt: LinearLayout = findViewById(R.id.optionLayout)
        opt.visibility = View.GONE

        val categoryBtn: ImageButton = findViewById(R.id.categoryBtn)
        val shopBtn: ImageButton = findViewById(R.id.shopBtn)
        val plantBtn: ImageButton = findViewById(R.id.plantBtn)

        categoryBtn.setOnClickListener {
            if(opt.visibility == View.GONE)
                opt.visibility = View.VISIBLE
            else
                opt.visibility = View.GONE
        }

        shopBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", address)
            val i = Intent(this@LandingActivity, ShopActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
        }

        plantBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", address)
            val i = Intent(this@LandingActivity, MainTreeActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
        }

        val dogBtn : ImageButton = findViewById(R.id.dogBtn)
        val catBtn : ImageButton = findViewById(R.id.catBtn)
        val birdBtn : ImageButton = findViewById(R.id.birdBtn)
        val fishBtn : ImageButton = findViewById(R.id.fishBtn)

        dogBtn.setOnClickListener{
            model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[PetViewModel::class.java]
            model.allDog.observe(this) { lst ->
                lst.let { newAdapter.updateList(it) }
            }
        }
        catBtn.setOnClickListener{
            model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[PetViewModel::class.java]
            model.allCat.observe(this) { lst ->
                lst.let { newAdapter.updateList(it) }
            }
        }
        birdBtn.setOnClickListener{
            model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[PetViewModel::class.java]
            model.allBird.observe(this) { lst ->
                lst.let { newAdapter.updateList(it) }
            }
        }
        fishBtn.setOnClickListener {
            model = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[PetViewModel::class.java]
            model.allFish.observe(this) { lst ->
                lst.let { newAdapter.updateList(it) }
            }
        }

//        catBtn.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString("username", name)
//            bundle.putString("email", email)
//            bundle.putString("address", address)
//            val i = Intent(this@LandingActivity, CatScrollActivity::class.java)
//            i.putExtras(bundle)
//            startActivity(i)
//            //startActivity(Intent(this, CatScrollActivity::class.java))
//        }
//
//        birdBtn.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString("username", name)
//            bundle.putString("email", email)
//            bundle.putString("address", address)
//            val i = Intent(this@LandingActivity, BirdScrollActivity::class.java)
//            i.putExtras(bundle)
//            startActivity(i)
//            //startActivity(Intent(this, BirdScrollActivity::class.java))
//        }
//
//        fishBtn.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString("username", name)
//            bundle.putString("email", email)
//            bundle.putString("address", address)
//            val i = Intent(this@LandingActivity, FishScrollActivity::class.java)
//            i.putExtras(bundle)
//            startActivity(i)
//            //startActivity(Intent(this, FishScrollActivity::class.java))
//        }
//        val plantBtn : ImageButton = findViewById(R.id.plantBtn)
//        plantBtn.setOnClickListener {
//            Toast.makeText(this, "Nothing to show at present", Toast.LENGTH_LONG).show()
//        }
        val addBtn : ImageButton = findViewById(R.id.addBtn1)
        addBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", address)
            val i = Intent(this@LandingActivity, PetAdd::class.java)
            i.putExtras(bundle)
            startActivity(i)
            //startActivity(Intent(this, PetAdd::class.java))
        }

//        val homeBtn : ImageButton = findViewById(R.id.homeBtn)
//        homeBtn.setOnClickListener {
//            startActivity(Intent(this, LandingActivity::class.java))
//        }
        val accountBtn : FloatingActionButton = findViewById(R.id.loginFab)
        accountBtn.setOnClickListener{
            //Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", address)
            val myIntent = Intent(this, AccountActivity::class.java)
            myIntent.putExtras(bundle)
            startActivity(myIntent)
        }

        val aboutBtn : FloatingActionButton = findViewById(R.id.aboutFab)
        aboutBtn.setOnClickListener{
            //Toast.makeText(this, R.string.none, Toast.LENGTH_LONG).show()
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", address)
            val myIntent = Intent(this, AboutActivity::class.java)
            myIntent.putExtras(bundle)
            startActivity(myIntent)
        }
//        val basketBtn : ImageButton = findViewById(R.id.basketBtn)
//        basketBtn.setOnClickListener{
//            startActivity(Intent(this, BasketActivity::class.java))
//        }
        val logoutBtn : FloatingActionButton = findViewById(R.id.fab)
        logoutBtn.setOnClickListener{
            logout()
        }
    }
    private fun logout () {
        WebAuthProvider.logout(account).withScheme(getString(R.string.auth0_scheme)).start(
            this, object : Callback<Void?, AuthenticationException> {
                override fun onFailure(error: AuthenticationException) {
                    Toast.makeText(this@LandingActivity, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: Void?) {
                    user = User()
                    startActivity(Intent(this@LandingActivity, MainActivity::class.java))
                }
            }
        )
    }

    override fun onClick(model: PetData, position: Int) {
        val bundle = Bundle()
        bundle.putByteArray("image", model.image)
        bundle.putString("name", model.name)
        bundle.putString("breed", model.category)
        bundle.putString("username", name)
        bundle.putString("email", email)
        bundle.putString("address", address)
        bundle.putBoolean("tree", false)
        val i = Intent(this@LandingActivity, PetDetailsActivity::class.java)
        i.putExtras(bundle)
        startActivity(i)
    }
}