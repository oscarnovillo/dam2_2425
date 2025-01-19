package com.example.compose.ui.sumar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.R

import com.example.primerxmlmvvm.common.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SumaViewModel @Inject constructor(
    private val stringProvider: StringProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SumaState(0,1,null))
    val uiState: StateFlow<SumaState> get() = _uiState


    // opcion para los one time event.
    private val _uiError = Channel<String>()
    val uiError = _uiError.receiveAsFlow()


    fun handleEvent(event: SumaEvent) {
        when (event) {

            SumaEvent.ErrorMostrado ->{
                _uiState.update{it.copy(error = null)}

            }

            is SumaEvent.Restar -> restar(event.incremento)

            is SumaEvent.Sumar -> sumar(event.incremento)
            is SumaEvent.ChangeIncremento -> _uiState.update { it.copy(incremento = event.incremento) }
        }
    }

    private fun operacion( op:(Int,Int) -> Int,incremento: Int){
        if (Random.nextBoolean())
            _uiState.update{it.copy(contador = op(_uiState.value.contador,incremento))}
        else{
            //_uiState.update{_uiState.value.copy(error = "Error aleatorio")}
            viewModelScope.launch {
                _uiError.send(stringProvider.getString(R.string.error))
            }
            Timber.d(stringProvider.getString(R.string.error))
        }

    }

    private fun sumar(incremento:Int) {
        operacion(Int::plus, incremento)
    }

    private fun restar(incremento : Int) {
        operacion(Int::minus,incremento)
    }

}

