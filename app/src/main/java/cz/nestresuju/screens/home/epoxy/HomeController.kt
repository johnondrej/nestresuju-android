package cz.nestresuju.screens.home.epoxy

import android.content.Context
import com.airbnb.epoxy.EpoxyController
import cz.nestresuju.R
import cz.nestresuju.common.extensions.controllerProperty
import cz.nestresuju.screens.home.HomeViewModel
import cz.nestresuju.views.common.epoxy.navigationCard

/**
 * Epoxy controller for displaying home screen content.
 */
class HomeController(
    private val applicationContext: Context,
    private val onItemClicked: (HomeViewModel.Destination) -> Unit
) : EpoxyController() {

    var items: List<HomeViewModel.HomeItem> by controllerProperty(emptyList())

    override fun buildModels() {
        items.forEach { homeItem ->
            when (homeItem) {
                is HomeViewModel.HomeItem.CardItem -> {
                    navigationCard {
                        id("item-${homeItem.destination.ordinal}-${homeItem.textRes}")
                        title(getTitleForDestination(homeItem.destination))
                        stateText(applicationContext.getString(homeItem.textRes))
                        stateDescriptionText(applicationContext.getString(homeItem.descriptionRes))
                        onItemClicked { onItemClicked(homeItem.destination) }
                    }
                }
                is HomeViewModel.HomeItem.DiaryItem -> {
                    homeDiarySmileys {
                        id("item-diary-stresslevel")
                        onItemClicked { onItemClicked(homeItem.destination) }
                    }
                }
                is HomeViewModel.HomeItem.DeadlineItem -> {
                    homeProgramDeadline {
                        id("item-deadline")
                        daysUntilDeadline(homeItem.deadlineInDays)
                    }
                }
            }
        }
    }

    private fun getTitleForDestination(destination: HomeViewModel.Destination): String {
        val titleRes = when (destination) {
            HomeViewModel.Destination.PROGRAM -> R.string.title_program
            HomeViewModel.Destination.DIARY -> R.string.title_diary
            HomeViewModel.Destination.LIBRARY -> R.string.title_library
        }

        return applicationContext.getString(titleRes)
    }
}