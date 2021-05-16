package com.willvargas.telemetria_esp8266.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tabla_equipos")
data class Equipo (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "nombreContacto") val nombreContacto :String ? = null,
    @ColumnInfo(name = "telefonoContacto") val telefonoContacto :String ? = null,
    @ColumnInfo(name = "direccion") val direccion :String ? = null,
    @ColumnInfo(name = "idEquipo") val idEquipo :String ? = null,
    @ColumnInfo(name = "contadorBebidas") val contadorBebidas :Long ? = null,
    @ColumnInfo(name = "descripcion") val descripcion :String ? = null,
    @ColumnInfo(name = "imagenEquipo") val imagenEquipo :String ? = null,

    )