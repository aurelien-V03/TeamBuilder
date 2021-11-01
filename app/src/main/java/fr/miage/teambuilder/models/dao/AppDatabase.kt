package fr.miage.teambuilder.models.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [SportifEntity::class, MatchEntity::class, EquipeEntity::class, ClubEntity::class], version = 1)
@TypeConverters(StringConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun sportifDao(): SportifDao
    abstract fun clubDao(): ClubDao
    abstract fun equipeDao(): EquipeDao
    abstract fun matchDao(): MatchDao

}