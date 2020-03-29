package com.ar0ne.stoppiler.domain

enum class Sex {
    MALE, FEMALE
}

enum class ActivityLevel(val value: Double) {
    LOW(1.2),
    MIDDLE(1.55),
    HIGH(1.725)
}

data class User(
    val name: String,
    val age: Int,
    val sex: Sex,
    val weight: Int,
    val height: Int
) {

    inner class HarrisBenedictEquation {
        fun baseMetabolicRate(): Double {
            if (sex == Sex.MALE) {
                return 88.36 + 13.4 * weight + 4.8 * height - 5.7 * age
            }
            return 447.6 + 9.2 * weight + 3.1 * height - 4.3 * age
        }

        fun metabolicRate(activity: ActivityLevel = ActivityLevel.MIDDLE): Double {
            return activity.value * baseMetabolicRate()
        }
    }

}

