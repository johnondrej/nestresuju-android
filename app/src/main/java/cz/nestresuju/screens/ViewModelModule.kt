package cz.nestresuju.screens

import cz.nestresuju.screens.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for providing ViewModels.
 */

val viewModelModule = module {

    viewModel { LoginViewModel(authRepository = get()) }
}