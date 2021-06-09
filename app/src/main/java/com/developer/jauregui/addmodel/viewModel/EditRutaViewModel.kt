package com.cursosant.android.stores.editModule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursosant.android.stores.editModule.model.EditInteractor
import com.developer.jauregui.backend.entities.RutaEntity

class EditRutaViewModel :ViewModel() {
    private val rutaSelected = MutableLiveData<RutaEntity>()
    private val showFab = MutableLiveData<Boolean>()
    private val result = MutableLiveData<Any>()

    private val interactor: EditInteractor

    init {
        interactor = EditInteractor()
    }

    fun setRutaSelected(rutaEntity: RutaEntity){
        rutaSelected.value = rutaEntity
    }

    fun getRutaSelected(): LiveData<RutaEntity>{
        return rutaSelected
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

    fun saveRuta(rutaEntity: RutaEntity){
        interactor.saveRuta(rutaEntity, { newId ->
            result.value = newId
        })
    }

    fun updateRuta(rutaEntity: RutaEntity){
        interactor.updateRuta(rutaEntity, { rutaUpdated ->
            result.value = rutaUpdated
        })
    }
}