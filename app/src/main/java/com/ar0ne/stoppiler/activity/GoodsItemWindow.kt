package com.ar0ne.stoppiler.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.ar0ne.stoppiler.R
import kotlinx.android.synthetic.main.goods_item_popup.*

class GoodsItemWindow : PopupWindow() {

    override fun initViews(main: View?, background: View?) {
        mainView = goods_add_product_view_with_border
        backgroundView = goods_add_product_background
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goods_item_popup)

        intent.getStringExtra(GoodsActivity.EXTRA_GOODS_UNIT)?.apply {
            add_product_unit.text = this
        }
        intent.getStringExtra(GoodsActivity.EXTRA_GOODS_NAME)?.apply {
            goods_product_name.text = this
        }
        intent.getIntExtra(GoodsActivity.EXTRA_GOODS_VOLUME, 0).apply {
            if (this > 0) {
                add_product_volume?.setText(this.toString())
            }
        }
    }

    fun onAddProductClicked(view: View) {
        val result = Intent()
        val volume = add_product_volume.text.toString().toInt()
        val product = goods_product_name.text.toString()
        result.putExtra(GoodsActivity.EXTRA_GOODS_VOLUME, volume)
        result.putExtra(GoodsActivity.EXTRA_GOODS_NAME, product)
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    fun onCancelAddProductClicked(view: View) {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

}