package com.ar0ne.stoppiler.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.adapter.GoodsAdapter
import com.ar0ne.stoppiler.domain.Goods
import com.ar0ne.stoppiler.domain.GoodsType
import com.ar0ne.stoppiler.domain.Priority
import com.ar0ne.stoppiler.domain.Units
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var introShown = false

    private var goodsAdapter: GoodsAdapter? = null

    companion object {
        const val SHOW_INTRO_REQUEST = 1
        const val SHOW_CROWD_REQUEST = 4
        const val SHOW_GOODS_REQUEST = 7

        val stock = mutableListOf(
            Goods("Bread", GoodsType.FOOD, 500.0, Units.GRAM, Priority.MEDIUM)
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!introShown) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivityForResult(intent, SHOW_INTRO_REQUEST)
        }

        goodsAdapter = GoodsAdapter(
            stock,
            object : GoodsAdapter.Callback {
                override fun onItemClicked(item: Goods) = showUpdateProductView()
            })

        main_goods_recycler_view.adapter = goodsAdapter

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("LOG_MAIN", "Received: code=$requestCode, result=$resultCode")

        when (requestCode) {
            SHOW_INTRO_REQUEST -> {
                introShown = true
            }
            SHOW_GOODS_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val productName = data?.getStringExtra(GoodsActivity.EXTRA_GOODS_NAME)
                    val productVolume = data?.getIntExtra(GoodsActivity.EXTRA_GOODS_VOLUME, 0)
                    if (productName != null && productVolume != null && productVolume > 0) {
                        // @todo: get data from dataSource
                        val product: Goods? = GoodsActivity.goods.find { it.name == productName }
                        product?.apply {
                            stock.add(this)
                            goodsAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
            SHOW_CROWD_REQUEST -> {

            }
        }
    }


    fun onButtonOpenCrowdClicked(view: View) {
        val intent = Intent(this, CrowdActivity::class.java)
        startActivityForResult(intent, SHOW_CROWD_REQUEST)
    }

    fun onButtonAddGoodsClicked(view: View) {
        val intent = Intent(this, GoodsActivity::class.java)
        startActivityForResult(intent, SHOW_GOODS_REQUEST)
    }

    fun showUpdateProductView() {

    }
}
