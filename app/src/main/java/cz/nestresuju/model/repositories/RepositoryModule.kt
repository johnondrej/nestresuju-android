package cz.nestresuju.model.repositories

import android.content.Context
import cz.nestresuju.model.database.sharedprefs.SharedPreferencesInteractor
import cz.nestresuju.model.synchronization.DataSynchronizer
import cz.nestresuju.model.synchronization.DataSynchronizerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Koin module providing all repositories.
 */

private const val SHARED_PREFS_KEY = "shared_prefs"

val repositoryModule = module {

    factory {
        AuthRepository(
            authApiDefinition = get(),
            apiDefinition = get(),
            authEntitiesConverter = get(),
            oAuthManager = get(),
            sharedPreferencesInteractor = get()
        )
    }

    factory<InputTestsRepository> { InputTestsRepositoryImpl(apiDefinition = get(), entityConverter = get(), sharedPreferencesInteractor = get()) }

    factory<DiaryRepository> {
        DiaryRepositoryImpl(apiDefinition = get(), database = get(), dataSynchronizer = get(), entityConverter = get())
    }

    factory<DataSynchronizer> { DataSynchronizerImpl(apiDefinition = get(), database = get()) }

    factory { SharedPreferencesInteractor(sharedPreferences = get()) }

    factory { androidContext().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE) }
}