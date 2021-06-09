package com.cursosant.android.stores.mainModule.adapter

import com.developer.jauregui.backend.entities.RutaEntity

interface OnClickListener {
    fun onClick(storeEntity: RutaEntity)
   // fun onFavoriteStore(storeEntity: RutaEntity)
    fun onDeleteStore(storeEntity: RutaEntity)
}