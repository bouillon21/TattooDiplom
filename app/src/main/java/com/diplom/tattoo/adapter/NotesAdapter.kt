package com.diplom.tattoo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diplom.tattoo.R
import com.diplom.tattoo.data.Record

class NotesAdapter(Context: Context, private val data: List<Record>) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(Context)

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.my_notes_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int): Record = data[position]

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val service: TextView = itemView.findViewById(R.id.textService)
        private val master: TextView = itemView.findViewById(R.id.textMaster)
        private val data: TextView = itemView.findViewById(R.id.textData)
        private val time: TextView = itemView.findViewById(R.id.textTime)
        private val tattoo: TextView = itemView.findViewById(R.id.textTattoo)

        fun bind(record: Record) {
            service.text = "Тату"
            master.text = record.master
            data.text = record.data
            time.text = record.time
            tattoo.text = record.tattoo
        }

    }
}