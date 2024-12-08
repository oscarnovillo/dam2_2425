package com.example.navigation.ui.personas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.navigation.R
import com.example.navigation.databinding.FragmentDetalleBinding
import com.example.navigation.ui.mainFragment.MainFragment


class DetalleFragment : Fragment(), MenuProvider {


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
            configAppBar()
            button.setOnClickListener {
                // navigate to add fragment
                findNavController().navigateUp()
                findNavController().navigate(DetalleFragmentDirections.actionMainFragmentToMiFragment(2) )
            }

        }
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

        // añade opciones al top bar
        menuInflater.inflate(R.menu.menu_appbar_search, menu)


        // controla la busqueda en el top bar
        val actionSearch = menu.findItem(R.id.search).actionView as SearchView

        actionSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    Log.d("::TAG::","submit $p0")

                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                newText?.let {
                    Log.d("::TAG::","Search $newText")

                }

                return false
            }


        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId)
        {
            R.id.boton-> {
                Log.d("::TAG::","clickado en home")
                true
            }
            else -> false
        }

    }

    private fun configAppBar() {

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }
}