package com.cursosant.android.stores.mainModule.adapter

import com.developer.jauregui.backend.entities.RutaEntity

interface OnClickListener {
    fun onClick(rutaEntity: RutaEntity)
    fun onDeleteRuta(rutaEntity: RutaEntity)
}