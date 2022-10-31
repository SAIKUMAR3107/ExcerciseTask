package com.example.excercise

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class GridItemAdaptor(val url : ArrayList<String>,val title : ArrayList<String>,) : RecyclerView.Adapter<GridItemAdaptor.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardImage : ImageView = itemView.findViewById(R.id.cardImage)
        val cardTitle : TextView = itemView.findViewById(R.id.cardTitle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item_view,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardTitle.text = title[position]
        Picasso.get().load(url[position]).into(holder.cardImage)
        Log.d("kumar","${title[position].toString()}")
    }

    override fun getItemCount(): Int {
        return title.size
    }
}