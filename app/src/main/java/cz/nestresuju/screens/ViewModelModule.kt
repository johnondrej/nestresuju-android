package cz.nestresuju.screens

import cz.nestresuju.model.entities.domain.program.ProgramId
import cz.nestresuju.router.RouterViewModel
import cz.nestresuju.screens.about.AboutAppViewModel
import cz.nestresuju.screens.about.ContactsViewModel
import cz.nestresuju.screens.about.FeedbackViewModel
import cz.nestresuju.screens.about.ResearchViewModel
import cz.nestresuju.screens.diary.DiaryViewModel
import cz.nestresuju.screens.home.HomeViewModel
import cz.nestresuju.screens.library.LibraryViewModel
import cz.nestresuju.screens.login.LoginViewModel
import cz.nestresuju.screens.program.evaluation.ProgramEvaluationViewModel
import cz.nestresuju.screens.program.first.ProgramFirstOverviewViewModel
import cz.nestresuju.screens.program.first.ProgramFirstQuestionViewModel
import cz.nestresuju.screens.program.first.ProgramFirstSatisfiabilityViewModel
import cz.nestresuju.screens.program.first.ProgramFirstSummaryViewModel
import cz.nestresuju.screens.program.fourth.ProgramFourthQuestionViewModel
import cz.nestresuju.screens.program.fourth.ProgramFourthQuestionsIntroViewModel
import cz.nestresuju.screens.program.fourth.ProgramFourthSummaryViewModel
import cz.nestresuju.screens.program.fourth.ProgramFourthTextQuestionViewModel
import cz.nestresuju.screens.program.overview.ProgramViewModel
import cz.nestresuju.screens.program.second.ProgramSecondInstructionsViewModel
import cz.nestresuju.screens.program.second.ProgramSecondRelaxationViewModel
import cz.nestresuju.screens.program.third.*
import cz.nestresuju.screens.tests.input.InputTestViewModel
import cz.nestresuju.screens.tests.output.OutputTestFirstViewModel
import cz.nestresuju.screens.tests.output.OutputTestSecondViewModel
import cz.nestresuju.screens.tests.screening.ScreeningTestViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for providing ViewModels.
 */

val viewModelModule = module {

    viewModel { RouterViewModel(oAuthManager = get(), sharedPreferencesInteractor = get()) }

    viewModel { LoginViewModel(authRepository = get()) }

    viewModel { InputTestViewModel(inputTestsRepository = get()) }

    viewModel { ScreeningTestViewModel(inputTestsRepository = get()) }

    viewModel {
        HomeViewModel(
            programOverviewRepository = get(),
            programFirstRepository = get(),
            programSecondRepository = get(),
            programThirdRepository = get(),
            programFourthRepository = get(),
            diaryRepository = get(),
            dataSynchronizer = get()
        )
    }

    viewModel {
        ProgramViewModel(
            programOverviewRepository = get(),
            programFirstRepository = get(),
            programSecondRepository = get(),
            programThirdRepository = get(),
            programFourthRepository = get(),
            dataSynchronizer = get()
        )
    }

    viewModel { (programId: ProgramId) ->
        ProgramEvaluationViewModel(
            programId,
            programEvaluationRepository = get(),
            programOverviewRepository = get()
        )
    }

    viewModel { (progress: Int) -> ProgramFirstQuestionViewModel(progress, programRepository = get()) }

    viewModel { ProgramFirstSatisfiabilityViewModel(programRepository = get()) }

    viewModel { ProgramFirstOverviewViewModel(programRepository = get()) }

    viewModel { ProgramFirstSummaryViewModel(programRepository = get()) }

    viewModel { (progress: Int) -> ProgramSecondInstructionsViewModel(progress, programRepository = get()) }

    viewModel { ProgramSecondRelaxationViewModel(programRepository = get()) }

    viewModel { ProgramThirdTimetableViewModel(programRepository = get()) }

    viewModel { ProgramThirdActivitiesViewModel(programRepository = get()) }

    viewModel { ProgramThirdActivitiesSummaryIntroViewModel(programRepository = get()) }

    viewModel { ProgramThirdActivitiesSummaryViewModel(programRepository = get()) }

    viewModel { (progress: Int) -> ProgramThirdGoalQuestionViewModel(progress, programRepository = get()) }

    viewModel { ProgramThirdGoalSatisfiabilityViewModel(programRepository = get()) }

    viewModel { ProgramThirdOverviewViewModel(programRepository = get()) }

    viewModel { ProgramThirdSummaryViewModel(programRepository = get()) }

    viewModel { (progress: Int) -> ProgramFourthTextQuestionViewModel(progress, programRepository = get()) }

    viewModel { ProgramFourthQuestionsIntroViewModel(programRepository = get()) }

    viewModel { ProgramFourthQuestionViewModel(programRepository = get()) }

    viewModel { ProgramFourthSummaryViewModel(programRepository = get()) }

    viewModel { DiaryViewModel(androidContext(), diaryRepository = get()) }

    viewModel { LibraryViewModel(libraryRepository = get()) }

    viewModel { AboutAppViewModel() }

    viewModel { ContactsViewModel(aboutAppRepository = get()) }

    viewModel { ResearchViewModel(aboutAppRepository = get(), apiDefinition = get()) }

    viewModel { FeedbackViewModel(apiDefinition = get()) }

    viewModel { OutputTestFirstViewModel(inputTestsRepository = get()) }

    viewModel { OutputTestSecondViewModel(apiDefinition = get()) }
}