package com.ar0ne.stoppiler.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ar0ne.stoppiler.R

class IntroActivity : AppCompatActivity() {

    val SHOW_CROWD_LIST_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }


    fun onButtonNextClicked(view: View) {
        val intent = Intent(this, CrowdActivity::class.java)
        startActivityForResult(intent, SHOW_CROWD_LIST_REQUEST)

        setResult(Activity.RESULT_OK)
        finish()
    }
}
