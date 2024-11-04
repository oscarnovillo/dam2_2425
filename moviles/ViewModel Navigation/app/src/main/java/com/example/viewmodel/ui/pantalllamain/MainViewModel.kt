package com.example.viewmodel.ui.pantalllamain
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodel.domain.usecases.personas.GetPersonas
import com.example.viewmodel.ui.pantalladetalle.DetalleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPersonas: GetPersonas,
) : ViewModel() {

    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState
    init {
        getPersonas()
    }
    fun getPersonas() {
        _uiState.value = _uiState.value?.copy(personas = getPersonas.invoke())
    }
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