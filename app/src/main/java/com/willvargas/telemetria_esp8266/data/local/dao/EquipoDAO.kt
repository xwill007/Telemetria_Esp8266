package com.willvargas.telemetria_esp8266.data.local.dao

import androidx.room.*
import com.willvargas.telemetria_esp8266.data.local.entities.Equipo
import com.willvargas.telemetria_esp8266.data.local.entities.User

@Dao
interface EquipoDAO {

    @Insert
    fun insertEquipo(equipo: Equipo)

    @Query("SELECT * From tabla_equipos WHERE idEquipo LIKE :idEquipo")
    fun searchEquipo(idEquipo: String): User

    @Delete
    fun deleteEquipo(equipo: Equipo)

    @Update
    fun updateEquipo(equipo: Equipo)
}