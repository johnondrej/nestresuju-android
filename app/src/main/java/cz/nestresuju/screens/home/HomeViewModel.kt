package cz.nestresuju.screens.home

import androidx.lifecycle.viewModelScope
import cz.nestresuju.R
import cz.nestresuju.model.common.StateLiveData
import cz.nestresuju.model.repositories.*
import cz.nestresuju.model.synchronization.DataSynchronizer
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import org.threeten.bp.LocalDate

class HomeViewModel(
    private val programFirstRepository: ProgramFirstRepository,
    private val programSecondRepository: ProgramSecondRepository,
    private val programThirdRepository: ProgramThirdRepository,
    private val programFourthRepository: ProgramFourthRepository,
    private val diaryRepository: DiaryRepository,
    private val dataSynchronizer: DataSynchronizer
) : BaseViewModel() {

    val screenStateStream = StateLiveData<List<HomeItem>>()

    init {
        screenStateStream.loading()
        viewModelScope.launchWithErrorHandling {
            dataSynchronizer.synchronizeAll()

            val programFirstFlow = programFirstRepository.observeProgramResults()
            val programSecondFlow = programSecondRepository.observeProgramResults()
            val programThirdFlow = programThirdRepository.observeProgramResults()
            val programFourthFlow = programFourthRepository.observeProgramResults()
            val diaryFlow = diaryRepository.observeDiaryEntries()

            combine(
                programFirstFlow,
                programSecondFlow,
                programThirdFlow,
                programFourthFlow,
                diaryFlow
            ) { firstResults, secondResults, thirdResults, fourthResults, diaryEntries ->
                // TODO: check if program is opened
                val homeItems = mutableListOf<HomeItem>()

                if (firstResults.programCompleted == null || secondResults.programCompleted == null
                    || thirdResults.programCompleted == null || fourthResults.programCompleted == null
                ) {
                    homeItems += HomeItem(
                        destination = Destination.PROGRAM,
                        textRes = R.string.home_unfinished_program_title,
                        descriptionRes = R.string.home_unfinished_program_description
                    )
                }

                val today = LocalDate.now()
                homeItems += if (diaryEntries.none { diaryEntry -> diaryEntry.dateCreated.toLocalDate() == today }) {
                    HomeItem(
                        destination = Destination.DIARY,
                        textRes = R.string.home_diary_mood_title,
                        descriptionRes = R.string.home_diary_mood_description
                    )
                } else {
                    HomeItem(
                        destination = Destination.DIARY,
                        textRes = R.string.home_diary_note_title,
                        descriptionRes = R.string.home_diary_note_description
                    )
                }

                homeItems += HomeItem(
                    destination = Destination.LIBRARY,
                    textRes = R.string.home_library_title,
                    descriptionRes = R.string.home_library_description
                )

                homeItems
            }.collect { homeItems ->
                screenStateStream.loaded(homeItems)
            }
        }
    }

    data class HomeItem(
        val destination: Destination,
        val textRes: Int,
        val descriptionRes: Int
    )

    enum class Destination {
        PROGRAM,
        DIARY,
        LIBRARY
    }
}