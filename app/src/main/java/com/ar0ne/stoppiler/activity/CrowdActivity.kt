package com.ar0ne.stoppiler.activity

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

    private var users: List<User> = listOf(
        User("John", 23, Sex.MALE),
        User("Anna", 22, Sex.FEMALE),
        User("Tommy", 9, Sex.MALE)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crowd)

        val crowdAdapter = CrowdAdapter(
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
        }
    }


}
