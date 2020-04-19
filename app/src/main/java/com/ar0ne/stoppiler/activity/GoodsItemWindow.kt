package com.ar0ne.stoppiler.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.ar0ne.stoppiler.Const.EXTRA_GOODS_NAME
import com.ar0ne.stoppiler.Const.EXTRA_GOODS_UNIT
import com.ar0ne.stoppiler.Const.EXTRA_GOODS_VOLUME
import com.ar0ne.stoppiler.R
import kotlinx.android.synthetic.main.goods_item_popup.*

class GoodsItemWindow : PopupWindow() {

    override fun setupViews() {
        mainView = goods_add_product_view_with_border
        backgroundView = goods_add_product_background
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.goods_item_popup)
        super.onCreate(savedInstanceState)

        intent.getStringExtra(EXTRA_GOODS_UNIT)?.let {
            add_product_unit.text = it
        }
        intent.getStringExtra(EXTRA_GOODS_NAME)?.let {
            goods_product_name.text = it
        }
        intent.getIntExtra(EXTRA_GOODS_VOLUME, 0).let {
            if (it > 0) {
                add_product_volume.setText(it.toString())
            }
        }

        add_product_btn_save.setEnabled(isBtnSaveEnabled())

        add_product_volume.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                add_product_btn_save.setEnabled(isBtnSaveEnabled())
            }
        })
    }

    fun onAddProductClicked(view: View) {
        val result = Intent()
        val volume = add_product_volume.text.toString().toInt()
        val product = goods_product_name.text.toString()
        result.putExtra(EXTRA_GOODS_VOLUME, volume)
        result.putExtra(EXTRA_GOODS_NAME, product)
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    fun onCancelAddProductClicked(view: View) {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun isBtnSaveEnabled(): Boolean {
        return add_product_volume.text.isNotBlank()
    }

}
