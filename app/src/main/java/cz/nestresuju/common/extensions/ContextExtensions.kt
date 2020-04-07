package cz.nestresuju.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
 * Extensions for common tasks related to Android [Context].
 */

fun Context.startActivity(activityClass: Class<*>) {
    val intent = Intent(this, activityClass)
    startActivity(intent)
}

fun Context.hideKeyboard(view: View) {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.dp(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

fun Context.dp(dp: Int) = dp(dp.toFloat())

fun Fragment.dp(dp: Float) = requireContext().dp(dp)

fun Fragment.dp(dp: Int) = requireContext().dp(dp)
