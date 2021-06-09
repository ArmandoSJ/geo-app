package com.developer.jauregui.backend.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ruta_table",
    foreignKeys = arrayOf(
    ForeignKey(entity = UserEntity::class,
    parentColumns = arrayOf("vUserID"),
    childColumns = arrayOf("vUserID"),
    onDelete = ForeignKey.CASCADE)
))
data class RutaEntity(@PrimaryKey(autoGenerate = true) var idLocation: Long = 0,
                      var vDescription: String,
                      var vLong: String,
                      var vLat: String,
                      var vUserID: String = "",
                      var vFechaEntre : Date?){

    constructor() : this(vDescription = "", vLong = "", vLat = "", vUserID = "", vFechaEntre = null)

}