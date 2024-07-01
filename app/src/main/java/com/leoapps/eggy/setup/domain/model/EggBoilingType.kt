package com.leoapps.eggy.setup.domain.model

enum class EggBoilingType {
    SOFT,
    MEDIUM,
    HARD;

    companion object {
        fun fromString(s: String) = entries.firstOrNull { it.name == s }
    }
}