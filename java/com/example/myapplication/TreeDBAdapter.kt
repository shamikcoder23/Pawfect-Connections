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

class TreeDBAdapter(val context: Context, private var onClickListener: OnClickListener): RecyclerView.Adapter<TreeDBAdapter.ViewHolder>() {
    private val plants = ArrayList<TreeData>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txt : TextView = itemView.findViewById(R.id.list_item_name)
        val img : ImageView = itemView.findViewById(R.id.list_item_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val plantItemView = LayoutInflater.from(parent.context).inflate(R.layout.tree_item, parent, false)
        return ViewHolder(plantItemView)
    }

    override fun getItemCount(): Int {
        return plants.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant : TreeData = plants[position]
        holder.txt.text = plant.name
        val resized = resize(BitmapFactory.decodeByteArray(plant.image, 0, plant.image.size))
        holder.img.setImageBitmap(resized /*BitmapFactory.decodeByteArray(pet.image, 0, pet.image.size)*/)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(plant, position)
        }
    }

    interface OnClickListener {
        fun onClick(model: TreeData, position: Int)
    }

    fun setOnClickListener (listener: OnClickListener) {
        this.onClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newPlants: List<TreeData>){
        plants.clear()
        plants.addAll(newPlants)
        notifyDataSetChanged()
    }

    private fun resize(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, 400, 400, false)
    }
}