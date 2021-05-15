package com.willvargas.telemetria_esp8266.data.local.dao

import androidx.room.*
import com.willvargas.telemetria_esp8266.data.local.entities.User

@Dao
interface UserDAO {

    @Insert
    fun insertUser(user: User)

    @Query("SELECT * From tabla_usuarios WHERE correo LIKE :correo")
    fun searchUser(correo: String): User

    @Delete
    fun deleteUser(deudor: User)

    @Update
    fun updateUser(deudor: User)
}