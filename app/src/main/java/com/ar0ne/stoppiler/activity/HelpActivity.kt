package com.ar0ne.stoppiler.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ar0ne.stoppiler.R


class HelpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }

    fun onButtonBackClicked(view: View) {
        finish()
    }

    fun onButtonGithubClicked(view: View) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_link)))
        startActivity(browserIntent)
    }
}
