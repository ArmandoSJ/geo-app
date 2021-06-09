package com.cursosant.android.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursosant.android.stores.mainModule.model.MainInteractor
import com.developer.jauregui.backend.entities.RutaEntity


class MainViewModel: ViewModel() {
    private var rutaList: MutableList<RutaEntity>
    private var interactor: MainInteractor

    init {
        rutaList = mutableListOf()
        interactor = MainInteractor()
    }

    private val rutas: MutableLiveData<List<RutaEntity>> by lazy {
        MutableLiveData<List<RutaEntity>>().also {
            loadStores()
        }
    }

    fun getRutas(): LiveData<List<RutaEntity>>{
        return rutas
    }

    private fun loadStores(){
        interactor.getStores {
            rutas.value = it
            rutaList = it
        }
    }

    fun deleteStore(storeEntity: RutaEntity){
        interactor.deleteStore(storeEntity, {
            val index = rutaList.indexOf(storeEntity)
            if (index != -1){
                rutaList.removeAt(index)
                rutas.value = rutaList
            }
        })
    }

    fun updateRutas(rutasEntity: RutaEntity){

    }
}