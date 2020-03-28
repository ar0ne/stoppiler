package com.ar0ne.stoppiler.domain

enum class GoodsType {
    FOOD,
    WATER,
    HOME,
    // @TODO: add rest types
}

enum class Priority {
    LOW, MEDIUM, HIGH
}

enum class Units {
    METER,
    KILOGRAM,
    LITER,
}


data class Goods(
    val name: String,
    val type: GoodsType,
    val dailyRate: Double,
    val unit: Units = Units.KILOGRAM,
    val priority: Priority = Priority.LOW
)
