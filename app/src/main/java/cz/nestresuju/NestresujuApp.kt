package cz.nestresuju

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.jakewharton.threetenabp.AndroidThreeTen
import cz.nestresuju.model.converters.converterModule
import cz.nestresuju.model.database.databaseModule
import cz.nestresuju.model.repositories.FirebaseTokenRepository
import cz.nestresuju.model.repositories.repositoryModule
import cz.nestresuju.networking.networkingModule
import cz.nestresuju.screens.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get
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

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_name)
            val descriptionText = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.notification_channel_id), name, importance).apply {
                description = descriptionText
            }

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
        }

        val koinComponent = object : KoinComponent {}
        val firebaseTokenRepository: FirebaseTokenRepository = koinComponent.get()
        firebaseTokenRepository.unregisterInvalidFirebaseToken()
    }
}