package cz.nestresuju.model.repositories

import android.content.Context
import cz.nestresuju.model.database.sharedprefs.SharedPreferencesInteractor
import cz.nestresuju.model.logouter.LogoutHandler
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
            sharedPreferencesInteractor = get(),
            logoutHandler = get()
        )
    }

    factory<InputTestsRepository> {
        InputTestsRepositoryImpl(
            apiDefinition = get(),
            entityConverter = get(),
            sharedPreferencesInteractor = get()
        )
    }
    factory<ProgramOverviewRepository> {
        ProgramOverviewRepositoryImpl(
            apiDefinition = get(),
            database = get(),
            sharedPreferencesInteractor = get(),
            entityConverter = get()
        )
    }

    factory<ProgramEvaluationRepository> {
        ProgramEvaluationRepositoryImpl(
            dataSynchronizer = get()
        )
    }

    factory<ProgramFirstRepository> {
        ProgramFirstRepositoryImpl(
            apiDefinition = get(),
            entityConverter = get(),
            dataSynchronizer = get(),
            database = get()
        )
    }

    factory<ProgramSecondRepository> {
        ProgramSecondRepositoryImpl(
            apiDefinition = get(),
            entityConverter = get(),
            dataSynchronizer = get(),
            database = get()
        )
    }

    factory<ProgramThirdRepository> {
        ProgramThirdRepositoryImpl(
            apiDefinition = get(),
            entityConverter = get(),
            dataSynchronizer = get(),
            database = get()
        )
    }

    factory<ProgramFourthRepository> {
        ProgramFourthRepositoryImpl(
            apiDefinition = get(),
            entityConverter = get(),
            dataSynchronizer = get(),
            database = get()
        )
    }

    factory<DiaryRepository> {
        DiaryRepositoryImpl(
            apiDefinition = get(),
            database = get(),
            dataSynchronizer = get(),
            entityConverter = get()
        )
    }

    factory<LibraryRepository> {
        LibraryRepositoryImpl(
            apiDefinition = get(),
            database = get(),
            entityConverter = get()
        )
    }

    factory<AboutAppRepository> {
        AboutAppRepositoryImpl(
            apiDefinition = get(),
            database = get(),
            entityConverter = get()
        )
    }

    factory<DataSynchronizer> {
        DataSynchronizerImpl(
            apiDefinition = get(),
            database = get(),
            programEvaluationConverter = get(),
            programFirstConverter = get(),
            programSecondConverter = get(),
            programThirdConverter = get(),
            programFourthConverter = get()
        )
    }

    factory { SharedPreferencesInteractor(sharedPreferences = get()) }

    factory { androidContext().getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE) }

    factory {
        LogoutHandler(
            applicationContext = androidContext(),
            oAuthManager = get(),
            sharedPreferencesInteractor = get(),
            appDatabase = get()
        )
    }
}