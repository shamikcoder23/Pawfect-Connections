package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityPetAddBinding
import java.io.ByteArrayOutputStream
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.net.Uri
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.lifecycle.ViewModelProvider

class PetAdd : AppCompatActivity() {
    var imgPath: ByteArray? = null

    val contx: Context = this@PetAdd

    private lateinit var typeText: EditText
    private lateinit var nameText: EditText
    private lateinit var breedText: EditText

    lateinit var model: PetViewModel

    private var username: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //binding = ActivityPetAddBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_pet_add) //binding.root
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        model = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(PetViewModel::class.java)
        typeText = findViewById(R.id.type)
        nameText = findViewById(R.id.name)
        breedText = findViewById(R.id.breed)
        val b1: ImageButton = findViewById(R.id.btn)
        b1.setOnClickListener { galleryLauncher.launch("image/*") }
//        binding.btn.setOnClickListener{
//            galleryLauncher.launch("image/*")
////            val i = Intent()
////            i.setType("image/*")
////            i.setAction(Intent.ACTION_GET_CONTENT)
//            //newLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//        }
        username = intent.extras?.getString("username")
        email = intent.extras?.getString("email")
        //binding.submitBtn.setOnClickListener {
            //val image = BitmapFactory.decodeFile(imgPath)
        val b2: Button = findViewById(R.id.submitBtn)
        b2.setOnClickListener {
            //val typeText: EditText = findViewById(R.id.type) //= binding.type.toString()
            val type: String = typeText.text.toString().trim()
            //val nameText: EditText = findViewById(R.id.name) // binding.name.toString().trim()
            val name: String = nameText.text.toString().trim()
            //val breedText: EditText = findViewById(R.id.breed) //binding.breed.toString().trim()
            val breed: String = breedText.text.toString().trim()
            if(type.isEmpty()){
                Toast.makeText(this, R.string.type_0, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(name.isEmpty()){
                Toast.makeText(this, R.string.name_0, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(breed.isEmpty()){
                Toast.makeText(this, R.string.category_0, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(imgPath == null || imgPath!!.isEmpty()){
                Toast.makeText(this, R.string.img_0, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val typeDB = if(type.equals("dog", ignoreCase = true))
                1
            else if(type.equals("cat", ignoreCase = true))
                2
            else if(type.equals("bird", ignoreCase = true))
                3
            else if(type.equals("fish", ignoreCase = true))
                4
            else if(type.equals("plant", ignoreCase = true))
                5
            else{
                Toast.makeText(this, R.string.type_01, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

//            val stream = ByteArrayOutputStream()
//            image.compress(Bitmap.CompressFormat.PNG, 100, stream)
//            val imgInByte: ByteArray = stream.toByteArray()

//            lifecycleScope.launch {
//                PetDB.getMyDB(contx).petDao()
//                    .insertRecord(PetData(0, typeDB, imgPath!!, name, breed))
//            }
            model.addPet(PetData(0,typeDB, imgPath!!, name, breed))
            val bundle = Bundle()
            bundle.putString("username", username)
            bundle.putString("email", email)
            val i = Intent(contx, LandingActivity::class.java)
            i.putExtras(bundle)
            startActivity(i)
            //startActivity(Intent(this@PetAdd, LandingActivity::class.java))
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
            //imgPath = galleryUi.toString()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

//    val newLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        result ->
//        if(result.resultCode == Activity.RESULT_OK) {
//            val resData = result.data
//            resData?.data?.let {
//                selectedUri ->
//                val selectedImageBitmap: Bitmap? = try {
//                    MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                    null
//                }
//                selectedImageBitmap?.let {
//                    imageView.setImageBitmap(it)
//                }
//            }
//        }
//    }
//    val newLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
//        uri ->
//        if(uri == null)
//            Toast.makeText(this, "No media selected", Toast.LENGTH_LONG)
//        else{
//            try{
//                binding.img.setImageURI(uri)
//                imgPath = uri.toString()
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
//    }

}