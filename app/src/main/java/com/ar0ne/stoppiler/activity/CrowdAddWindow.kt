package com.ar0ne.stoppiler.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.domain.Sex
import kotlinx.android.synthetic.main.crowd_add_person.*

class CrowdAddWindow : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    private var darkStatusBar = true

    companion object {
        const val EXTRA_PERSON_NAME = "person_name"
        const val EXTRA_PERSON_AGE = "person_age"
        const val EXTRA_PERSON_SEX = "person_sex"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.crowd_add_person)

        crowd_person_sex_switch?.setOnCheckedChangeListener(this)

        if (darkStatusBar) {
            this.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        this.window.statusBarColor = Color.TRANSPARENT
        setWindowFlag(this, false)

        // Fade animation for the background of Popup Window
        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            crowd_add_person_background.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()


        // Fade animation for the Popup Window
        crowd_add_person_view_with_border.alpha = 0f
        crowd_add_person_view_with_border.animate().alpha(1f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

    }

    private fun setWindowFlag(activity: Activity, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        } else {
            winParams.flags =
                winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        }
        win.attributes = winParams
    }

    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            crowd_add_person_background.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

        // Fade animation for the Popup Window when you press the back button
        crowd_add_person_view_with_border.animate().alpha(0f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }


    fun onCancelAddCrowdClicked(view: View) {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    fun onSavePersonClicked(view: View) {
        val result = Intent()
        val sex = if (crowd_person_sex_switch.isChecked) Sex.FEMALE else Sex.MALE
        val name = crowd_person_name.text.toString()
        val age = crowd_person_age.text.toString()
        result.putExtra(EXTRA_PERSON_NAME, name)
        result.putExtra(EXTRA_PERSON_AGE, age)
        result.putExtra(EXTRA_PERSON_SEX, sex)
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            crowd_person_sex_switch.setText(R.string.crowd_person_female)
        } else {
            crowd_person_sex_switch.setText(R.string.crowd_person_male)
        }
    }
}