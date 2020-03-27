package com.ar0ne.stoppiler

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
    }


    fun onButtonNextClicked(view: View) {
        setResult(Activity.RESULT_OK)
        finish()
    }
}
