package cz.nestresuju.common.extensions

import com.airbnb.epoxy.EpoxyController
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Extensions for Epoxy models & controllers.
 */

fun <T> EpoxyController.controllerProperty(initialValue: T): ReadWriteProperty<Any?, T> = object : ObservableProperty<T>(initialValue) {
    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
        requestModelBuild()
    }
}