package fr.miage.teambuilder.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EquipeDao {

    @Query("SELECT * FROM EquipeEntity")
    fun getEquipes(): Flow<List<EquipeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEquipes(equipesEntities: List<EquipeEntity>)
}