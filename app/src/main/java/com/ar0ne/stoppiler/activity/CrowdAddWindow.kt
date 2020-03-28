package com.ar0ne.stoppiler.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.domain.Sex
import kotlinx.android.synthetic.main.crowd_add_person.*

class CrowdAddWindow : PopupWindow(), CompoundButton.OnCheckedChangeListener {

    companion object {
        const val EXTRA_PERSON_NAME = "person_name"
        const val EXTRA_PERSON_AGE = "person_age"
        const val EXTRA_PERSON_SEX = "person_sex"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // @todo: any better solution?
        mainView = crowd_add_person_view_with_border
        backgroundView = crowd_add_person_background

        super.onCreate(savedInstanceState)
        setContentView(R.layout.crowd_add_person)

        crowd_person_sex_switch?.setOnCheckedChangeListener(this)
    }

    fun onCancelAddPersonClicked(view: View) {
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
            crowd_person_sex_switch.setText(R.string.female)
        } else {
            crowd_person_sex_switch.setText(R.string.male)
        }
    }
}