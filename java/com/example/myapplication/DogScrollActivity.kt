package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DogScrollActivity : AppCompatActivity(), PetDbAdapter.OnClickListener {
    private lateinit var dogItem : ArrayList<Pet>

    private lateinit var account: Auth0

    private var user = User()

//    private lateinit var newItem: List<PetData>
//    private lateinit var newAdapter: PetAdapter

    private val contx: Context = this@DogScrollActivity

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

//        db = Room.databaseBuilder(applicationContext, PetDB::class.java, "pets").build()
//        dao = db.petDao()
//        dao.insertRecord(PetData("Lisa", "Labrador", getString(R.string.dummy)))

        val rev : RecyclerView = findViewById(R.id.rev)
        dogItem = ArrayList()
        dogItem.add(Pet(R.drawable.dog_1, "Lisa", "Labrador"))
        dogItem.add(Pet(R.drawable.dog_2, "Don", "Labrador"))
        dogItem.add(Pet(R.drawable.dog_3, "Remy", "Husky"))
        dogItem.add(Pet(R.drawable.dog_4, "Butch", "Labrador"))
        dogItem.add(Pet(R.drawable.dog_5, "Maple", "Rottweiler"))
        dogItem.add(Pet(R.drawable.dog_6, "Hammy", "Rottweiler"))
        dogItem.add(Pet(R.drawable.dog_7, "Dona", "Husky"))
        dogItem.add(Pet(R.drawable.dog_8, "Olivia", "Husky"))
        dogItem.add(Pet(R.drawable.dog_9, "Jim and Jam", "Labradors"))
        dogItem.add(Pet(R.drawable.dog_10, "Powder", "Husky"))
        dogItem.add(Pet(R.drawable.dog_11, "Connor", "Husky"))
        dogItem.add(Pet(R.drawable.dog_12, "Ginger", "Husky"))
        dogItem.add(Pet(R.drawable.dog_13, "Kim", "Husky"))
        dogItem.add(Pet(R.drawable.dog_14, "Sunny", "Golden Retriever"))
        dogItem.add(Pet(R.drawable.dog_15, "Rocky", "Rottweiler"))
        dogItem.add(Pet(R.drawable.dog_16, "Luna", "Labrador"))
        dogItem.add(Pet(R.drawable.dog_17, "Pluto", "Corgi"))
        dogItem.add(Pet(R.drawable.dog_18, "Alice", "Labrador"))
        //val adapter = PetAdapter(dogItem)
        val linLayManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rev.layoutManager = linLayManager
        //rev.adapter = adapter
//        newItem = ArrayList()
//        db = PetDB.getMyDB(contx)
//        dao = db.petDao()
//        lifecycleScope.launch {
////            newItem = PetDB.getMyDB(contx).petDao()
////                .getRecord(1) //PetDB.invoke(this@DogScrollActivity).petDao().getRecord()
//            newItem = dao.getRecord(1)
//        }
//        newAdapter = PetAdapter(newItem)
//        rev.adapter = newAdapter
        val newAdapter = PetDbAdapter(this, this@DogScrollActivity)
        rev.adapter = newAdapter
        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[PetViewModel::class.java]
        model.allDog.observe(this) { lst ->
            lst.let { newAdapter.updateList(it) }
        }

//        newAdapter.setOnClickListener(object : PetAdapter.OnClickListener {
//            override fun onClick(position: Int, model: PetData) {
//                startActivity(Intent(this@DogScrollActivity, PetAdd::class.java))
//            }
//        })



//        adapter.setOnClickListener(object : PetAdapter.OnClickListener {
//            override fun onClick(position: Int, model: Pet) {
//                val bundle = Bundle()
//                bundle.putInt("image", model.getImg())
//                bundle.putString("name", model.getName())
//                val myIntent = Intent(this@DogScrollActivity, PetDetailsActivity::class.java)
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
                    Toast.makeText(this@DogScrollActivity, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: Void?) {
                    user = User()
                    startActivity(Intent(this@DogScrollActivity, MainActivity::class.java))
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
