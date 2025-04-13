package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PetDbAdapter(val context: Context, private var onClickListener: OnClickListener): RecyclerView.Adapter<PetDbAdapter.ViewHolder>() {
    private val allPets = ArrayList<PetData>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txt : TextView = itemView.findViewById(R.id.list_item_name)
        val img : ImageView = itemView.findViewById(R.id.list_item_img)
        val brd : TextView = itemView.findViewById(R.id.list_item_breed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val petItemView = LayoutInflater.from(parent.context).inflate(R.layout.pet_item, parent, false)
        return ViewHolder(petItemView)
    }

    override fun getItemCount(): Int {
        return allPets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pet : PetData = allPets[position]
        holder.txt.text = pet.name
        holder.brd.text = pet.category
        val resized = resize(BitmapFactory.decodeByteArray(pet.image, 0, pet.image.size))
        holder.img.setImageBitmap(resized /*BitmapFactory.decodeByteArray(pet.image, 0, pet.image.size)*/)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(pet, position)
        }
    }

    interface OnClickListener {
        fun onClick(model: PetData, position: Int)
    }

    fun setOnClickListener (listener: OnClickListener) {
        this.onClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newPet: List<PetData>){
        allPets.clear()
        allPets.addAll(newPet)
        notifyDataSetChanged()
    }

    private fun resize(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, 400, 400, false)
    }

}