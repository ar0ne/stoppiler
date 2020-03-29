package com.ar0ne.stoppiler.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.adapter.StockAdapter
import com.ar0ne.stoppiler.domain.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Collections.max


class MainActivity : AppCompatActivity() {

    private var introShown = false

    private var stockAdapter: StockAdapter? = null

    companion object {
        const val SHOW_INTRO_REQUEST = 1
        const val SHOW_CROWD_REQUEST = 4
        const val SHOW_GOODS_REQUEST = 7
        const val SHOW_UPDATE_GOODS_REQUEST = 9
        const val SHOW_HELP_REQUEST = 11

        val stock = Stock(
            mutableListOf(
                StockRecord(
                    Goods("Bread", GoodsType.FOOD, 500.0, Units.GRAM, Priority.MEDIUM),
                    300
                )
            )
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!introShown) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivityForResult(intent, SHOW_INTRO_REQUEST)
        }

        stockAdapter = StockAdapter(
            stock,
            object : StockAdapter.Callback {
                override fun onItemClicked(record: StockRecord) = showUpdateProductView(record)
            },
            object: StockAdapter.Callback {
                override fun onItemClicked(record: StockRecord) = onRemoveRecordBtnClicked(record)
            })

        main_goods_recycler_view.adapter = stockAdapter
        updateProgress()
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
                            stock.addRecord(this, productVolume)
                            stockAdapter?.notifyDataSetChanged()
                            updateProgress()
                        }
                    }
                }
            }
            SHOW_CROWD_REQUEST -> {

            }
            SHOW_UPDATE_GOODS_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val productName = data?.getStringExtra(GoodsActivity.EXTRA_GOODS_NAME)
                    val productVolume = data?.getIntExtra(GoodsActivity.EXTRA_GOODS_VOLUME, 0)
                    if (productName != null && productVolume != null && productVolume > 0) {
                        // @todo: get data from dataSource
                        stock.getRecord(productName)?.apply {
                            this.volume = productVolume
                            stockAdapter?.notifyDataSetChanged()
                            updateProgress()
                        }
                    }
                }
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

    fun onButtonOpenHelpClicked(view: View) {
        val intent = Intent(this, HelpActivity::class.java)
        startActivityForResult(intent, SHOW_HELP_REQUEST)
    }

    fun showUpdateProductView(record: StockRecord) {
        val intent = Intent(this, GoodsItemWindow::class.java)
        intent.putExtra(GoodsActivity.EXTRA_GOODS_NAME, record.goods.name)
        intent.putExtra(GoodsActivity.EXTRA_GOODS_UNIT, record.goods.unit.repr)
        intent.putExtra(GoodsActivity.EXTRA_GOODS_VOLUME, record.volume)
        startActivityForResult(intent, SHOW_UPDATE_GOODS_REQUEST)
    }

    @SuppressLint("SetTextI18n")
    fun updateProgress() {
        val foodEstimation = stock.getFoodEstimation()
        val waterEstimation = stock.getWaterEstimation()
        val paperEstimation = stock.getToiletPaperEstimation()
        val max = max(listOf(foodEstimation, waterEstimation, paperEstimation))
        food_progressBar.max = max
        water_progressBar.max = max
        toiletPaper_progressBar.max = max
        food_progressBar.setProgress(foodEstimation, true)
        water_progressBar.setProgress(waterEstimation, true)
        toiletPaper_progressBar.setProgress(paperEstimation, true)
        food_estimation.text = "$foodEstimation days"
        water_estimation.text = "$waterEstimation days"
        paper_estimation.text = "$paperEstimation days"
    }


    fun onRemoveRecordBtnClicked(record: StockRecord) {
        val builder = AlertDialog.Builder(this)

        with(builder) {
            setTitle(R.string.remove_alert_title)
            setMessage(R.string.remove_alert_confirmation_text)
            setPositiveButton(android.R.string.yes) { _, _ ->
                stock.removeRecord(record)
                stockAdapter?.notifyDataSetChanged()
            }
            setNegativeButton(android.R.string.no) { dialog, which -> }
            show()
        }
    }
}
