package com.example.navigation.ui.personas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.navigation.databinding.FragmentDetalleBinding
import com.example.navigation.ui.mainFragment.MainFragment


class DetalleFragment : Fragment() {


    private var _binding: FragmentDetalleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            button.setOnClickListener {
                // navigate to add fragment
                findNavController().navigateUp()
                findNavController().navigate(DetalleFragmentDirections.actionMainFragmentToMiFragment(2) )
            }

        }
    }
}