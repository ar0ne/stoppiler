package com.ar0ne.stoppiler.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ar0ne.stoppiler.R
import kotlinx.android.synthetic.main.goods_add_product.*

class GoodsAddWindow : PopupWindow() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // @todo: any better solution?
        mainView = goods_add_product_view_with_border
        backgroundView = goods_add_product_background
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goods_add_product)

        intent.getStringExtra(GoodsActivity.EXTRA_GOODS_UNIT)?.apply {
            add_product_unit.text = this
        }
        intent.getStringExtra(GoodsActivity.EXTRA_GOODS_NAME)?.apply {
            goods_product_name.text = this
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
