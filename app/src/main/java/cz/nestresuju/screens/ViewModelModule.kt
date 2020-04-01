package cz.nestresuju.screens

import cz.nestresuju.router.RouterViewModel
import cz.nestresuju.screens.about.AboutAppViewModel
import cz.nestresuju.screens.diary.DiaryViewModel
import cz.nestresuju.screens.home.HomeViewModel
import cz.nestresuju.screens.library.LibraryViewModel
import cz.nestresuju.screens.login.LoginViewModel
import cz.nestresuju.screens.program.ProgramViewModel
import cz.nestresuju.screens.tests.input.InputTestViewModel
import cz.nestresuju.screens.tests.screening.ScreeningTestViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for providing ViewModels.
 */

val viewModelModule = module {

    viewModel { RouterViewModel(oAuthManager = get()) }

    viewModel { LoginViewModel(authRepository = get()) }

    viewModel { InputTestViewModel() }

    viewModel { ScreeningTestViewModel() }

    viewModel { AboutAppViewModel() }

    viewModel { DiaryViewModel(androidContext(), diaryRepository = get()) }

    viewModel { HomeViewModel() }

    viewModel { LibraryViewModel() }

    viewModel { ProgramViewModel() }
}