package com.willvargas.telemetria_esp8266.data.server

import java.io.Serializable


class EquiposServer (
    val id : String? = null,
    val nombreContacto :String ? = null,
    val telefonoContacto :String ? = null,
    val direccion :String ? = null,
    val idEquipo :String ? = null,
    val contadorBebidas :String ? = null,
    val descripcion :String ? = null,
    val urlPicture :String ? = null,
    val emailUsuario :String ? = null
) :Serializable