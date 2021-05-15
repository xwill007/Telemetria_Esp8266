package com.willvargas.telemetria_esp8266

import android.app.Application
import androidx.room.Room
import com.willvargas.telemetria_esp8266.data.local.UsuariosDatabase

class MisUsuariosApp : Application() {
    companion object {
        lateinit var database: UsuariosDatabase
    }
    override fun onCreate(){
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            UsuariosDatabase::class.java,
            "usuario_db"
        ).allowMainThreadQueries()
            .build()

    }
}



