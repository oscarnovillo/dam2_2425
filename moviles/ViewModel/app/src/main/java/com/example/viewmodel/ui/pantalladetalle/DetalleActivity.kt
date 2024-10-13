package com.example.viewmodel.ui.pantalladetalle

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appnobasica.utils.StringProvider
import com.example.viewmodel.R
import com.example.viewmodel.data.Repository
import com.example.viewmodel.databinding.ActivityDetalleBinding

import com.example.viewmodel.domain.modelo.Persona
import com.example.viewmodel.domain.usecases.personas.AddPersonaUseCase
import com.example.viewmodel.domain.usecases.personas.DeletePersonaUseCase
import com.example.viewmodel.domain.usecases.personas.GetPersonas
import com.example.viewmodel.ui.common.UiEvent

class DetalleActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetalleBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddPersonaUseCase(),
            GetPersonas(Repository()),
            DeletePersonaUseCase(),
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        intent.extras?.let {
            val persona = it.getParcelable<Persona>(getString(R.string.persona))
            val idPersona = it.getInt("id")
            viewModel.getPersonas(idPersona)
        }

        binding = ActivityDetalleBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // recyclerview.layouManager
        // crea adapter
        // Enganchas adapter al recycler

        eventos()
        observarViewModel()
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this@DetalleActivity) { state ->

            state.event?.let { event ->
                if (event is UiEvent.PopBackStack) {
                    this@DetalleActivity.finish()
                } else if (event is UiEvent.ShowSnackbar) {
                    Toast.makeText(this@DetalleActivity, event.message, Toast.LENGTH_SHORT).show()
                }
                viewModel.errorMostrado()
            }


            if (state.event == null)
                binding.editTextTextPersonName.setText(state.persona.nombre)
        }
    }

    private fun eventos() {

        with(binding) {
            button.setOnClickListener {
                viewModel.addPersona(Persona(editTextTextPersonName.text.toString()))
                viewModel.getPersonas(2)
            }
            buttonBorrar.setOnClickListener {
                viewModel.delPersona(viewModel.uiState.value?.persona ?: Persona())
            }

        }
    }
}