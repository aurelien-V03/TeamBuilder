package fr.miage.teambuilder.models.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StringConverter {

        @TypeConverter
        fun fromArrayList(value : ArrayList<String>) = Json.encodeToString(value)

        @TypeConverter
        fun toArrayList(value: String) = Json.decodeFromString<ArrayList<String>>(value)


}