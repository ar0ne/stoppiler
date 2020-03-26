package com.ar0ne.stoppiler.domain

class Stock(private val goods: MutableMap<Goods, Float> = mutableMapOf()) {

    fun addGoods(item: Goods, volume: Float) {
        val currentVolume: Float = goods[item] ?: 0f
        goods[item] = currentVolume.plus(volume)
    }

    fun subtract(item: Goods, diff: Float = item.dailyRate) {
        val currentVolume: Float = goods[item] ?: 0f
        val updatedVolume = currentVolume.minus(diff)
        goods[item] = if (updatedVolume > 0) updatedVolume else 0f
    }

    fun left(item: Goods): Float {
        val volume: Float = goods[item] ?: 0f
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
        type = GoodsType.HOME, name = "Toilet Paper", dailyRate = 2f, unit = Units.METER
    )
    val buckwheat = Goods(
        type = GoodsType.FOOD, name = "Buckwheat grain", dailyRate = .1f, unit = Units.KILOGRAM
    )
    val juice = Goods(
        type = GoodsType.WATER, name = "Orange Juice", dailyRate = .5f, unit = Units.LITER
    )
    val myStock = Stock()
    myStock.addGoods(toiletPaper, 120f)
    myStock.addGoods(buckwheat, 3f)
    myStock.addGoods(juice, 4f)

//    println(myStock.left(toiletPaper))
    println(myStock.left(buckwheat))
//    println(myStock.left(juice))

    myStock.subtract(buckwheat, 0.3f)

    println(myStock.left(buckwheat))


}