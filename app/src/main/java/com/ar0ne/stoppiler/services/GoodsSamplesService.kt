package com.ar0ne.stoppiler.services

import com.ar0ne.stoppiler.R
import com.ar0ne.stoppiler.domain.Goods
import com.ar0ne.stoppiler.domain.GoodsType
import com.ar0ne.stoppiler.domain.Priority
import com.ar0ne.stoppiler.domain.Units
import com.ar0ne.stoppiler.services.GoodsSamples

class GoodsSamplesService: GoodsSamples {
    override fun getSamples(): List<Goods> {
        /*
        @TODO: Request and cache data from external service
         */
        return listOf(
            Goods("Toilet Paper", GoodsType.TOILET_PAPER,1.94, Units.METER, Priority.HIGH, R.string.toilet_paper_icon),

            Goods("Buckwheat Cereal", GoodsType.FOOD, 3.29, Units.GRAM, Priority.MEDIUM),
            Goods("Rice", GoodsType.FOOD, 3.3, Units.GRAM, Priority.MEDIUM),
            Goods("Pasta", GoodsType.FOOD, 3.58, Units.GRAM, Priority.MEDIUM),
            Goods("Bread", GoodsType.FOOD, 2.39, Units.GRAM, Priority.MEDIUM, R.string.bread_icon),
            Goods("Eggs", GoodsType.FOOD, 1.53, Units.GRAM, Priority.LOW, R.string.egg_icon),
            Goods("Orange", GoodsType.FOOD, 0.38, Units.GRAM, Priority.LOW),
            Goods("Banana", GoodsType.FOOD, 0.87, Units.GRAM, Priority.LOW),
            Goods("Apple", GoodsType.FOOD, 0.48, Units.GRAM, Priority.LOW, R.string.apple_icon),
            Goods("Pear", GoodsType.FOOD, 0.41, Units.GRAM, Priority.LOW),
            Goods("Fish", GoodsType.FOOD, 1.28, Units.GRAM, Priority.MEDIUM, R.string.fish_icon),
            Goods("Chicken", GoodsType.FOOD, 1.6, Units.GRAM, Priority.MEDIUM),
            Goods("Pork", GoodsType.FOOD, 4.01, Units.GRAM, Priority.MEDIUM),
            Goods("Beef", GoodsType.FOOD, 1.91, Units.GRAM, Priority.MEDIUM),
            Goods("Cheese", GoodsType.FOOD,3.52, Units.GRAM, Priority.MEDIUM, R.string.cheese_icon),
            Goods("Carrot", GoodsType.FOOD, 0.29, Units.GRAM, Priority.LOW, R.string.carrot_icon),
            Goods("Tomato", GoodsType.FOOD, 0.12, Units.GRAM, Priority.LOW),
            Goods("Salad", GoodsType.FOOD, 0.15, Units.GRAM, Priority.LOW, R.string.seedling_icon),
            Goods("A Pineapple", GoodsType.FOOD, 0.49, Units.GRAM, Priority.LOW),
            Goods("Chocolate", GoodsType.FOOD, 5.52, Units.GRAM, Priority.MEDIUM),
            Goods("Salami", GoodsType.FOOD, 5.36, Units.GRAM, Priority.MEDIUM),

            Goods("Water", GoodsType.WATER, 2.0, Units.LITER, Priority.HIGH, R.string.water_icon),
            Goods("Orange Juice", GoodsType.WATER, 2.0, Units.LITER, Priority.MEDIUM),
            Goods("Milk", GoodsType.WATER, 2.0, Units.LITER, Priority.LOW)
        )

    }
}