package cz.nestresuju.model.repositories

import cz.nestresuju.model.synchronization.DataSynchronizer
import cz.nestresuju.model.synchronization.DataSynchronizerImpl
import org.koin.dsl.module

/**
 * Koin module providing all repositories.
 */

val repositoryModule = module {

    factory { AuthRepository(authApiDefinition = get(), apiDefinition = get(), authEntitiesConverter = get(), oAuthManager = get()) }

    factory<InputTestsRepository> { InputTestsRepositoryImpl(apiDefinition = get(), entityConverter = get()) }

    factory<DiaryRepository> {
        DiaryRepositoryImpl(apiDefinition = get(), database = get(), dataSynchronizer = get(), entityConverter = get())
    }

    factory<DataSynchronizer> { DataSynchronizerImpl(apiDefinition = get(), database = get()) }
}