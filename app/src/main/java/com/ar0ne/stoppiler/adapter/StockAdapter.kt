package com.ar0ne.stoppiler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.domain.Stock
import com.ar0ne.stoppiler.domain.StockRecord

class StockAdapter(var stock: Stock, val callback: Callback) :
    RecyclerView.Adapter<StockAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MainHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.goods_item,
            parent,
            false
        )
    )

    override fun getItemCount() = stock.size()
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(stock.getRecord(position))
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName = itemView.findViewById<TextView>(R.id.product_name)
        private val productPhoto = itemView.findViewById<ImageView>(R.id.product_photo)
        fun bind(record: StockRecord) {
            productName.text = record.goods.name
            productPhoto.setImageResource(R.mipmap.ic_launcher_round)
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(stock.getRecord(adapterPosition))
            }
        }
    }

    interface Callback {
        fun onItemClicked(record: StockRecord)
    }
}