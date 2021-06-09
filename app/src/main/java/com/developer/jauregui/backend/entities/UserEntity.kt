package com.developer.jauregui.backend.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_table")
data class UserEntity (@PrimaryKey var vUserID: String = "",
                       var vUserName: String,
                       var vPhoneID: String){

    constructor() : this(vUserID = "", vUserName = "", vPhoneID = "")

}