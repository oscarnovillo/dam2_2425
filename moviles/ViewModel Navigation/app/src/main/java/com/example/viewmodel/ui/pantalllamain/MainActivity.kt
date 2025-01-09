package com.example.viewmodel.ui.pantalllamain

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewmodel.R
import com.example.viewmodel.data.Repository

import com.example.viewmodel.databinding.ActivityMainBinding
import com.example.viewmodel.domain.modelo.Persona
import com.example.viewmodel.domain.usecases.personas.GetPersonas
import com.example.viewmodel.ui.common.MarginItemDecoration
import com.example.viewmodel.ui.common.UiEvent
import com.example.viewmodel.ui.pantalladetalle.DetalleActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PersonaAdapter


    private val viewModel: MainViewModel by viewModels()

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

        events()
        configureRecyclerView()
        observarState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getPersonas()
    }

    private fun observarState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    adapter.submitList(state.personas)
                    state.event?.let { event ->
                        if (event is UiEvent.ShowSnackbar) {
                            val mys = Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT)
                                mys.setAction("UNDO") {
                                   Log.d("::TAG::","click undo")
                                }
                                mys.show()

                        }
                        viewModel.eventConsumido()
                    }
                }
            }
        }
    }

    private fun configureRecyclerView() {

        adapter = PersonaAdapter(itemClick = { persona ->
            navigateToDetail(persona.id)

        },
            actions = object : PersonaAdapter.PersonasActions {
                override fun onItemClick(persona: Persona) {
                    navigateToDetail(persona.id)
                }

            })

        binding.listaPersonas.layoutManager = LinearLayoutManager(this)

        binding.listaPersonas.adapter = adapter

        binding.listaPersonas.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.margin
                )
            )
        )
    }


    private fun navigateToDetail(id: Int) {
        val intent = Intent(this, DetalleActivity::class.java)
        intent.putExtra("id", id)

        intent.putExtra(getString(R.string.persona), Persona(0, "nombre", "apellidos"))

        startActivity(intent)

    }

    private fun events() {

        binding.button2.setOnClickListener {
            navigateToDetail(1)

        }
    }
}