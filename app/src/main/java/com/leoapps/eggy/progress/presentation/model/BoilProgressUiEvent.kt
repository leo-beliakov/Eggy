package com.leoapps.eggy.progress.presentation.model

sealed interface BoilProgressUiEvent {
    object NavigateBack : BoilProgressUiEvent

    data class RequestPermission(
        val permission: String
    ) : BoilProgressUiEvent

    data class UpdateTimer(
        val progress: Float,
        val progressText: String,
    ) : BoilProgressUiEvent
}