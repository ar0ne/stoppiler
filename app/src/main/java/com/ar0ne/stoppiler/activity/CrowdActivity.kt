package com.ar0ne.stoppiler.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.adapter.CrowdAdapter
import com.ar0ne.stoppiler.domain.Sex
import com.ar0ne.stoppiler.domain.User
import kotlinx.android.synthetic.main.activity_crowd.*

class CrowdActivity : AppCompatActivity() {

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

}
