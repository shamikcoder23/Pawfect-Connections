package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AccAdapter (private var lst: ArrayList<Acc>, private var onClickListener: OnClickListener): RecyclerView.Adapter<AccAdapter.ViewHolder>(){

    inner class ViewHolder (itemView : View) : RecyclerView.ViewHolder (itemView) {
        val txt : TextView = itemView.findViewById(R.id.list_item_name)
        val img : ImageView = itemView.findViewById(R.id.list_item_img)
        val prc : TextView = itemView.findViewById(R.id.list_item_breed)

        val b: Button = itemView.findViewById(R.id.add3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccAdapter.ViewHolder {
        val accItemView = LayoutInflater.from(parent.context).inflate(R.layout.acc_item, parent, false)
        return ViewHolder(accItemView)
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    override fun onBindViewHolder(holder: AccAdapter.ViewHolder, position: Int) {
        val item : Acc = lst[position]
        holder.txt.text = item.getName()
        holder.prc.text = item.getPrice()
        holder.img.setImageResource(item.getImg())

        holder.b.setOnClickListener {
            onClickListener.onClick(position, item)
        }
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Acc)
    }
    fun setOnClickListener (listener: OnClickListener) {
        this.onClickListener = listener
    }
}