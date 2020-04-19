package com.ar0ne.stoppiler.storage

import com.ar0ne.stoppiler.domain.Stock
import com.ar0ne.stoppiler.domain.User
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

// Combined all repositories in one place for simplicity
interface DataStorage {

    companion object {
        inline fun <reified T> parseArray(json: String, typeToken: Type): T {
            val gson = GsonBuilder().create()
            return gson.fromJson<T>(json, typeToken)
        }
    }

    fun getUsers(): List<User>
    fun setUsers(users: List<User>)

    fun isIntroShown(): Boolean
    fun setIntroShown(yes: Boolean)

    fun getStock(): Stock
    fun setStock(stock: Stock)
}