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
        }.sumByDouble { it.volume * it.goods.calories }
            .div(dailyRate)
    }

    fun getWaterEstimation(dailyRate: Double): Double {
        return records.filter {
            it.goods.type == GoodsType.WATER
        }.sumBy { it.volume}
            .div(dailyRate * 2.0)
    }

    fun getToiletPaperEstimation(dailyRate: Double): Double {
        val records = records.filter {
            it.goods.type == GoodsType.TOILET_PAPER
        }
        if (records.isEmpty()) {
            return 0.0
        }
        return records.first().volume / (dailyRate * records.first().goods.calories)
    }

    fun getRecord(goodsName: String): StockRecord? {
        return records.find {
            it.goods.name == goodsName
        }
    }

    fun removeRecord(record: StockRecord) {
        records.remove(record)
    }

    fun getGoodsNames(): List<String> {
        return records.map { it.goods.name }
    }

}
