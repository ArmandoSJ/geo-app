package com.developer.jauregui.backend.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.developer.jauregui.backend.entities.RutaEntity
import com.developer.jauregui.backend.entities.UserEntity
import com.developer.jauregui.utils.DateConverter


@Database(entities = arrayOf(RutaEntity::class, UserEntity::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class GeoDataBase : RoomDatabase() {
    companion object {
        private  lateinit var dataBase: GeoDataBase

        fun getInstance(context: Context): GeoDataBase{
                synchronized(GeoDataBase::class){
                    dataBase = Room.databaseBuilder(
                        context.applicationContext,
                        GeoDataBase::class.java,
                        "geodatabase.db"
                    ).build()
                }

            return dataBase
        }
    }


    abstract fun rutaDao(): RutaDao
    abstract fun userDao(): UserDao
}
