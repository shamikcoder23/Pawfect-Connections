package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ShopDBAdapter(val context: Context, private var onClickListener: OnClickListener):
    RecyclerView.Adapter<ShopDBAdapter.ViewHolder>() {
    private val items = ArrayList<ShopData>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txt : TextView = itemView.findViewById(R.id.list_item_name)
        val img : ImageView = itemView.findViewById(R.id.list_item_img)
        val prc : TextView = itemView.findViewById(R.id.list_item_breed)

        val b: Button = itemView.findViewById(R.id.add3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopDBAdapter.ViewHolder {
        val accItemView = LayoutInflater.from(parent.context).inflate(R.layout.acc_item, parent, false)
        return ViewHolder(accItemView)
    }

    override fun onBindViewHolder(holder: ShopDBAdapter.ViewHolder, position: Int) {
        val item: ShopData = items[position]
        holder.txt.text = item.name
        holder.prc.text = item.price
        holder.img.setImageBitmap(BitmapFactory.decodeByteArray(item.image, 0, item.image.size))

        holder.b.text = ContextCompat.getString(context, R.string.rem_basket)
        holder.b.textSize = 14F
        holder.b.setOnClickListener {
            onClickListener.onClick(item, position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newItems: List<ShopData>){
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(model: ShopData, position: Int)
    }

    fun setOnClickListener (listener: OnClickListener) {
        this.onClickListener = listener
    }
}