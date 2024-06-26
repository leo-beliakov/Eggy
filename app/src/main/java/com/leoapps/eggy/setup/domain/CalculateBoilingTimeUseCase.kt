package com.leoapps.eggy.setup.domain

import com.leoapps.eggy.setup.domain.model.EggBoilingType
import com.leoapps.eggy.setup.domain.model.EggSize
import com.leoapps.eggy.setup.domain.model.EggTemperature
import javax.inject.Inject
import kotlin.random.Random

class CalculateBoilingTimeUseCase @Inject constructor() {

    operator fun invoke(
        temperature: EggTemperature?,
        size: EggSize?,
        type: EggBoilingType?,
    ): Long {
        return if (temperature != null && size != null && type != null) {
            Random.nextLong(
                from = 60 * 1000L,
                until = 6 * 60 * 1000L
            )
        } else {
            0L
        }
    }
}