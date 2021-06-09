package com.cursosant.android.stores.editModule.model


import android.app.Application
import com.developer.jauregui.backend.database.GeoDataBase
import com.developer.jauregui.backend.entities.RutaEntity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class EditInteractor : Application(){

    fun saveRuta(rutaEntity: RutaEntity, callback: (Long) -> Unit){
        doAsync {
            val rutaID = GeoDataBase.getInstance(this@EditInteractor).rutaDao().addRuta(rutaEntity)
            GeoDataBase.getInstance(this@EditInteractor).rutaDao().deleteRuta(rutaEntity)
            uiThread {
                callback(rutaID)
            }
        }
    }

    fun updateRuta(rutaEntity: RutaEntity, callback: (RutaEntity) -> Unit){
        doAsync {
            GeoDataBase.getInstance(this@EditInteractor).rutaDao().updateRuta(rutaEntity)
            uiThread {
                callback(rutaEntity)
            }
        }
    }
}