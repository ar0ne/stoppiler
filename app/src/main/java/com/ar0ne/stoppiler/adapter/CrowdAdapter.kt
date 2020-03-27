package com.ar0ne.stoppiler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.domain.User

class CrowdAdapter(var users: List<User>, val callback: Callback) :
    RecyclerView.Adapter<CrowdAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.crowd_item_cardview,
            parent,
            false
        )
    )

    override fun getItemCount() = users.size
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(users[position])
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val personName = itemView.findViewById<TextView>(R.id.person_name)
        private val personAge = itemView.findViewById<TextView>(R.id.person_age)
        private val personSex = itemView.findViewById<TextView>(R.id.person_sex)
        private val personPhoto = itemView.findViewById<ImageView>(R.id.person_photo)
        fun bind(user: User) {
            personName.text = user.name
            personAge.text = user.age.toString()
            personSex.text = user.sex.toString()
            personPhoto.setImageResource(R.mipmap.ic_launcher)
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(users[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(user: User)
    }
}