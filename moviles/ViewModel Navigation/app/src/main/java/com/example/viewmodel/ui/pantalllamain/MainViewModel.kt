package com.example.viewmodel.ui.pantalllamain
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.viewmodel.R
import com.example.viewmodel.data.remote.NetworkResult
import com.example.viewmodel.domain.modelo.toPersona
import com.example.viewmodel.domain.usecases.personas.GetPersonas
import com.example.viewmodel.domain.usecases.personas.GetUsers
import com.example.viewmodel.ui.common.UiEvent
import com.example.viewmodel.ui.pantalladetalle.DetalleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUsers: GetUsers,
    private val getPersonas: GetPersonas,
) : ViewModel() {

    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState
    init {
        getPersonas()
    }




    fun getPersonas() {

        viewModelScope.launch {
            when (val result =getUsers.invoke())
            {
                is NetworkResult.Error -> _uiState.value = _uiState.value?.copy(event = UiEvent.ShowSnackbar(result.message), isLoading = false)
                is NetworkResult.Loading -> _uiState.value = _uiState.value?.copy(isLoading = true)
                is NetworkResult.Success -> {

                    val personas = result.data.map{ it.toPersona() }.toList()

                    _uiState.value = _uiState.value?.copy(personas = personas,
                        isLoading = false)

                }
            }
        }

    }

    fun eventConsumido()
    {
        _uiState.value = _uiState.value?.copy(event = null)
    }
}

