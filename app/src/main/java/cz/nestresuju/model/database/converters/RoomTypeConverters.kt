package cz.nestresuju.model.database.converters

import androidx.room.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

/**
 * Room type converters for storing unknown types.
 */
class RoomTypeConverters {

    @TypeConverter
    fun toDbTimestamp(dateTime: ZonedDateTime?): Long? = dateTime?.toInstant()?.toEpochMilli()

    @TypeConverter
    fun fromDbTimestamp(timestamp: Long?): ZonedDateTime? = timestamp?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()) }
}