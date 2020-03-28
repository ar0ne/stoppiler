package com.ar0ne.stoppiler.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ar0ne.stoppiler.R


class MainActivity : AppCompatActivity() {

    private var introShown = false

    companion object {
        const val SHOW_INTRO_REQUEST = 1
        const val SHOW_CROWD_REQUEST = 4
        const val SHOW_GOODS_REQUEST = 7
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!introShown) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivityForResult(intent, SHOW_INTRO_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SHOW_INTRO_REQUEST) {
            introShown = true
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
}
