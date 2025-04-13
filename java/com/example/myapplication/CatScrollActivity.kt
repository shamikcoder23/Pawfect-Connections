package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class CatScrollActivity : AppCompatActivity(), PetDbAdapter.OnClickListener {
    private lateinit var catItem : ArrayList<Pet>

    private lateinit var account: Auth0

    private var user = User()

    private lateinit var newItem: List<PetData>
    private lateinit var newAdapter: PetAdapter

    private val contx: Context = this@CatScrollActivity

    lateinit var model: PetViewModel

    private var name: String? = null
    private var email: String? = null
    private var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pet_scroll)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        account = Auth0( getString(R.string.auth0_clientId), getString(R.string.auth0_domain) )
        name = intent.extras?.getString("username")
        email = intent.extras?.getString("email")
        address = intent.extras?.getString("address")

        val rev : RecyclerView = findViewById(R.id.rev)
        catItem = ArrayList()
        catItem.add(Pet(R.drawable.cat_1, "Kate", "Cat"))
        catItem.add(Pet(R.drawable.cat_2, "Tim", "Cat"))
        catItem.add(Pet(R.drawable.cat_3, "Roth", "American Shorthair"))
        catItem.add(Pet(R.drawable.cat_4, "Paine", "Persian Kitten"))
        catItem.add(Pet(R.drawable.cat_5, "Jamie", "Cat"))
        catItem.add(Pet(R.drawable.cat_6, "Doll", "Cat"))
        catItem.add(Pet(R.drawable.cat_7, "Cat-herine", "Rag doll"))
        catItem.add(Pet(R.drawable.cat_8, "Heroine", "Rag doll"))
        catItem.add(Pet(R.drawable.cat_9, "Melissa", "Rag doll"))
        catItem.add(Pet(R.drawable.cat_10, "Tom", "Rag doll"))
        catItem.add(Pet(R.drawable.cat_11, "Whiskers", "Persian Kitten"))
        catItem.add(Pet(R.drawable.cat_12, "Whiskerina", "Maine Coon" ))
        catItem.add(Pet(R.drawable.cat_13, "Christina", "Cat"))
        //val adapter = PetAdapter(catItem)
        val linLayManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rev.layoutManager = linLayManager
        //rev.adapter = adapter
//        newItem = ArrayList()
//        lifecycleScope.launch {
//            newItem = PetDB.getMyDB(contx).petDao()
//                .getRecord(2) // PetDB.invoke(applicationContext).petDao().getRecord()
//        }
//        newAdapter = PetAdapter(newItem)
//        rev.adapter = newAdapter
//
//        newAdapter.setOnClickListener(object : PetAdapter.OnClickListener {
//            override fun onClick(position: Int, model: PetData) {
//                startActivity(Intent(contx, PetDetailsActivity::class.java))
//            }
//        })

        val newAdapter = PetDbAdapter(this, this@CatScrollActivity)
        rev.adapter = newAdapter
        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(PetViewModel::class.java)
        model.allCat.observe(this, Observer { lst ->
            lst.let { newAdapter.updateList(it) }
        })

//        adapter.setOnClickListener(object : PetAdapter.OnClickListener {
//            override fun onClick(position: Int, model: Pet) {
//                val bundle = Bundle()
//                bundle.putInt("image", model.getImg())
//                bundle.putString("name", model.getName())
//                val myIntent = Intent(this@CatScrollActivity, PetDetailsActivity::class.java)
//                myIntent.putExtras(bundle)
//                startActivity(myIntent)
//            }
//        })

        val homeBtn : FloatingActionButton = findViewById(R.id.fab)
        homeBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username", name)
            bundle.putString("email", email)
            bundle.putString("address", address)
            val i = Intent(contx, LandingActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
        }
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
                    Toast.makeText(this@CatScrollActivity, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: Void?) {
                    user = User()
                    startActivity(Intent(this@CatScrollActivity, MainActivity::class.java))
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
        val i = Intent(contx, PetDetailsActivity::class.java)
        i.putExtras(bundle)
        startActivity(i)
    }
}