package cz.nestresuju.screens.program

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Program identifiers.
 */
@Parcelize
enum class ProgramId(val txtId: String) : Parcelable {

    PROGRAM_FIRST_ID("goal"),
    PROGRAM_SECOND_ID("relaxation"),
    PROGRAM_THIRD_ID("time-management"),
    PROGRAM_FOURTH_ID("searching-for-meaning")
}