package br.com.ioasys.empresas.data.local

import android.content.Context
import androidx.room.Room

object DatabaseConfiguration {

    fun getDatabaseInstance(context: Context): EmpresasDataBase {
        return Room.databaseBuilder(
            context,
            EmpresasDataBase::class.java,
            "empresas_db"
        ).build()
    }

}