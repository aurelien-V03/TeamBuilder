package fr.miage.teambuilder.models.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class MatchEntity (
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    val uidSportif: String? = null,
    val uidEquipe: String? = null
    )