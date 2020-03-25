package com.ar0ne.stoppiler.domain

class Stock(val goods: MutableMap<Goods, Float> = mutableMapOf()) {

    fun updateGoods(item: Goods, volume: Float) {
        val currentVolume: Float = goods[item] ?: 0f
        goods[item] = currentVolume.plus(volume)
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
    myStock.updateGoods(toiletPaper, 120f)
    myStock.updateGoods(buckwheat, 3f)
    myStock.updateGoods(juice, 4f)

    println(myStock.left(toiletPaper))
    println(myStock.left(buckwheat))
    println(myStock.left(juice))
}