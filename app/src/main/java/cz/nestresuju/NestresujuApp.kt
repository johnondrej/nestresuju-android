package cz.nestresuju

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import cz.nestresuju.model.converters.converterModule
import cz.nestresuju.model.repositories.repositoryModule
import cz.nestresuju.networking.networkingModule
import cz.nestresuju.screens.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Main application class.
 */
class NestresujuApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        startKoin {
            androidContext(this@NestresujuApp)
            modules(
                networkingModule,
                converterModule,
                repositoryModule,
                viewModelModule
            )
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}