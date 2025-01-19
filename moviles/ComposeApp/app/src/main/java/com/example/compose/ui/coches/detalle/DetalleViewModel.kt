package com.example.compose.ui.coches.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.di.IoDispatcher
import com.example.compose.domain.usecases.coches.DelCoche
import com.example.compose.domain.usecases.coches.GetCoche
import com.example.compose.ui.coches.detalle.DetalleContract.*
import com.example.compose.ui.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleViewModel @Inject constructor(
    val getCoche: GetCoche,
    val delCoche: DelCoche,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _uiState: MutableStateFlow<DetalleState> = MutableStateFlow(DetalleState())

    val uiState: StateFlow<DetalleState> = _uiState.asStateFlow()


    fun handleEvent(event: DetalleEvent) {
        when (event) {


            is DetalleEvent.GetCoche ->
                viewModelScope.launch(dispatcher) {
                    _uiState.update{ it.copy(coche = getCoche.invoke(event.matricula))}
                }

            DetalleEvent.DelCoche -> {
                viewModelScope.launch(dispatcher) {
                    _uiState.value.coche?.let { coche ->
                        delCoche(coche)
                        _uiState.update{it.copy(
                            uiEvent = listOf(UiEvent.Navigate("listado"),UiEvent.ShowSnackbar("coche eliminado")))}
                    }
                }
            }

            DetalleEvent.UiEventDone ->{
                _uiState.update {it.copy(uiEvent = emptyList())}
            }
        }
    }


}