package com.developer.jauregui.backend.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.developer.jauregui.backend.entities.RutaEntity
import com.developer.jauregui.backend.entities.UserEntity


@Database(entities = arrayOf(RutaEntity::class, UserEntity::class), version = 1)
abstract class GeoDataBase : RoomDatabase() {
    companion object {
        private  var Instance: GeoDataBase?= null

        fun getInstance(context: Context): GeoDataBase?{
            if (Instance == null){

                synchronized(GeoDataBase::class){
                    Instance = Room.databaseBuilder(
                        context.applicationContext,
                        GeoDataBase::class.java,
                        "geodatabase.db"
                    ).build()
                }
            }
            return Instance
        }
    }


    abstract fun rutaDao(): RutaDao
    abstract fun userDao(): UserDao
}
