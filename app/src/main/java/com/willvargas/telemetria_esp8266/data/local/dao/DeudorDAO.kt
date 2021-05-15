package com.willvargas.telemetria_esp8266.data.local.dao

import androidx.room.*
import com.willvargas.telemetria_esp8266.data.local.entities.User

@Dao
interface UserDAO {

    @Insert
    fun insertDeudor(deudor: User)

    @Query("SELECT * From tabla_usuarios WHERE nombre LIKE :nombre")
    fun searchDeudor(nombre: String): User

    @Delete
    fun deleteDeudor(deudor: User)

    @Update
    fun updateDeudor(deudor: User)
}