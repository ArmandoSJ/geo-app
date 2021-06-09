package com.developer.jauregui.backend.database

import androidx.room.*
import com.developer.jauregui.backend.entities.RutaEntity
import com.developer.jauregui.backend.entities.UserEntity

@Dao
interface UserDao {
    @Query("Select * From user_table")
    fun getAllUser() : MutableList<UserEntity>

    @Query("Select * From user_table where vUserID = :id")
    fun getUserById(id: String): UserEntity

    @Insert
    fun addUser(userEntity: UserEntity): Long

    @Update
    fun updateUser(userEntity: UserEntity)

    @Delete
    fun deleteUser(userEntity: UserEntity)
}