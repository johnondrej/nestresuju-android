package cz.nestresuju.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import io.multimoon.colorful.Colorful
import io.multimoon.colorful.CustomThemeColor
import io.multimoon.colorful.Defaults

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

fun Context.styleDefaults(
    style: Int,
    primaryColor: Int,
    primaryDarkColor: Int,
    accentColor: Int
): Defaults {
    val primaryThemeColor = CustomThemeColor(
        this,
        style,
        style,
        primaryColor,
        primaryDarkColor
    )

    val accentThemeColor = CustomThemeColor(
        this,
        style,
        style,
        accentColor,
        accentColor
    )

    return Defaults(
        primaryColor = primaryThemeColor,
        accentColor = accentThemeColor,
        useDarkTheme = false,
        translucent = false
    )
}

fun Activity.changeStyle(
    style: Int,
    primaryColor: Int,
    primaryDarkColor: Int,
    accentColor: Int
) {
    val defaults = styleDefaults(style, primaryColor, primaryDarkColor, accentColor)

    Colorful().edit()
        .setPrimaryColor(defaults.primaryColor)
        .setAccentColor(defaults.accentColor)
        .apply(this) {
            recreate()
        }
}
