package com.willvargas.telemetria_esp8266.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.willvargas.telemetria_esp8266.data.local.dao.EquipoDAO
import com.willvargas.telemetria_esp8266.data.local.entities.Equipo

@Database(entities=[Equipo::class],version= 1)
abstract class EquiposDatabase : RoomDatabase() {
    abstract fun EquipoDAO(): EquipoDAO
}