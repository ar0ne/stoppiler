package com.ar0ne.stoppiler.domain

class Stock(private val goods: MutableMap<Goods, Double> = mutableMapOf()) {

    fun addGoods(item: Goods, volume: Double) {
        val currentVolume: Double = goods[item] ?: 0.0
        goods[item] = currentVolume.plus(volume)
    }

    fun subtract(item: Goods, diff: Double = item.dailyRate) {
        val currentVolume: Double = goods[item] ?: 0.0
        val updatedVolume = currentVolume.minus(diff)
        goods[item] = if (updatedVolume > 0) updatedVolume else 0.0
    }

    fun left(item: Goods): Double {
        val volume: Double = goods[item] ?: 0.0
        return volume / item.dailyRate
    }

    override fun toString(): String {
        return goods.map {
            "${it.key.name}: left for ${left(it.key)} days"
        }.joinToString()
    }

}
