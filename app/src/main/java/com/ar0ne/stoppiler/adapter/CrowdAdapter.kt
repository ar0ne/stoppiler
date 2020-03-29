package com.ar0ne.stoppiler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.domain.Sex
import com.ar0ne.stoppiler.domain.User
import com.ar0ne.stoppiler.ui.SolidFontAwesomeTextView

class CrowdAdapter(
    val context: Context,
    var users: List<User>, val callback: Callback
) :
    RecyclerView.Adapter<CrowdAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.crowd_item,
            parent,
            false
        )
    )

    private fun getUserIcon(user: User): String {
        return context.resources.getString(if (user.sex == Sex.MALE) R.string.male_icon else R.string.female_icon)
    }

    override fun getItemCount() = users.size
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(users[position])
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val personName = itemView.findViewById<TextView>(R.id.person_name)
        private val personAge = itemView.findViewById<TextView>(R.id.person_age)
        private val personPhoto = itemView.findViewById<SolidFontAwesomeTextView>(R.id.person_photo)
        fun bind(user: User) {
            personName.text = user.name
            personAge.text = "(${user.age} y/o)"
            personPhoto.text = getUserIcon(user)
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(users[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(user: User)
    }
}