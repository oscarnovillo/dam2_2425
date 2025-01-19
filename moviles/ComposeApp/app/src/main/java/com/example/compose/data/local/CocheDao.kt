package com.example.compose.data.local

import androidx.room.*
import com.example.primerxmlmvvm.data.local.modelo.CocheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocheDao {


    @Query("SELECT * FROM coches where matricula like :filtro order by matricula")
    fun getAllFlowFiltrado(filtro: String?): Flow<List<CocheEntity>>

    @Query("SELECT * FROM coches order by matricula")
    fun getAllFlow(): Flow<List<CocheEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<CocheEntity>)

    @Delete
    fun delete(movie: CocheEntity)


    @Delete
    fun deleteAll(movie: List<CocheEntity>)

    @Query("SELECT * FROM coches order by matricula")

    fun getAll(): List<CocheEntity>
    @Query("SELECT * FROM coches WHERE matricula = :matricula")
    fun getCoche(matricula: String): CocheEntity?
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(toCocheEntity: CocheEntity)
}