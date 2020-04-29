package cz.nestresuju.screens.home

import androidx.lifecycle.viewModelScope
import cz.nestresuju.R
import cz.nestresuju.model.common.StateLiveData
import cz.nestresuju.model.entities.domain.diary.DiaryEntry
import cz.nestresuju.model.entities.domain.program.ProgramId
import cz.nestresuju.model.entities.domain.program.first.ProgramFirstResults
import cz.nestresuju.model.entities.domain.program.fourth.ProgramFourthResults
import cz.nestresuju.model.entities.domain.program.overview.ProgramOverview
import cz.nestresuju.model.entities.domain.program.second.ProgramSecondResults
import cz.nestresuju.model.entities.domain.program.third.ProgramThirdResults
import cz.nestresuju.model.repositories.*
import cz.nestresuju.model.synchronization.DataSynchronizer
import cz.nestresuju.screens.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

class HomeViewModel(
    private val programOverviewRepository: ProgramOverviewRepository,
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
            try {
                programOverviewRepository.fetchProgramDeadline()
            } catch (e: Exception) {
                // do nothing, value will be fetched next time
            }

            val programDeadline = programOverviewRepository.getProgramDeadline()
            val programOverviewFlow = programOverviewRepository.observeOverview()
            val programFirstFlow = programFirstRepository.observeProgramResults()
            val programSecondFlow = programSecondRepository.observeProgramResults()
            val programThirdFlow = programThirdRepository.observeProgramResults()
            val programFourthFlow = programFourthRepository.observeProgramResults()
            val diaryFlow = diaryRepository.observeDiaryEntries()

            combine(
                programOverviewFlow,
                programFirstFlow,
                programSecondFlow,
                programThirdFlow,
                programFourthFlow,
                diaryFlow
            ) { flows ->
                val overview = flows[0] as List<ProgramOverview>
                val firstResults = flows[1] as ProgramFirstResults
                val secondResults = flows[2] as ProgramSecondResults
                val thirdResults = flows[3] as ProgramThirdResults
                val fourthResults = flows[4] as ProgramFourthResults
                val diaryEntries = flows[5] as List<DiaryEntry>
                val homeItems = mutableListOf<HomeItem>()

                val today = LocalDate.now()
                homeItems += if (diaryEntries.none { diaryEntry -> diaryEntry.dateCreated.toLocalDate() == today }) {
                    HomeItem.DiaryItem(
                        destination = Destination.DIARY
                    )
                } else {
                    HomeItem.CardItem(
                        destination = Destination.DIARY,
                        textRes = R.string.home_diary_note_title,
                        descriptionRes = R.string.home_diary_note_description
                    )
                }

                if (firstResults.programCompleted == null ||
                    (secondResults.programCompleted == null && overview.find { it.id == ProgramId.PROGRAM_SECOND_ID.txtId }?.isOpened == true) ||
                    (thirdResults.programCompleted == null && overview.find { it.id == ProgramId.PROGRAM_THIRD_ID.txtId }?.isOpened == true) ||
                    (fourthResults.programCompleted == null && overview.find { it.id == ProgramId.PROGRAM_FOURTH_ID.txtId }?.isOpened == true)
                ) {
                    homeItems += HomeItem.CardItem(
                        destination = Destination.PROGRAM,
                        textRes = R.string.home_unfinished_program_title,
                        descriptionRes = R.string.home_unfinished_program_description
                    )
                }

                homeItems += HomeItem.CardItem(
                    destination = Destination.LIBRARY,
                    textRes = R.string.home_library_title,
                    descriptionRes = R.string.home_library_description
                )

                val deadlineInDays = programDeadline?.let { ChronoUnit.DAYS.between(LocalDate.now(), it) }

                if (deadlineInDays != null && deadlineInDays > 0) {
                    homeItems += HomeItem.DeadlineItem(
                        deadlineInDays = deadlineInDays.toInt()
                    )
                }

                homeItems
            }.collect { homeItems ->
                screenStateStream.loaded(homeItems)
            }
        }
    }

    sealed class HomeItem {

        data class CardItem(val destination: Destination, val textRes: Int, val descriptionRes: Int) : HomeItem()
        data class DiaryItem(val destination: Destination) : HomeItem()
        data class DeadlineItem(val deadlineInDays: Int) : HomeItem()
    }

    enum class Destination {
        PROGRAM,
        DIARY,
        LIBRARY
    }
}