package com.developer.jauregui.backend.database

import androidx.room.*
import com.developer.jauregui.backend.entities.RutaEntity


@Dao
interface RutaDao {
    @Query("SELECT * FROM ruta_table")
    fun getAllRutas() : MutableList<RutaEntity>

    @Query("SELECT * FROM ruta_table where idLocation = :id")
    fun getRutasById(id: Long): RutaEntity

    @Insert
    fun addRuta(rutaEntity: RutaEntity): Long

    @Update
    fun updateRuta(rutaEntity: RutaEntity)

    @Delete
    fun deleteRuta(rutaEntity: RutaEntity)
}