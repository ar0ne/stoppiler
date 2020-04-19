package com.ar0ne.stoppiler.storage

import android.content.Context
import com.ar0ne.stoppiler.domain.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class UserDataStorage(context: Context) : DataStorage {

    private object Const {
        const val STORAGE = "com.ar0ne.stoppiler.STORAGE"
        const val USERS = "com.ar0ne.stoppiler.USERS"
    }

    val sPref = context.getSharedPreferences(Const.STORAGE, Context.MODE_PRIVATE)

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

    inline fun <reified T> parseArray(json: String, typeToken: Type): T {
        val gson = GsonBuilder().create()
        return gson.fromJson<T>(json, typeToken)
    }

}