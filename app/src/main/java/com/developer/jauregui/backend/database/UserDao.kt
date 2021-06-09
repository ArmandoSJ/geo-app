package com.developer.jauregui.backend.database

import androidx.room.*
import com.developer.jauregui.backend.entities.RutaEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAllStores() : MutableList<RutaEntity>

    @Query("SELECT * FROM user_table where vUserID = :id")
    fun getStoreById(id: String): RutaEntity

    @Insert
    fun addStore(rutaEntity: RutaEntity): Long

    @Update
    fun updateStore(rutaEntity: RutaEntity)

    @Delete
    fun deleteStore(rutaEntity: RutaEntity)
}