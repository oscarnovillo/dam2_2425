package com.example.viewmodel.ui.pantalllamain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewmodel.R
import com.example.viewmodel.databinding.ActivityMainBinding
import com.example.viewmodel.domain.modelo.Persona
import com.example.viewmodel.ui.Constantes.HOLA_Q_TAL

import com.example.viewmodel.ui.common.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var adapter: PersonaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        events()
        configureRecyclerView()
        observarState()
        val stringQueBajaPuntos = HOLA_Q_TAL

    }


    private fun observarState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.personas)
            //adapter.notifyDataSetChanged()
        }
    }

    private fun configureRecyclerView() {

        adapter = PersonaAdapter(itemClick = { persona ->
            // findNavController().navigate(

        },
            actions = object : PersonaAdapter.PersonasActions {
                override fun onItemClick(persona: Persona) {
                    //findNavController().navigate(DetalleFragmentDirections.actionMainFragmentToMiFragment(persona.id) )
                }

            })

        binding.listaPersonas.layoutManager = LinearLayoutManager(requireContext())

        binding.listaPersonas.adapter = adapter

        binding.listaPersonas.addItemDecoration(
            MarginItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.margin
                )
            )
        )
    }


    private fun events() {

        binding.button2.setOnClickListener {
            //findNavController().navigate(R.id.action_mainFragment_to_addPersonaFragment)

        }
    }

}