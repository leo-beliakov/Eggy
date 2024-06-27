package com.leoapps.eggy.progress.presentation

import androidx.lifecycle.ViewModel
import com.leoapps.eggy.setup.presentation.model.BoilProgressUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BoilProgressViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(BoilProgressUiState())
    val state = _state.asStateFlow()

    fun onStartClicked() {

    }

    fun onStopClicked() {

    }

}