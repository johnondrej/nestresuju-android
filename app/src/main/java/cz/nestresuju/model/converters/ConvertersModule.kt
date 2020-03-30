package cz.nestresuju.model.converters

import org.koin.dsl.module

/**
 * Koin module providing all entity converters.
 */

val converterModule = module {

    factory<AuthEntitiesConverter> { AuthEntitiesConverterImpl() }

    factory<StressLevelConverter> { StressLevelConverterImpl() }

    factory<DiaryEntitiesConverter> { DiaryEntitiesConverterImpl(stressLevelConverter = get()) }
}