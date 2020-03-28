package com.ar0ne.stoppiler.activity

import android.app.Activity
import android.content.Intent
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
import kotlinx.android.synthetic.main.activity_goods.*

class GoodsActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    var goodsAdapter: GoodsAdapter? = null

    companion object {
        var goods: MutableList<Goods> = mutableListOf(
            Goods("Bread", GoodsType.FOOD, 500.0, Units.GRAM, Priority.MEDIUM),
            Goods("Salat", GoodsType.FOOD, 200.0, Units.GRAM, Priority.LOW),
            Goods("Meat", GoodsType.FOOD, 400.0, Units.GRAM, Priority.MEDIUM),
            Goods("Orange Juice", GoodsType.WATER, 1.0, Units.LITER, Priority.MEDIUM),
            Goods("Toilet Paper", GoodsType.HOME, 1.0, Units.METER, Priority.HIGH)
        )
        const val SHOW_ADD_GOODS_REQUEST = 8
        const val EXTRA_GOODS_NAME = "name"
        const val EXTRA_GOODS_UNIT = "unit"
        const val EXTRA_GOODS_VOLUME = "volume"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)

        goodsAdapter = GoodsAdapter(this, goods as ArrayList<Goods>)

        goods_list_view.adapter = goodsAdapter

        search.setOnQueryTextListener(this)

        goods_list_view.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val item = parent.getItemAtPosition(position) as Goods
                val intent = Intent(this, GoodsAddWindow::class.java)
                intent.putExtra(EXTRA_GOODS_NAME, item.name)
                intent.putExtra(EXTRA_GOODS_UNIT, item.unit.repr)
                startActivityForResult(intent, SHOW_ADD_GOODS_REQUEST)
            }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        goodsAdapter?.filter(newText)
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SHOW_ADD_GOODS_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val productName: String? = data?.getStringExtra(EXTRA_GOODS_NAME)
                val volume: Int? = data?.getIntExtra(EXTRA_GOODS_VOLUME, 0)
                if (productName != null && volume != null && volume > 0) {
                    val product: Goods? = goods.find { it.name == productName }
                    product?.apply {
                        Log.d("LOG", "Added: $this")
                    }
                }
            }
        }
    }


}
