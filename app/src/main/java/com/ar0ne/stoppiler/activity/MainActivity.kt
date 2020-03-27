package com.ar0ne.stoppiler.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ar0ne.stoppiler.R


class MainActivity : AppCompatActivity() {

    private var intoShown = false
    private val SHOW_INTRO_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!intoShown) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivityForResult(intent, SHOW_INTRO_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SHOW_INTRO_REQUEST) {
            intoShown = true
        }
    }

}
