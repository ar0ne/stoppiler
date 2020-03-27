package com.ar0ne.stoppiler.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.adapter.CrowdAdapter
import com.ar0ne.stoppiler.domain.Sex
import com.ar0ne.stoppiler.domain.User
import kotlinx.android.synthetic.main.activity_crowd.*

class CrowdActivity : AppCompatActivity() {

    val ADD_PERSON_REQUEST = 3

    private var crowdAdapter: CrowdAdapter? = null

    private var users: MutableList<User> = mutableListOf(
        User("John", 23, Sex.MALE),
        User("Anna", 22, Sex.FEMALE),
        User("Tommy", 9, Sex.MALE)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crowd)

        crowdAdapter = CrowdAdapter(
            users,
            object : CrowdAdapter.Callback {
                override fun onItemClicked(user: User) {
                }
            })

        crowd_recycler_view.adapter = crowdAdapter
    }

    fun onAddCrowdClicked(view: View) {
        val intent = Intent(this, CrowdAddWindow::class.java)
        startActivityForResult(intent, ADD_PERSON_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ADD_PERSON_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val name: String? = data?.getStringExtra(CrowdAddWindow.EXTRA_PERSON_NAME)
                val age: Int? = data?.getStringExtra(CrowdAddWindow.EXTRA_PERSON_AGE)?.toInt()
                var sex: Sex = Sex.MALE
                data?.getStringExtra(CrowdAddWindow.EXTRA_PERSON_SEX)?.let {
                    sex = if (Sex.valueOf(it) == Sex.MALE) Sex.MALE else Sex.FEMALE
                }
                if (name != null && age != null) {
                    val user = User(name, age, sex)
                    users.add(user)
                    crowdAdapter?.notifyDataSetChanged()
                }
            }
        }
    }


}
