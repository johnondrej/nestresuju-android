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
    private val onItemClicked: (HomeViewModel.HomeItem) -> Unit
) : EpoxyController() {

    var items: List<HomeViewModel.HomeItem> by controllerProperty(emptyList())

    override fun buildModels() {
        items.forEach { homeItem ->
            navigationCard {
                id("item-${homeItem.destination.ordinal}-${homeItem.textRes}")
                title(getTitleForDestination(homeItem.destination))
                stateText(applicationContext.getString(homeItem.textRes))
                stateDescriptionText(applicationContext.getString(homeItem.descriptionRes))
                onItemClicked { onItemClicked(homeItem) }
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