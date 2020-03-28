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
    fun toDbTimestamp(dateTime: ZonedDateTime): Long = dateTime.toInstant().toEpochMilli()

    @TypeConverter
    fun fromDbTimestamp(timestamp: Long): ZonedDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault())
}