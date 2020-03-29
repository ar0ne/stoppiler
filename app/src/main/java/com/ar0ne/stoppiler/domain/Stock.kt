package com.ar0ne.stoppiler.domain

import java.util.*

class Stock(private var records: MutableList<StockRecord> = mutableListOf()) {

    companion object {
        val now = { Calendar.getInstance().time }
    }

    fun addRecord(goods: Goods, volume: Int) {
        val newRecord = StockRecord(goods, volume, now())
        records.add(newRecord)
    }

    fun size(): Int = records.size

    fun getRecord(index: Int): StockRecord {
        return records[index]
    }

    fun getFoodEstimation(dailyRate: Double): Double {
        return records.filter {
            it.goods.type == GoodsType.FOOD
        }.sumByDouble { (it.volume * it.goods.calories) }
            .div(dailyRate)
    }

    fun getWaterEstimation(dailyRate: Double): Double {
        return records.filter {
            it.goods.type == GoodsType.WATER
        }.sumByDouble { (it.volume * it.goods.calories) }
            .div(dailyRate)
    }

    fun getToiletPaperEstimation(dailyRate: Double): Double {
        return records.filter {
            it.goods.type == GoodsType.TOILET_PAPER
        }.sumByDouble { (it.volume * it.goods.calories) }
            .div(dailyRate)
    }

    fun getRecord(goodsName: String): StockRecord? {
        return records.find {
            it.goods.name == goodsName
        }
    }

    fun removeRecord(record: StockRecord) {
        records.remove(record)
    }

//    fun addGoods(item: Goods, volume: Double) {
//        val currentVolume: Double = goods[item] ?: 0.0
//        goods[item] = currentVolume.plus(volume)
//    }
//
//    fun subtract(item: Goods, diff: Double = item.calories) {
//        val currentVolume: Double = goods[item] ?: 0.0
//        val updatedVolume = currentVolume.minus(diff)
//        goods[item] = if (updatedVolume > 0) updatedVolume else 0.0
//    }
//
//    fun left(item: Goods): Double {
//        val volume: Double = goods[item] ?: 0.0
//        return volume / item.calories
//    }
//
//    override fun toString(): String {
//        return goods.map {
//            "${it.key.name}: left for ${left(it.key)} days"
//        }.joinToString()
//    }

}
