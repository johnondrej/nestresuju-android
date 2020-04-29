package cz.nestresuju.model.database.sharedprefs

import android.content.SharedPreferences
import androidx.core.content.edit
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Class for obtaining data from shared preferences.
 */
class SharedPreferencesInteractor(private val sharedPreferences: SharedPreferences) {

    companion object {

        private const val KEY_CONSENT_GIVEN = "consent_given"
        private const val KEY_INPUT_TEST_COMPLETED = "input_test_completed"
        private const val KEY_SCREENING_TEST_COMPLETED = "screening_test_completed"
        private const val KEY_OUTPUT_TEST_COMPLETED = "output_test_completed"
        private const val KEY_PROGRAM_DEADLINE = "program_deadline"
    }

    fun isConsentGiven() = sharedPreferences.getBoolean(KEY_CONSENT_GIVEN, false)

    fun setConsentGiven() = sharedPreferences.edit {
        putBoolean(KEY_CONSENT_GIVEN, true)
    }

    fun isInputTestCompleted() = sharedPreferences.getBoolean(KEY_INPUT_TEST_COMPLETED, false)

    fun setInputTestCompleted() = sharedPreferences.edit {
        putBoolean(KEY_INPUT_TEST_COMPLETED, true)
    }

    fun setOutputTestCompleted() = sharedPreferences.edit {
        putBoolean(KEY_OUTPUT_TEST_COMPLETED, true)
    }

    fun isScreeningTestCompleted() = sharedPreferences.getBoolean(KEY_SCREENING_TEST_COMPLETED, false)

    fun setScreeningTestCompleted() = sharedPreferences.edit {
        putBoolean(KEY_SCREENING_TEST_COMPLETED, true)
    }

    fun getProgramDeadline(): ZonedDateTime? {
        val storedDate = sharedPreferences.getString(KEY_PROGRAM_DEADLINE, null)

        return storedDate?.let { ZonedDateTime.parse(it) }
    }

    fun setProgramDeadline(deadline: ZonedDateTime) {
        val formattedDate = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(deadline)

        sharedPreferences.edit {
            putString(KEY_PROGRAM_DEADLINE, formattedDate)
        }
    }

    fun clearAllData() {
        sharedPreferences.edit().clear().apply()
    }
}
