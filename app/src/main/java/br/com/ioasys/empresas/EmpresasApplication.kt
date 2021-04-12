package br.com.ioasys.empresas

import android.app.Application
import br.com.ioasys.empresas.di.dataLocalModule
import br.com.ioasys.empresas.di.dataModule
import br.com.ioasys.empresas.di.dataRemoteModule
import br.com.ioasys.empresas.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EmpresasApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@EmpresasApplication)
            modules(
                presentationModule,
                dataModule,
                dataRemoteModule,
                dataLocalModule
            )
        }
    }

}