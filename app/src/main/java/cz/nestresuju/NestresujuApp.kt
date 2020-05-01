package cz.nestresuju

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import cz.nestresuju.common.extensions.styleDefaults
import cz.nestresuju.model.converters.converterModule
import cz.nestresuju.model.database.databaseModule
import cz.nestresuju.model.repositories.repositoryModule
import cz.nestresuju.networking.networkingModule
import cz.nestresuju.screens.viewModelModule
import io.multimoon.colorful.initColorful
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
                databaseModule,
                repositoryModule,
                viewModelModule
            )
        }

        val styleDefaults = styleDefaults(
            style = R.style.HomeStyle,
            primaryColor = R.color.primary,
            primaryDarkColor = R.color.primaryDark,
            accentColor = R.color.accent
        )
        initColorful(this, styleDefaults)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}