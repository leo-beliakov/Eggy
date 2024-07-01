package com.leoapps.eggy.setup.domain

import com.leoapps.eggy.setup.domain.model.EggBoilingType
import com.leoapps.eggy.setup.domain.model.EggSize
import com.leoapps.eggy.setup.domain.model.EggTemperature
import javax.inject.Inject

class CalculateBoilingTimeUseCase @Inject constructor() {

    operator fun invoke(
        temperature: EggTemperature?,
        size: EggSize?,
        type: EggBoilingType?,
    ): Long {
        return if (temperature != null && size != null && type != null) {
            calculateBoilingTime(temperature, size, type)
        } else {
            0L
        }
    }

    private fun calculateBoilingTime(
        temperature: EggTemperature,
        size: EggSize,
        type: EggBoilingType,
    ): Long {
        // Define the base boiling times in seconds
        val baseTime = when (type) {
            EggBoilingType.SOFT -> 240L
            EggBoilingType.MEDIUM -> 360L
            EggBoilingType.HARD -> 480L
        }

        // Adjust the time based on the egg size
        val sizeAdjustment = when (size) {
            EggSize.SMALL -> -30L
            EggSize.MEDIUM -> 0L
            EggSize.LARGE -> 30L
        }

        // Adjust the time based on the egg temperature
        val temperatureAdjustment = when (temperature) {
            EggTemperature.ROOM -> 0L
            EggTemperature.FRIDGE -> 60L
        }

        val totalTimeInSeconds = baseTime + sizeAdjustment + temperatureAdjustment
        return totalTimeInSeconds * 1000
    }
}