package com.ar0ne.stoppiler.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.SearchView
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.adapter.GoodsAdapter
import com.ar0ne.stoppiler.domain.Goods
import com.ar0ne.stoppiler.domain.GoodsType
import com.ar0ne.stoppiler.domain.Priority
import com.ar0ne.stoppiler.domain.Units
import kotlinx.android.synthetic.main.activity_goods_add.*

class GoodsAddActivity : AppCompatActivity(), SearchView.OnQueryTextListener  {

    var goodsAdapter: GoodsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_add)

        goodsAdapter = GoodsAdapter(this, goods as ArrayList<Goods>)

        goods_list_view.adapter = goodsAdapter

        search.setOnQueryTextListener(this)

        goods_list_view.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // show pop up
            Log.d("LOG", "Clicked pos=$position, id=$id")
            val item = parent.getItemAtPosition(position) as Goods
        }

    }

    companion object {
        var goods: MutableList<Goods> = mutableListOf(
            Goods("Bread", GoodsType.FOOD, 0.5, Units.KILOGRAM, Priority.MEDIUM),
            Goods("Salat", GoodsType.FOOD, 0.2, Units.KILOGRAM, Priority.LOW),
            Goods("Meat", GoodsType.FOOD, 0.4, Units.KILOGRAM, Priority.MEDIUM),
            Goods("Orange Juice", GoodsType.WATER, 1.0, Units.LITER, Priority.MEDIUM),
            Goods("Toilet Paper", GoodsType.HOME, 1.0, Units.METER, Priority.HIGH)
        )
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        goodsAdapter?.filter(newText)
        return false
    }

}
