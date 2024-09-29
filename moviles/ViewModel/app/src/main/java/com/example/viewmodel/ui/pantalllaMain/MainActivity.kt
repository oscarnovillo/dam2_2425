package com.example.viewmodel.ui.pantalllaMain

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
import com.example.viewmodel.databinding.ActivityMainBinding
import com.example.viewmodel.domain.modelo.Persona
import com.example.viewmodel.domain.usecases.personas.AddPersonaUseCase
import com.example.viewmodel.domain.usecases.personas.GetPersonas

class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddPersonaUseCase(),
            GetPersonas(Repository()),
        )
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
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
        viewModel.uiState.observe(this@MainActivity) { state ->

            state.error?.let { error ->
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }


            if (state.error == null)
                binding.editTextTextPersonName.setText(state.persona?.nombre)
        }
    }

    private fun eventos() {

        with(binding) {
            button.setOnClickListener {
                viewModel.addPersona(Persona(editTextTextPersonName.text.toString()))
                viewModel.getPersonas(2)

            }

        }
    }
}