package cz.nestresuju.networking

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

/**
 * Formats dates with RFC 3339 standard.
 */
object Rfc3339ZonedDateTimeJsonAdapter {

    @FromJson
    fun fromJson(value: String?): ZonedDateTime? {
        return value?.let {
            ZonedDateTime.parse(it)
        }
    }

    @ToJson
    fun toJson(value: ZonedDateTime?): String? {
        return value?.let {
            DateTimeFormatter.ISO_ZONED_DATE_TIME.format(it)
        }
    }
}