package com.leoapps.eggy.progress.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.leoapps.eggy.setup.presentation.model.BoilProgressUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BoilProgressViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val args = savedStateHandle.toRoute<BoilProgressScreenDestination>()

    private val _state = MutableStateFlow(BoilProgressUiState())
    val state = _state.asStateFlow()

    init {
        Log.d("MyTag", "time = ${args.calculatedTime}")
    }

    fun onStartClicked() {

    }

    fun onStopClicked() {

    }

}