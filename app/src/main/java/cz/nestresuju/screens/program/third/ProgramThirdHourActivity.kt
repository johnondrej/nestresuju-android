package cz.nestresuju.screens.program.third

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Enum with all possible activities that can have assigned time.
 */
@Parcelize
enum class ProgramThirdHourActivity : Parcelable {
    GETTING_UP,
    BREAKFAST,
    LUNCH,
    DINNER,
    GOING_SLEEP,
    SLEEPING
}