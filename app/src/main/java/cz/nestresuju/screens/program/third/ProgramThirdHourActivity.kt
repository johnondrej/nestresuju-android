package cz.nestresuju.screens.program.third

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Enum with all possible activities that can have assigned time.
 */
@Parcelize
enum class ProgramThirdHourActivity(val txtId: String) : Parcelable {
    GETTING_UP("gettingUp"),
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner"),
    GOING_SLEEP("goingToBed"),
    SLEEPING("fallingAsleep")
}