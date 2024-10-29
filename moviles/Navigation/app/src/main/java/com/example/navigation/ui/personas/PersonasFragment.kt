package com.example.navigation.ui.personas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation.databinding.FragmentPersonasBinding

class PersonasFragment : Fragment() {



    private var _binding: FragmentPersonasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPersonasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            buttonAdd.setOnClickListener {
                // navigate to add fragment
                val action = PersonasFragmentDirections.actionPersonasFragmentToAddPersonaFragment()
                findNavController().navigate(action)
            }
            buttonDetalle.setOnClickListener {
                //findNavController().navigate(PersonasFragmentDirections.actionPersonasFragmentToDetalleFragment2("34"))
            }
        }
    }

}