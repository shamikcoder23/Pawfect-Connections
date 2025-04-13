package com.example.myapplication

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.ByteArrayInputStream

class PetAdapter (private var lst : List<PetData>) : RecyclerView.Adapter<PetAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder (itemView) {
        val txt : TextView = itemView.findViewById(R.id.list_item_name)
        val img : ImageView = itemView.findViewById(R.id.list_item_img)
        val brd : TextView = itemView.findViewById(R.id.list_item_breed)

        //val b1 : Button = itemView.findViewById(R.id.chat1)
        //val b2 : Button = itemView.findViewById(R.id.add1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetAdapter.ViewHolder {
        val petItemView = LayoutInflater.from(parent.context).inflate(R.layout.pet_item, parent, false)
        return ViewHolder(petItemView)
    }

    override fun onBindViewHolder(holder: PetAdapter.ViewHolder, position: Int) {
        val pet : PetData = lst[position]
        holder.txt.text = pet.name //getName() //name
        holder.brd.text = pet.category  //getBreed()  //category
        //holder.img.setImageResource(pet.getImg())
        holder.img.setImageBitmap(BitmapFactory.decodeByteArray(pet.image, 0, pet.image.size))

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, pet)
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }


    interface OnClickListener {
        fun onClick(position: Int, model: PetData)
    }

    fun setOnClickListener (listener: OnClickListener) {
        this.onClickListener = listener
    }
}