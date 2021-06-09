package com.cursosant.android.stores.mainModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursosant.android.stores.mainModule.model.MainInteractor
import com.developer.jauregui.backend.entities.RutaEntity


class MainViewModel: ViewModel() {
    private var storeList: MutableList<RutaEntity>
    private var interactor: MainInteractor

    init {
        storeList = mutableListOf()
        interactor = MainInteractor()
    }

    private val stores: MutableLiveData<List<RutaEntity>> by lazy {
        MutableLiveData<List<RutaEntity>>().also {
            loadStores()
        }
    }

    fun getStores(): LiveData<List<RutaEntity>>{
        return stores
    }

    private fun loadStores(){
        interactor.getStores {
            stores.value = it
            storeList = it
        }
    }

    fun deleteStore(storeEntity: RutaEntity){
        interactor.deleteStore(storeEntity, {
            val index = storeList.indexOf(storeEntity)
            if (index != -1){
                storeList.removeAt(index)
                stores.value = storeList
            }
        })
    }

    fun updateStore(storeEntity: RutaEntity){

    }
}