package com.leoapps.progress.presentation.model

sealed interface BoilProgressUiEvent {
    object NavigateBack : BoilProgressUiEvent
    object RequestNotificationsPermission : BoilProgressUiEvent
    object OpenNotificationsSettings : BoilProgressUiEvent

    data class UpdateTimer(
        val progress: Float,
        val progressText: String,
    ) : BoilProgressUiEvent
}