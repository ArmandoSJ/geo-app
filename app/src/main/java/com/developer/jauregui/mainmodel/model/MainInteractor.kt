package com.cursosant.android.stores.mainModule.model


import android.app.Application
import com.developer.jauregui.backend.database.GeoDataBase
import com.developer.jauregui.backend.entities.RutaEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class MainInteractor : Application()  {

    fun getStores(callback: (MutableList<RutaEntity>) -> Unit){
        doAsync {
            val lstRuta = GeoDataBase.getInstance(this@MainInteractor).rutaDao().getAllRutas()
            uiThread {
                callback(lstRuta)
            }
        }
    }

    fun deleteStore(rutaEntity: RutaEntity, callback: (RutaEntity) -> Unit){
        doAsync {
            GeoDataBase.getInstance(this@MainInteractor).rutaDao().deleteRuta(rutaEntity)
            uiThread {
                callback(rutaEntity)
            }
        }
    }

    fun updateStore(rutaEntity: RutaEntity, callback: (RutaEntity) -> Unit){
        doAsync {
            GeoDataBase.getInstance(this@MainInteractor).rutaDao().deleteRuta(rutaEntity)
            uiThread {
                callback(rutaEntity)
            }
        }
    }
}