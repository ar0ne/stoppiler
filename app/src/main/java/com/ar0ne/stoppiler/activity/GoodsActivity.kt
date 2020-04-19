package com.ar0ne.stoppiler.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import com.ar0ne.stoppiler.Const.EXTRA_GOODS_NAME
import com.ar0ne.stoppiler.Const.EXTRA_GOODS_UNIT
import com.ar0ne.stoppiler.Const.EXTRA_GOODS_VOLUME
import com.ar0ne.stoppiler.Const.SHOW_ADD_GOODS_REQUEST
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.adapter.GoodsSearchAdapter
import com.ar0ne.stoppiler.domain.*
import com.ar0ne.stoppiler.storage.AppDataStorage
import com.ar0ne.stoppiler.services.GoodsSamplesService
import kotlinx.android.synthetic.main.activity_goods.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

class GoodsActivity : AppCompatActivity(), SearchView.OnQueryTextListener, KoinComponent {

    private lateinit var goodsSearchAdapter: GoodsSearchAdapter

    private val appService by inject<AppDataStorage>()
    private val goodsSamplesService by inject<GoodsSamplesService>()

    private val goods = goodsSamplesService.getSamples()

    companion object {
        lateinit var filteredGoods: MutableList<Goods>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)

        val usersGoods = appService.getStock().getGoodsNames()
        filteredGoods = goods.filterNot {
            it.name in usersGoods
        }.toMutableList()
        filteredGoods.sortBy { it.name.toLowerCase() }

        goodsSearchAdapter = GoodsSearchAdapter(this, filteredGoods as ArrayList<Goods>)

        goods_list_view.adapter = goodsSearchAdapter

        search.setOnQueryTextListener(this)

        goods_list_view.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
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
        goodsSearchAdapter.filter(newText)
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

}
