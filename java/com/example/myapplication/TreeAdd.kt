package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import java.io.ByteArrayOutputStream

class TreeAdd : AppCompatActivity() {
    var imgPath: ByteArray? = null

    val contx: Context = this@TreeAdd

    private lateinit var nameText: EditText

    lateinit var model: TreeViewModel

    private var username: String? = null
    private var email: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tree_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(TreeViewModel::class.java)
        nameText = findViewById(R.id.name)
        val b1: ImageButton = findViewById(R.id.btn)
        b1.setOnClickListener { galleryLauncher.launch("image/*") }
        username = intent.extras?.getString("username")
        email = intent.extras?.getString("email")

        val b2: Button = findViewById(R.id.submitBtn)
        b2.setOnClickListener {
            val name: String = nameText.text.toString().trim()
            if(name.isEmpty()){
                Toast.makeText(this, R.string.name_0, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(imgPath == null || imgPath!!.isEmpty()){
                Toast.makeText(this, R.string.img_0, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            model.addPlant(TreeData(0, imgPath!!, name))
            val bundle = Bundle()
            bundle.putString("username", username)
            bundle.putString("email", email)
            val i = Intent(contx, LandingTreeActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
            this.finish()
        }
    }
    @SuppressLint("Recycle")
    fun convertToByteArray(uri: Uri, context: Context): ByteArray? {
        try{
            val ipStream = context.contentResolver.openInputStream(uri)
            val byteBuffer = ByteArrayOutputStream()
            val size = 1024
            val buffer = ByteArray(size)
            var len: Int
            while(ipStream?.read(buffer).also { len = it!! } != -1){
                byteBuffer.write(buffer, 0, len)
            }
            return byteBuffer.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
        val galleryUi = it
        try{
            val img: ImageView = findViewById(R.id.img)
            img.setImageURI(galleryUi)
            if (galleryUi != null) {
                imgPath = convertToByteArray(galleryUi, contx)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}