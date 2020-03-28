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


fun main() {
    val toiletPaper = Goods(
        type = GoodsType.HOME, name = "Toilet Paper", dailyRate = 2.0, unit = Units.METER
    )
    val buckwheat = Goods(
        type = GoodsType.FOOD, name = "Buckwheat grain", dailyRate = .1, unit = Units.KILOGRAM
    )
    val juice = Goods(
        type = GoodsType.WATER, name = "Orange Juice", dailyRate = .5, unit = Units.LITER
    )
    val myStock = Stock()
    myStock.addGoods(toiletPaper, 120.0)
    myStock.addGoods(buckwheat, 3.0)
    myStock.addGoods(juice, 4.0)

//    println(myStock.left(toiletPaper))
    println(myStock.left(buckwheat))
//    println(myStock.left(juice))

    myStock.subtract(buckwheat, 0.3)

    println(myStock.left(buckwheat))


}