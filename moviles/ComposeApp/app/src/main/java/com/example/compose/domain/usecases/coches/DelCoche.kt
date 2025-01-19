package com.example.compose.domain.usecases.coches

import com.example.compose.data.CocheRepository
import com.example.compose.domain.modelo.Coche
import javax.inject.Inject

class DelCoche @Inject constructor(val cocheRepository: CocheRepository){

    operator fun invoke(coche: Coche) = cocheRepository.delCoches(listOf(coche))
}