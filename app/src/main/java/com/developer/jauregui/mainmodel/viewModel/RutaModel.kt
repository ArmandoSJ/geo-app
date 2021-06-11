package com.cursosant.android.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursosant.android.stores.mainModule.model.MainInteractor
import com.developer.jauregui.backend.entities.RutaEntity


class RutaModel: ViewModel() {
    private var rutaList: MutableList<RutaEntity>
    private var interactor: MainInteractor

    init {
        rutaList = mutableListOf()
        interactor = MainInteractor()
    }

    private val rutas: MutableLiveData<List<RutaEntity>> by lazy {
        MutableLiveData<List<RutaEntity>>().also {
            loadRutas()
        }
    }

    fun getRutas(): LiveData<List<RutaEntity>>{
        return rutas
    }

    private fun loadRutas(){
        interactor.getRutas {
            rutas.value = it
            rutaList = it
        }
    }

    fun deleteRutas(rutasEntity: RutaEntity){
        interactor.deleteRutas(rutasEntity, {
            val index = rutaList.indexOf(rutasEntity)
            if (index != -1){
                rutaList.removeAt(index)
                rutas.value = rutaList
            }
        })
    }

    fun updateRutas(rutasEntity: RutaEntity){

    }
}