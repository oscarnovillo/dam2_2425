package com.example.viewmodel.ui.pantalllamain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodel.domain.usecases.personas.GetPersonas
import com.example.viewmodel.ui.pantalladetalle.DetalleViewModel

class MainViewModel(
    private val getPersonas: GetPersonas,
) : ViewModel() {

    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init {

        _uiState.value = _uiState.value?.copy(personas = getPersonas())
    }

    fun getPersonas() = getPersonas.invoke()


}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class MainViewModelFactory(

    private val getPersonas: GetPersonas,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                getPersonas,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}