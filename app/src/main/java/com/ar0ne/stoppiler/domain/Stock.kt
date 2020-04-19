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

    val size: Int
        get() = records.size

    val isEmpty: Boolean
        get() = size == 0

    fun getRecord(index: Int): StockRecord = records[index]

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
            .div(dailyRate)
    }

    fun getToiletPaperEstimation(dailyRate: Double): Double {
        val toiletPaper = records.filter {
            it.goods.type == GoodsType.TOILET_PAPER
        }
        if (toiletPaper.isEmpty()) {
            return 0.0
        }
        return toiletPaper.first().volume / (dailyRate * toiletPaper.first().goods.calories)
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
