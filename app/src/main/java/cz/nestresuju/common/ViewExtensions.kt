package cz.nestresuju.common

import android.view.View

/**
 * Extensions on [View]s.
 */

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.INVISIBLE
    }