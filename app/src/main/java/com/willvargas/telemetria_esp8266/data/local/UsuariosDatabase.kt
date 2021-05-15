package com.willvargas.telemetria_esp8266.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.willvargas.telemetria_esp8266.data.local.dao.UserDAO
import com.willvargas.telemetria_esp8266.data.local.entities.User

@Database(entities=[User::class],version= 1)
abstract class UsuariosDatabase : RoomDatabase() {
    abstract fun UserDAO(): UserDAO
}