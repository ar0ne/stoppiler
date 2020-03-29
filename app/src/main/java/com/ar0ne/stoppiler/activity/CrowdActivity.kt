package com.ar0ne.stoppiler.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.ar0ne.stoppiler.adapter.CrowdAdapter
import com.ar0ne.stoppiler.domain.Sex
import com.ar0ne.stoppiler.domain.User
import kotlinx.android.synthetic.main.activity_crowd.*
import com.ar0ne.stoppiler.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class CrowdActivity : AppCompatActivity() {

    companion object {
        const val ADD_PERSON_REQUEST = 3
        const val USERS_KEY = "users"

        inline fun <reified T> parseArray(json: String, typeToken: Type): T {
            val gson = GsonBuilder().create()
            return gson.fromJson<T>(json, typeToken)
        }
    }

    private var crowdAdapter: CrowdAdapter? = null

    private var users: MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crowd)

        loadData()

        crowdAdapter = CrowdAdapter(
            this,
            users,
            object : CrowdAdapter.Callback {
                override fun onItemClicked(user: User) = showDeletePersonDialog(user)
            })

        crowd_recycler_view.adapter = crowdAdapter

        crowd_next.setEnabled(isNextButtonEnabled())
    }

    override fun onStop() {
        super.onStop()
        saveData()
    }


    fun onAddPersonClicked(view: View) {
        val intent = Intent(this, CrowdAddWindow::class.java)
        startActivityForResult(intent, ADD_PERSON_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_PERSON_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val name: String = data?.getStringExtra(CrowdAddWindow.EXTRA_PERSON_NAME) ?: return
                val extraSex = data.getStringExtra(CrowdAddWindow.EXTRA_PERSON_SEX) ?: return

                val extraAge = data.getIntExtra(CrowdAddWindow.EXTRA_PERSON_AGE, 0)
                val extraWeight = data.getIntExtra(CrowdAddWindow.EXTRA_PERSON_WEIGHT, 0)
                val extraHeight = data.getIntExtra(CrowdAddWindow.EXTRA_PERSON_HEIGHT, 0)

                // @TODO: default value based on sex ?
                val weight: Int = if (extraWeight > 0) extraWeight else 70
                val height: Int =  if (extraHeight in 1..229) extraHeight else 170
                val age: Int = if (extraAge in 1..99) extraAge else 30
                val sex: Sex = if (Sex.valueOf(extraSex) == Sex.MALE) Sex.MALE else Sex.FEMALE

                val user = User(name, age, sex, weight, height)

                users.add(user)
                crowdAdapter?.notifyDataSetChanged()
                crowd_next.setEnabled(isNextButtonEnabled())
                saveData()
            }
        }
    }

    private fun showDeletePersonDialog(user: User) {
        val builder = AlertDialog.Builder(this)

        with(builder) {
            setTitle(R.string.remove_alert_title)
            setMessage(R.string.remove_alert_confirmation_text)
            setPositiveButton(android.R.string.yes) { _, _ ->
                users.remove(user)
                crowdAdapter?.notifyDataSetChanged()
                crowd_next.setEnabled(isNextButtonEnabled())
            }
            setNegativeButton(android.R.string.no) { dialog, which -> }
            show()
        }
    }

    fun onButtonNextClicked(view: View) {
        val result = Intent()
        setResult(MainActivity.SHOW_INTRO_REQUEST, result)
        finish()
    }

    private fun loadData() {
        val sPref = getSharedPreferences("stop",Context.MODE_PRIVATE)
        val usersJson: String? = sPref?.getString(USERS_KEY, null)
        usersJson?.let {
            val type = object : TypeToken<MutableList<User>>() {}.type
            val result: MutableList<User> =
                parseArray(json = it, typeToken = type)
            users = result
        }
    }

    private fun saveData() {
        val sPref = getSharedPreferences("stop",Context.MODE_PRIVATE)
        val usersJson = Gson().toJson(users)
        usersJson?.let {
            with(sPref!!.edit()) {
                putString(USERS_KEY, it)
                commit()
            }
        }
    }

    private fun isNextButtonEnabled(): Boolean = users.size > 0
}
