package com.ar0ne.stoppiler.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        const val EXTRA_PERSON_WEIGHT = "person_weight"
        const val EXTRA_PERSON_HEIGHT = "person_height"
    }

    override fun initViews(main: View?, background: View?) {
        mainView = crowd_add_person_view_with_border
        backgroundView = crowd_add_person_background
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crowd_add_person)

        crowd_person_sex_switch?.setOnCheckedChangeListener(this)

        crowd_person_save.setEnabled(isSaveButtonEnabled())

        crowd_person_name.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crowd_person_save.setEnabled(isSaveButtonEnabled())
            }
        })

        crowd_person_age.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crowd_person_save.setEnabled(isSaveButtonEnabled())
            }
        })

        crowd_person_weight.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crowd_person_save.setEnabled(isSaveButtonEnabled())
            }
        })

        crowd_person_height.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                crowd_person_save.setEnabled(isSaveButtonEnabled())
            }
        })

    }

    fun onCancelAddPersonClicked(view: View) {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    fun onSavePersonClicked(view: View) {
        val result = Intent()
        val sex = if (crowd_person_sex_switch.isChecked) Sex.FEMALE else Sex.MALE
        val name = crowd_person_name.text.toString()
        val age = crowd_person_age.text.toString().toInt()
        val weight = crowd_person_weight.text.toString().toInt()
        val height = crowd_person_height.text.toString().toInt()
        result.putExtra(EXTRA_PERSON_NAME, name)
        result.putExtra(EXTRA_PERSON_AGE, age)
        result.putExtra(EXTRA_PERSON_SEX, sex.toString())
        result.putExtra(EXTRA_PERSON_WEIGHT, weight)
        result.putExtra(EXTRA_PERSON_HEIGHT, height)
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

    private fun isSaveButtonEnabled(): Boolean {
        return crowd_person_name.text.isNotEmpty() &&
                crowd_person_age.text.isNotEmpty() &&
                crowd_person_weight.text.isNotEmpty() &&
                crowd_person_height.text.isNotEmpty()
    }
}