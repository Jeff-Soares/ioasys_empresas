package br.com.ioasys.empresas.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HeadersLocal::class], version = 1)
abstract class EmpresasDataBase: RoomDatabase() {

    abstract fun provideHeadersDao(): HeadersDao
}