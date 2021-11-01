package fr.miage.teambuilder.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SportifDao {


    @Query("SELECT * FROM SportifEntity")
    fun getSportifs(): Flow<List<SportifEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSportif(userEntities: List<SportifEntity>)
}