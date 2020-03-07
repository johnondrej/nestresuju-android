package cz.nestresuju.common

import android.content.Context
import android.content.Intent

/**
 * Extensions for common tasks related to Android [Context].
 */

fun Context.startActivity(activityClass: Class<*>) {
    val intent = Intent(this, activityClass)
    startActivity(intent)
}