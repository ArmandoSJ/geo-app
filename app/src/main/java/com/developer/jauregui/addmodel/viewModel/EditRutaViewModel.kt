package com.cursosant.android.stores.editModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursosant.android.stores.editModule.model.EditInteractor
import com.developer.jauregui.backend.entities.RutaEntity

class EditRutaViewModel :ViewModel() {
    private val storeSelected = MutableLiveData<RutaEntity>()
    private val showFab = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any>()

    private val interactor: EditInteractor

    init {
        interactor = EditInteractor()
    }

    fun setStoreSelected(storeEntity: RutaEntity){
        storeSelected.value = storeEntity
    }

    fun getStoreSelected(): LiveData<RutaEntity>{
        return storeSelected
    }

    fun setShowFab(isVisible: Boolean){
        showFab.value = isVisible
    }

    fun getShowFab(): LiveData<Boolean>{
        return showFab
    }

    fun setResult(value: Any){
        result.value = value
    }

    fun getResult(): LiveData<Any>{
        return result
    }

    fun saveStore(rutaEntity: RutaEntity){
        interactor.saveStore(rutaEntity, { newId ->
            result.value = newId
        })
    }

    fun updateStore(rutaEntity: RutaEntity){
        interactor.updateStore(rutaEntity, { storeUpdated ->
            result.value = storeUpdated
        })
    }
}