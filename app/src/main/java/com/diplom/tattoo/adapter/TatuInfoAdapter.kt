package com.diplom.tattoo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.diplom.tattoo.R
import com.diplom.tattoo.data.Tattoo
import com.squareup.picasso.Picasso

class TatuInfoAdapter(Context: Context, private val tatu: List<String>) :
    RecyclerView.Adapter<TatuInfoAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(Context)

    override fun getItemCount(): Int = tatu.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.photo_gallery_info_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): String = tatu[position]


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.photo_gallery_avatar)

        fun bind(data: String) {
            Picasso.get().load(data).placeholder(R.drawable.tattoo).into(image)
        }

    }
}