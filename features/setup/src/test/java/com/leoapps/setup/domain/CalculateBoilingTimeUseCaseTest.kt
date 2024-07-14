package com.leoapps.setup.domain

import com.leoapps.base.egg.domain.model.EggBoilingType
import com.leoapps.base.egg.domain.model.EggSize
import com.leoapps.base.egg.domain.model.EggTemperature
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateBoilingTimeUseCaseTest {

    private val calculateBoilingTime = CalculateBoilingTimeUseCase()

    @Test
    fun testBoilingTimeSoftRoomTemperatureSmall() {
        val result = calculateBoilingTime(
            EggTemperature.ROOM,
            EggSize.SMALL,
            EggBoilingType.SOFT
        )
        assertEquals(210000, result)
    }

    @Test
    fun testBoilingTimeMediumRoomTemperatureMedium() {
        val result = calculateBoilingTime(
            EggTemperature.ROOM,
            EggSize.MEDIUM,
            EggBoilingType.MEDIUM
        )
        assertEquals(360000, result)
    }

    @Test
    fun testBoilingTimeHardRoomTemperatureLarge() {
        val result = calculateBoilingTime(
            EggTemperature.ROOM,
            EggSize.LARGE,
            EggBoilingType.HARD
        )
        assertEquals(510000, result)
    }

    @Test
    fun testBoilingTimeSoftFridgeTemperatureSmall() {
        val result = calculateBoilingTime(
            EggTemperature.FRIDGE,
            EggSize.SMALL,
            EggBoilingType.SOFT
        )
        assertEquals(270000, result)
    }

    @Test
    fun testBoilingTimeMediumFridgeTemperatureMedium() {
        val result = calculateBoilingTime(
            EggTemperature.FRIDGE,
            EggSize.MEDIUM,
            EggBoilingType.MEDIUM
        )
        assertEquals(420000, result)
    }

    @Test
    fun testBoilingTimeHardFridgeTemperatureLarge() {
        val result = calculateBoilingTime(
            EggTemperature.FRIDGE,
            EggSize.LARGE,
            EggBoilingType.HARD
        )
        assertEquals(570000, result)
    }

    @Test
    fun testNullInputs() {
        val result = calculateBoilingTime(
            null,
            EggSize.MEDIUM,
            EggBoilingType.SOFT
        )
        assertEquals(0L, result)

        val result2 = calculateBoilingTime(
            EggTemperature.ROOM,
            null,
            EggBoilingType.SOFT
        )
        assertEquals(0L, result2)

        val result3 = calculateBoilingTime(
            EggTemperature.ROOM,
            EggSize.MEDIUM,
            null
        )
        assertEquals(0L, result3)

        val result4 = calculateBoilingTime(
            null,
            null,
            null
        )
        assertEquals(0L, result4)
    }
}
