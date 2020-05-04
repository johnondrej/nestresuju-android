package cz.nestresuju.model.database.sharedprefs

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Class for obtaining data from shared preferences.
 */
class SharedPreferencesInteractor(
    private val sharedPreferences: SharedPreferences,
    private val sharedPreferencesFirebase: SharedPreferences
) {

    companion object {

        private const val KEY_CONSENT_GIVEN = "consent_given"
        private const val KEY_INPUT_TEST_COMPLETED = "input_test_completed"
        private const val KEY_SCREENING_TEST_COMPLETED = "screening_test_completed"
        private const val KEY_OUTPUT_TEST_FIRST_COMPLETED = "output_test_first_completed"
        private const val KEY_OUTPUT_TEST_SECOND_COMPLETED = "output_test_second_completed"
        private const val KEY_PROGRAM_DEADLINE = "program_deadline"
        private const val KEY_FIREBASE_TOKEN = "firebase_token"
        private const val KEY_FIREBASE_TOKEN_INVALID = "invalid_firebase_token"
    }

    private val observer = PreferencesObserver(sharedPreferences)

    fun isConsentGiven() = sharedPreferences.getBoolean(KEY_CONSENT_GIVEN, false)

    fun setConsentGiven() = sharedPreferences.edit {
        putBoolean(KEY_CONSENT_GIVEN, true)
    }

    fun isInputTestCompleted() = sharedPreferences.getBoolean(KEY_INPUT_TEST_COMPLETED, false)

    fun setInputTestCompleted() = sharedPreferences.edit {
        putBoolean(KEY_INPUT_TEST_COMPLETED, true)
    }

    fun isScreeningTestCompleted() = sharedPreferences.getBoolean(KEY_SCREENING_TEST_COMPLETED, false)

    fun setScreeningTestCompleted() = sharedPreferences.edit {
        putBoolean(KEY_SCREENING_TEST_COMPLETED, true)
    }

    fun isOutputTestFirstCompleted() = sharedPreferences.getBoolean(KEY_OUTPUT_TEST_FIRST_COMPLETED, false)

    fun observeOutputTestFirstCompleted() = callbackFlow {
        offer(isOutputTestFirstCompleted())
        val keyObserver = { key: String ->
            if (key == KEY_OUTPUT_TEST_FIRST_COMPLETED) {
                offer(isOutputTestFirstCompleted())
            }
        }

        observer.observeChanges(keyObserver)
        awaitClose { observer.unregisterChangeListener(keyObserver) }
    }

    fun setOutputTestFirstCompleted() = sharedPreferences.edit {
        putBoolean(KEY_OUTPUT_TEST_FIRST_COMPLETED, true)
    }

    fun isOutputTestSecondCompleted() = sharedPreferences.getBoolean(KEY_OUTPUT_TEST_SECOND_COMPLETED, false)

    fun observeOutputTestSecondCompleted() = callbackFlow {
        offer(isOutputTestSecondCompleted())
        val keyObserver = { key: String ->
            if (key == KEY_OUTPUT_TEST_SECOND_COMPLETED) {
                offer(isOutputTestSecondCompleted())
            }
        }

        observer.observeChanges(keyObserver)
        awaitClose { observer.unregisterChangeListener(keyObserver) }
    }

    fun setOutputTestSecondCompleted() = sharedPreferences.edit {
        putBoolean(KEY_OUTPUT_TEST_SECOND_COMPLETED, true)
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

    fun getFirebaseToken() = sharedPreferencesFirebase.getString(KEY_FIREBASE_TOKEN, null)

    fun setFirebaseToken(token: String) {
        sharedPreferencesFirebase.edit {
            putString(KEY_FIREBASE_TOKEN, token)
        }
    }

    fun getInvalidFirebaseToken() = sharedPreferencesFirebase.getString(KEY_FIREBASE_TOKEN_INVALID, null)

    fun setInvalidFirebaseToken(invalidToken: String) {
        sharedPreferencesFirebase.edit {
            putString(KEY_FIREBASE_TOKEN_INVALID, invalidToken)
        }
    }

    fun clearInvalidFirebaseToken() {
        sharedPreferencesFirebase.edit {
            remove(KEY_FIREBASE_TOKEN_INVALID)
        }
    }

    fun clearAllData() {
        sharedPreferences.edit().clear().apply()
    }

    private class PreferencesObserver(private val sharedPreferences: SharedPreferences) : SharedPreferences.OnSharedPreferenceChangeListener {

        private var observers = mutableListOf<(String) -> Unit>()

        fun observeChanges(onKeyChanged: (String) -> Unit) {
            if (observers.isEmpty()) {
                sharedPreferences.registerOnSharedPreferenceChangeListener(this)
            }
            observers.add(onKeyChanged)
        }

        fun unregisterChangeListener(onKeyChanged: (String) -> Unit) {
            observers.remove(onKeyChanged)
            if (observers.isEmpty()) {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
            }
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            observers.forEach { observer ->
                observer(key)
            }
        }
    }
}
