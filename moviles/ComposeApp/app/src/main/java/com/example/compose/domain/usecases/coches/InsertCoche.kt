package com.example.compose.domain.usecases.coches

import com.example.compose.data.CocheRepository
import com.example.compose.domain.modelo.Coche
import javax.inject.Inject

class InsertCoche  @Inject constructor(val cocheRepository: CocheRepository){

    operator fun invoke(coche: Coche) = cocheRepository.insert(coche)
}