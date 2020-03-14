package cz.nestresuju.model.repositories

import org.koin.dsl.module

/**
 * Koin module providing all repositories.
 */

val repositoryModule = module {

    factory { AuthRepository(authApiDefinition = get(), oAuthManager = get()) }
}