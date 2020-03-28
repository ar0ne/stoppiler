package com.ar0ne.stoppiler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.activity.GoodsAddActivity
import com.ar0ne.stoppiler.domain.Goods
import java.util.*
import kotlin.collections.ArrayList

class GoodsAdapter(
    internal val mContext: Context,
    val goodsList: ArrayList<Goods>
) : BaseAdapter() {

    private var inflater: LayoutInflater = LayoutInflater.from(mContext)

    inner class ViewHolder {
        internal var name: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder
        if (view == null) {
            holder = ViewHolder()
            view = inflater.inflate(R.layout.goods_item, null)

            holder.name = view!!.findViewById(R.id.name) as TextView
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }
        holder.name!!.text = GoodsAddActivity.goods[position].name
        return view
    }

    override fun getItem(position: Int): Any {
        return GoodsAddActivity.goods[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return GoodsAddActivity.goods.size
    }

    fun filter(searchText: String) {
        val loweredSearchText = searchText.toLowerCase(Locale.getDefault())
        GoodsAddActivity.goods = mutableListOf()
        if (searchText.isEmpty()) {
            GoodsAddActivity.goods = goodsList
        } else {
            for (product in goodsList) {
                if (product.name.toLowerCase(Locale.getDefault()).contains(loweredSearchText)) {
                    GoodsAddActivity.goods.add(product)
                }
            }
        }
        notifyDataSetChanged()
    }

}