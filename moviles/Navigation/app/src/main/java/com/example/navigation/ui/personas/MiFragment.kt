package com.example.navigation.ui.personas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.navigation.databinding.FragmentMiBinding
import com.example.navigation.databinding.FragmentOtraBinding

class MiFragment : Fragment() {



    private var _binding: FragmentMiBinding? = null
    private val binding get() = _binding!!
    private val args : MiFragmentArgs by navArgs()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            args.id


        }
    }
}