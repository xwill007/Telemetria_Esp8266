package com.willvargas.telemetria_esp8266.data.local.dao

import androidx.room.*
import com.willvargas.telemetria_esp8266.data.local.entities.User

@Dao
interface UserDAO {

    @Insert
    fun insertUser(user: User)

    @Query("SELECT * From tabla_usuarios WHERE nombre LIKE :nombre")
    fun searchUser(nombre: String): User

    @Delete
    fun deleteUser(deudor: User)

    @Update
    fun updateUser(deudor: User)
}