package com.ar0ne.stoppiler.storage

import com.ar0ne.stoppiler.domain.User

interface DataStorage {

    fun getUsers(): List<User>

    fun setUsers(users: List<User>)
}