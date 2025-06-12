package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PurposeAdapter(private val purposes: MutableList<Purpose> = mutableListOf()) :
    RecyclerView.Adapter<PurposeAdapter.PurposeViewHolder>() {

    class PurposeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.titleText)
        val timeText: TextView = view.findViewById(R.id.timeText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurposeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_purpose, parent, false)
        return PurposeViewHolder(view)
    }

    override fun onBindViewHolder(holder: PurposeViewHolder, position: Int) {
        val purpose = purposes[position]
        holder.titleText.text = purpose.title
        holder.timeText.text = "Giorni: ${purpose.days.joinToString()} - Orario: ${purpose.timestamp.toFormattedTime()}"

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PurposeDetailActivity::class.java)
            intent.putExtra("title", purpose.title)
            intent.putExtra("time", purpose.timestamp.toFormattedTime())
            intent.putExtra("days", purpose.days.joinToString())
            intent.putExtra("lat", purpose.latitude ?: 0.0)
            intent.putExtra("lon", purpose.longitude ?: 0.0)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = purposes.size

    fun setData(newList: List<Purpose>) {
        purposes.clear()
        purposes.addAll(newList)
        notifyDataSetChanged()
    }
}

