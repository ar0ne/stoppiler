package com.ar0ne.stoppiler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.domain.GoodsType
import com.ar0ne.stoppiler.domain.Stock
import com.ar0ne.stoppiler.domain.StockRecord
import com.ar0ne.stoppiler.ui.SolidFontAwesomeTextView

class StockAdapter(var stock: Stock, val callback: Callback, val removeCallback: Callback) :
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
        private val productPhoto =
            itemView.findViewById<SolidFontAwesomeTextView>(R.id.product_photo)
        private val productVolume = itemView.findViewById<TextView>(R.id.product_volume)
        private val btnRemove =
            itemView.findViewById<SolidFontAwesomeTextView>(R.id.product_btn_remove)

        fun bind(record: StockRecord) {
            productName.text = record.goods.name
            productVolume.text = "${record.volume} ${record.goods.unit.repr}"
            productPhoto.text = getStockRecordIcon(itemView, record)

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(
                    stock.getRecord(
                        adapterPosition
                    )
                )
            }

            btnRemove.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) removeCallback.onItemClicked(
                    stock.getRecord(
                        adapterPosition
                    )
                )
            }
        }
    }

    fun getStockRecordIcon(view: View, record: StockRecord): String {
        val resourceId = record.goods.iconResourceId
        if (resourceId != null) {
            return view.resources.getString(resourceId)
        }
        return when (record.goods.type) {
            GoodsType.FOOD -> {
                view.resources.getString(R.string.food_icon)
            }
            GoodsType.WATER -> {
                view.resources.getString(R.string.water_icon)
            }
            GoodsType.TOILET_PAPER -> {
                view.resources.getString(R.string.toilet_paper_icon)
            }
        }
    }

    interface Callback {
        fun onItemClicked(record: StockRecord)
    }
}