package com.example.compose.data

import com.example.compose.data.local.CocheDao
import com.example.primerxmlmvvm.data.local.modelo.toCoche
import com.example.primerxmlmvvm.data.local.modelo.toCocheEntity
import com.example.compose.di.IoDispatcher
import com.example.compose.domain.modelo.Coche
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CocheRepository @Inject constructor(
    private val cocheDao: CocheDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    fun getCochesFlow(filtro :String?): Flow<List<Coche>> {
        filtro?.let{
            return cocheDao.getAllFlowFiltrado("%$filtro%").map { listaCoches ->
            listaCoches.map { it.toCoche() }}
        }
        return cocheDao.getAllFlow().map { listaCoches ->
            listaCoches.map { it.toCoche() }}
    }

    fun getCoches(): List<Coche> =
        cocheDao.getAll().map { it.toCoche() }


    fun insertAll(coches: List<Coche>) {
        cocheDao.insertAll(coches.map { it.toCocheEntity() } )
    }

    fun insert(coche: Coche) {
        cocheDao.insert(coche.toCocheEntity()  )
    }

    fun getCoche(matricula: String): Coche? {
        return cocheDao.getCoche(matricula)?.toCoche()

    }

    fun delCoches(coche: List<Coche>): Any {
        return cocheDao.deleteAll(coche.map { it.toCocheEntity()})
    }
}


