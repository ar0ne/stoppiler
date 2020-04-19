package com.ar0ne.stoppiler.storage

import android.content.Context
import android.content.SharedPreferences
import com.ar0ne.stoppiler.domain.Stock
import com.ar0ne.stoppiler.domain.User
import com.ar0ne.stoppiler.storage.DataStorage.Companion.parseArray
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppDataStorage(context: Context) : DataStorage {

    private object Const {
        const val STORAGE = "com.ar0ne.stoppiler.STORAGE"
        const val USERS = "com.ar0ne.stoppiler.USERS"
        const val INTRO_SHOWN = "com.ar0ne.stoppiler.INTRO_SHOWN"
        const val STOCK = "com.ar0ne.stoppiler.STOCK"
    }

    private val sPref: SharedPreferences =
        context.getSharedPreferences(Const.STORAGE, Context.MODE_PRIVATE)

    override fun getUsers(): List<User> {
        return sPref.getString(Const.USERS, null)?.let {
            val type = object : TypeToken<MutableList<User>>() {}.type
            return parseArray(json = it, typeToken = type)
        } ?: listOf()
    }

    override fun setUsers(users: List<User>) {
        Gson().toJson(users)?.let {
            with(sPref.edit()) {
                putString(Const.USERS, it)
                commit()
            }
        }
    }

    override fun isIntroShown(): Boolean {
        return sPref.getBoolean(Const.INTRO_SHOWN, false)
    }

    override fun setIntroShown(yes: Boolean) {
        with(sPref.edit()) {
            putBoolean(Const.INTRO_SHOWN, yes)
            commit()
        }
    }

    override fun getStock(): Stock {
        return sPref.getString(Const.STOCK, null)?.let {
            return Gson().fromJson(it, Stock::class.java)
        } ?: Stock()
    }

    override fun setStock(stock: Stock) {
        Gson().toJson(stock)?.let {
            with(sPref.edit()) {
                putString(Const.STOCK, it)
                commit()
            }
        }
    }

}