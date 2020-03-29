package com.ar0ne.stoppiler.domain

import java.util.*

class StockRecord (val goods: Goods, var volume: Int, val timestamp: Date = Stock.now()) {

}
