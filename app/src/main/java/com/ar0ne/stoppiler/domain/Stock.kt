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

    fun getFoodEstimation(): Int {
        return records.filter {
            it.goods.type == GoodsType.FOOD
        }.sumBy { (it.volume * it.goods.dailyRate).toInt() }
    }

    fun getWaterEstimation(): Int {
        return records.filter {
            it.goods.type == GoodsType.WATER
        }.sumBy { (it.volume * it.goods.dailyRate).toInt() }
    }

    fun getToiletPaperEstimation(): Int {
        return records.filter {
            it.goods.type == GoodsType.HOME
        }.sumBy { (it.volume * it.goods.dailyRate).toInt() }
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
//    fun subtract(item: Goods, diff: Double = item.dailyRate) {
//        val currentVolume: Double = goods[item] ?: 0.0
//        val updatedVolume = currentVolume.minus(diff)
//        goods[item] = if (updatedVolume > 0) updatedVolume else 0.0
//    }
//
//    fun left(item: Goods): Double {
//        val volume: Double = goods[item] ?: 0.0
//        return volume / item.dailyRate
//    }
//
//    override fun toString(): String {
//        return goods.map {
//            "${it.key.name}: left for ${left(it.key)} days"
//        }.joinToString()
//    }

}
