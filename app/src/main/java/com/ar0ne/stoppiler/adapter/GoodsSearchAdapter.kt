package com.ar0ne.stoppiler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.activity.GoodsActivity
import com.ar0ne.stoppiler.domain.Goods
import java.util.*
import kotlin.collections.ArrayList

class GoodsSearchAdapter(
    mContext: Context,
    val goodsList: ArrayList<Goods>
) : BaseAdapter() {

    private var inflater: LayoutInflater = LayoutInflater.from(mContext)

    inner class ViewHolder {
        internal var name: TextView? = null
    }

    override fun getItem(position: Int): Any = GoodsActivity.filteredGoods[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getCount(): Int = GoodsActivity.filteredGoods.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder
        if (view == null) {
            holder = ViewHolder()
            view = inflater.inflate(R.layout.goods_search_item, null)

            holder.name = view?.findViewById(R.id.name) as TextView
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        holder.name?.text = GoodsActivity.filteredGoods[position].name
        return view
    }

    fun filter(searchText: String) {
        val loweredSearchText = searchText.toLowerCase(Locale.getDefault())
        GoodsActivity.filteredGoods = mutableListOf()
        if (searchText.isEmpty()) {
            GoodsActivity.filteredGoods = goodsList
        } else {
            for (product in goodsList) {
                if (product.name.toLowerCase(Locale.getDefault()).contains(loweredSearchText)) {
                    GoodsActivity.filteredGoods.add(product)
                }
            }
        }
        notifyDataSetChanged()
    }

}