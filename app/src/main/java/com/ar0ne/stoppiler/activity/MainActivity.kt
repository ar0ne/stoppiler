package com.ar0ne.stoppiler.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ar0ne.stoppiler.Const
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.adapter.StockAdapter
import com.ar0ne.stoppiler.di.appModules
import com.ar0ne.stoppiler.domain.*
import com.ar0ne.stoppiler.storage.AppDataStorage
import com.ar0ne.stoppiler.services.GoodsSamplesService
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.Collections.max


class MainActivity : AppCompatActivity() {

    private var introShown: Boolean = false
    private lateinit var stock: Stock
    private lateinit var stockAdapter: StockAdapter
    private val appService by inject<AppDataStorage>()
    private val goodsSamplesService by inject<GoodsSamplesService>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startKoin {
            androidContext(this@MainActivity)
            modules(appModules)
        }

        introShown = appService.isIntroShown()
        stock = appService.getStock()

        saveData()

        stockAdapter = StockAdapter(
            stock,
            object : StockAdapter.Callback {
                override fun onItemClicked(record: StockRecord) = showUpdateProductView(record)
            },
            object : StockAdapter.Callback {
                override fun onItemClicked(record: StockRecord) = onRemoveRecordBtnClicked(record)
            })

        main_goods_recycler_view.adapter = stockAdapter

        updateEstimations()

        if (!introShown) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivityForResult(intent, Const.SHOW_INTRO_REQUEST)
        }

        main_goods_recycler_view.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        updateEstimations()
    }

    override fun onStop() {
        super.onStop()
        saveData()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Const.SHOW_INTRO_REQUEST -> {
                introShown = true
            }
            Const.SHOW_GOODS_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val productName = data?.getStringExtra(Const.EXTRA_GOODS_NAME)
                    val productVolume = data?.getIntExtra(Const.EXTRA_GOODS_VOLUME, 0)
                    if (productName != null && productVolume != null && productVolume > 0) {
                        val product: Goods? = goodsSamplesService.getSamples().find { it.name == productName }
                        product?.apply {
                            stock.addRecord(this, productVolume)
                            stockAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
            Const.SHOW_UPDATE_GOODS_REQUEST -> {
                if (resultCode == Activity.RESULT_OK) {
                    val productName = data?.getStringExtra(Const.EXTRA_GOODS_NAME)
                    val productVolume = data?.getIntExtra(Const.EXTRA_GOODS_VOLUME, 0)
                    if (productName != null && productVolume != null && productVolume > 0) {
                        stock.getRecord(productName)?.apply {
                            this.volume = productVolume
                            stockAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
            Const.SHOW_CROWD_REQUEST -> {
            }
        }
        saveData()
        updateEstimations()
    }


    fun onButtonOpenCrowdClicked(view: View) {
        val intent = Intent(this, CrowdActivity::class.java)
        startActivityForResult(intent, Const.SHOW_CROWD_REQUEST)
    }

    fun onButtonAddGoodsClicked(view: View) {
        val intent = Intent(this, GoodsActivity::class.java)
        startActivityForResult(intent, Const.SHOW_GOODS_REQUEST)
    }

    fun onButtonOpenHelpClicked(view: View) {
        val intent = Intent(this, HelpActivity::class.java)
        startActivityForResult(intent, Const.SHOW_HELP_REQUEST)
    }

    fun showUpdateProductView(record: StockRecord) {
        val intent = Intent(this, GoodsItemWindow::class.java)
        intent.putExtra(Const.EXTRA_GOODS_NAME, record.goods.name)
        intent.putExtra(Const.EXTRA_GOODS_UNIT, record.goods.unit.repr)
        intent.putExtra(Const.EXTRA_GOODS_VOLUME, record.volume)
        startActivityForResult(intent, Const.SHOW_UPDATE_GOODS_REQUEST)
    }

    @SuppressLint("SetTextI18n")
    fun updateEstimations() {
        val users = appService.getUsers()

        if (stock.isEmpty || users.isEmpty()) {
            drawEstimations()
            return
        }

        val usersCaloriesDailyRate = users.sumByDouble {
            it.HarrisBenedictEquation().metabolicRate()
        }
        val foodEstimationInDays = stock.getFoodEstimation(usersCaloriesDailyRate)
        val waterEstimationInDays = stock.getWaterEstimation(users.size * 2.0)
        val paperEstimationInDays = stock.getToiletPaperEstimation(users.size.toDouble())  // @TODO: fix this

        val max = max(listOf(foodEstimationInDays, waterEstimationInDays, paperEstimationInDays))

        food_progressBar.max = max.toInt()
        water_progressBar.max = max.toInt()
        toiletPaper_progressBar.max = max.toInt()

        food_progressBar.setProgress(foodEstimationInDays.toInt(), true)
        water_progressBar.setProgress(waterEstimationInDays.toInt(), true)
        toiletPaper_progressBar.setProgress(paperEstimationInDays.toInt(), true)

        drawEstimations(foodEstimationInDays, waterEstimationInDays, paperEstimationInDays)
    }

    private fun drawEstimations(
        foodEstimationInDays: Double = 0.0,
        waterEstimationInDays: Double = 0.0,
        paperEstimationInDays: Double = 0.0
    ) {
        val notAvailable = resources.getString(R.string.estimation_not_available)
        food_estimation.text =
            if (foodEstimationInDays > 0) resources.getString(
                R.string.goods_estimation_pattern,
                foodEstimationInDays
            ) else notAvailable
        water_estimation.text =
            if (waterEstimationInDays > 0) resources.getString(
                R.string.goods_estimation_pattern,
                waterEstimationInDays
            ) else notAvailable
        paper_estimation.text =
            if (paperEstimationInDays > 0) resources.getString(
                R.string.goods_estimation_pattern,
                paperEstimationInDays
            ) else notAvailable
    }


    fun onRemoveRecordBtnClicked(record: StockRecord) {
        val builder = AlertDialog.Builder(this)

        with(builder) {
            setTitle(R.string.remove_alert_title)
            setMessage(R.string.remove_alert_confirmation_text)
            setPositiveButton(android.R.string.yes) { _, _ ->
                stock.removeRecord(record)
                stockAdapter.notifyDataSetChanged()
                saveData()
                updateEstimations()
            }
            setNegativeButton(android.R.string.no) { _, _ -> }
            show()
        }
    }

    private fun saveData() {
        appService.setStock(stock)
        appService.setIntroShown(introShown)
    }

}
