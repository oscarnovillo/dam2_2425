package com.example.viewmodel.ui.pantalllamain

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodel.databinding.PersonaViewBinding
import com.example.viewmodel.domain.modelo.Persona

class PersonaItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = PersonaViewBinding.bind(itemView)

    fun bind(item: Persona){
        with(binding) {
            textViewNombre.text = item.nombre
            textViewApellido.text = item.apellidos

            itemView.setBackgroundResource(android.R.color.white)



            itemView.setOnLongClickListener{
                true
            }
            itemView.setOnClickListener {
               //actions.onItemClick(item)
            }
        }
    }
}