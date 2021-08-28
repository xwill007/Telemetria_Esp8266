package com.willvargas.telemetria_esp8266.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tabla_usuarios")
data class User (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "nombre") val nombre :String ? = null,
    @ColumnInfo(name = "telefono") val telefono :String ? = null,
    @ColumnInfo(name = "correo") val correo :String ? = null,
    @ColumnInfo(name = "idF") val idF :String ? = null,

    )