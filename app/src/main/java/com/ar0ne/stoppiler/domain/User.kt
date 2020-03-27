package com.ar0ne.stoppiler.domain

enum class Sex {
    MALE, FEMALE
}

data class User(
    val name: String,
    val age: Int,
    val sex: Sex
    // weight/height ?
)