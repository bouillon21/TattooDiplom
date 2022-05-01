package com.diplom.tattoo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diplom.tattoo.R
import com.diplom.tattoo.data.Master

class MasterAdapter(Context: Context, private val master: List<Master>) :
    RecyclerView.Adapter<MasterAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(Context)

    override fun getItemCount(): Int = master.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.photo_gallery_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int) : Master = master[position]


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.photo_gallery_avatar)
        private val title: TextView = itemView.findViewById(R.id.photo_gallery_name)

        fun bind(version: Master) {
            image.setImageResource(version.imageAndroid)
            title.text = version.title
        }

    }
}