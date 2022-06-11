package com.diplom.tattoo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diplom.tattoo.R
import com.diplom.tattoo.data.Tattoo
import com.squareup.picasso.Picasso

class TatuAdapter(
    Context: Context,
    private val tatu: ArrayList<Tattoo>,
    private val clickListener: (position: Int) -> Unit
) :
    RecyclerView.Adapter<TatuAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(Context)

    override fun getItemCount(): Int = tatu.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            inflater.inflate(
                R.layout.photo_gallery_item,
                parent, false
            ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): Tattoo = tatu[position]


    class ViewHolder(
        itemView: View,
        listener: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.photo_gallery_avatar)
        private val title: TextView = itemView.findViewById(R.id.photo_gallery_name)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    listener(position)
            }
        }

        fun bind(data: Tattoo) {
            Picasso.get().load(data.photoUrl[0]).placeholder(R.drawable.tattoo).into(image)
            title.text = data.title
        }

    }
}