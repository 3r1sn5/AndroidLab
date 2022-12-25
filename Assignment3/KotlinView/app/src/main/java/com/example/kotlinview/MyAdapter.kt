package com.example.kotlinview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val data: ArrayList<Contact>) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(v: View): RecyclerView.ViewHolder(v)
    {
        val tvName = v.findViewById<TextView>(R.id.tvName)
        val tvPhone = v.findViewById<TextView>(R.id.tvPhone)
        val imgDelete = v.findViewById<ImageView>(R.id.imgDelete)
    }
    override fun getItemCount() = data.size
    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder
    {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_row, viewGroup, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.tvName.text = data[position].name
        holder.tvPhone.text = data[position].phone
        holder.imgDelete.setOnClickListener {
            data.removeAt(position)
            notifyDataSetChanged()
        }
    }
}