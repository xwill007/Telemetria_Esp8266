package com.willvargas.telemetria_esp8266

import android.app.Application
import androidx.room.Room
import com.willvargas.telemetria_esp8266.data.local.EquiposDatabase
import com.willvargas.telemetria_esp8266.data.local.UsuariosDatabase

class MiBaseDeDatosApp : Application() {
    companion object {
        lateinit var databaseUser: UsuariosDatabase
        lateinit var databaseEquipos: EquiposDatabase
    }
    override fun onCreate(){
        super.onCreate()

        databaseUser = Room.databaseBuilder(this, UsuariosDatabase::class.java, "usuario_db").allowMainThreadQueries().build()
        databaseEquipos = Room.databaseBuilder(this, EquiposDatabase::class.java, "equipo_db").allowMainThreadQueries().build()
    }
}




