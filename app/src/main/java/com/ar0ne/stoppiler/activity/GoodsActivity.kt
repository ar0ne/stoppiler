package com.ar0ne.stoppiler.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.adapter.GoodsSearchAdapter
import com.ar0ne.stoppiler.domain.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_goods.*

class GoodsActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var goodsSearchAdapter: GoodsSearchAdapter? = null

    companion object {
        var goods = mutableListOf(
            Goods("Toilet Paper", GoodsType.TOILET_PAPER, 1.94, Units.METER, Priority.HIGH),

            Goods("Buckwheat Cereal", GoodsType.FOOD, 3.29, Units.GRAM, Priority.MEDIUM),
            Goods("Rice", GoodsType.FOOD, 3.3, Units.GRAM, Priority.MEDIUM),
            Goods("Pasta", GoodsType.FOOD, 3.58, Units.GRAM, Priority.MEDIUM),
            Goods("Bread", GoodsType.FOOD, 2.39, Units.GRAM, Priority.MEDIUM),
            Goods("Eggs", GoodsType.FOOD, 1.53, Units.GRAM, Priority.LOW),
            Goods("Orange", GoodsType.FOOD, 0.38, Units.GRAM, Priority.LOW),
            Goods("Banana", GoodsType.FOOD, 0.87, Units.GRAM, Priority.LOW),
            Goods("Apple", GoodsType.FOOD, 0.48, Units.GRAM, Priority.LOW),
            Goods("Pear", GoodsType.FOOD, 0.41, Units.GRAM, Priority.LOW),
            Goods("Fish", GoodsType.FOOD, 1.28, Units.GRAM, Priority.MEDIUM),
            Goods("Chicken", GoodsType.FOOD, 1.6, Units.GRAM, Priority.MEDIUM),
            Goods("Pork", GoodsType.FOOD, 4.01, Units.GRAM, Priority.MEDIUM),
            Goods("Beef", GoodsType.FOOD, 1.91, Units.GRAM, Priority.MEDIUM),
            Goods("Cheese", GoodsType.FOOD, 3.52, Units.GRAM, Priority.MEDIUM),
            Goods("Carrot", GoodsType.FOOD, 0.29, Units.GRAM, Priority.LOW),
            Goods("Tomato", GoodsType.FOOD, 0.12, Units.GRAM, Priority.LOW),
            Goods("Salad", GoodsType.FOOD, 0.15, Units.GRAM, Priority.LOW),
            Goods("A Pineapple", GoodsType.FOOD, 0.49, Units.GRAM, Priority.LOW),
            Goods("Chocolate", GoodsType.FOOD, 5.52, Units.GRAM, Priority.MEDIUM),
            Goods("Salami", GoodsType.FOOD, 5.36, Units.GRAM, Priority.MEDIUM),

            Goods("Plain Water", GoodsType.WATER, 2.0, Units.LITER, Priority.HIGH),
            Goods("Orange Juice", GoodsType.WATER, 2.0, Units.LITER, Priority.MEDIUM),
            Goods("Milk", GoodsType.WATER, 2.0, Units.LITER, Priority.LOW)
        )
        var filteredGoods: MutableList<Goods>? = null


        const val SHOW_ADD_GOODS_REQUEST = 8
        const val EXTRA_GOODS_NAME = "name"
        const val EXTRA_GOODS_UNIT = "unit"
        const val EXTRA_GOODS_VOLUME = "volume"
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)

        loadData()

        goodsSearchAdapter = GoodsSearchAdapter(this, filteredGoods as ArrayList<Goods>)

        goods_list_view.adapter = goodsSearchAdapter

        search.setOnQueryTextListener(this)

        goods_list_view.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val item = parent.getItemAtPosition(position) as Goods
                val intent = Intent(this, GoodsItemWindow::class.java)
                intent.putExtra(EXTRA_GOODS_NAME, item.name)
                intent.putExtra(EXTRA_GOODS_UNIT, item.unit.toString().toLowerCase())
                startActivityForResult(intent, SHOW_ADD_GOODS_REQUEST)
            }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        goodsSearchAdapter?.filter(newText)
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
                        val result = Intent()
                        result.putExtra(EXTRA_GOODS_NAME, productName)
                        result.putExtra(EXTRA_GOODS_VOLUME, volume)
                        setResult(Activity.RESULT_OK, result)
                        finish()
                    }
                }
            }
        }
    }

    fun onBtnCloseClicked(view: View) {
        val result = Intent()
        setResult(Activity.RESULT_CANCELED, result)
        finish()
    }

    private fun getUsersGoodsNames(): List<String> {
        val sPref = getSharedPreferences("stop", Context.MODE_PRIVATE)
        val stockJson: String? = sPref?.getString(MainActivity.STOCK_KEY, null)
        if (stockJson != null) {
            val stock = Gson().fromJson(stockJson, Stock::class.java)
            return stock.getGoodsNames()
        }
        return listOf()
    }

    private fun loadData() {
        val usersGoods = getUsersGoodsNames()
        filteredGoods = goods.filterNot {
            it.name in usersGoods
        }.toMutableList()
    }

}
