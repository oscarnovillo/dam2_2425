package com.example.viewmodel.ui.pantalllamain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.viewmodel.R
import com.example.viewmodel.domain.modelo.Persona



class PersonaAdapter(
    val itemClick: (Persona) -> Unit,
    val actions : PersonasActions,
) : ListAdapter<Persona, PersonaItemViewholder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaItemViewholder {
        return PersonaItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.persona_view, parent, false),
            actions,
            )
    }

    override fun onBindViewHolder(holder: PersonaItemViewholder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }



    // clase para la animacion al cambiar datos del adapter
    class DiffCallback : DiffUtil.ItemCallback<Persona>() {
        override fun areItemsTheSame(oldItem: Persona, newItem: Persona): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Persona, newItem: Persona): Boolean {
            return oldItem == newItem
        }
    }

    interface PersonasActions {
        fun onItemClick(persona: Persona)

    }
}