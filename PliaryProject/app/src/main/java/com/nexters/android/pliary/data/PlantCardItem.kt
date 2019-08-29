package com.nexters.android.pliary.data

import com.nexters.android.pliary.db.entity.Plant
import com.nexters.android.pliary.view.util.getWateredDDay
import com.nexters.android.pliary.view.util.isWateredDayPast


internal sealed class PlantCard(val type : Int, val listItemId: String) {
    companion object {
        const val EMPTY_CARD = 0
        const val PLANT_CARD = 1
    }

    data class PlantCardItem(val plant: Plant) : PlantCard(PLANT_CARD, plant.toString())

    class EmptyCard : PlantCard(EMPTY_CARD, this.toString())
}

data class PlantCardUI(
    var isDayPast: Boolean,
    var photoUrl: String?,
    var nickName: String,
    var waterDday: String
)

internal fun Plant.toUIData() = PlantCardUI(
    isDayPast = isWateredDayPast(this.lastWateredDate, this.waterTerm ?: 1),
    photoUrl = when(isWateredDayPast(this.lastWateredDate, this.waterTerm ?: 1)) {
        true -> this.species?.nagUrl
        false -> this.species?.posUrl
    },
    nickName = this.nickName ?: "",
    waterDday = getWateredDDay(this.lastWateredDate, this.waterTerm ?: 1)
)