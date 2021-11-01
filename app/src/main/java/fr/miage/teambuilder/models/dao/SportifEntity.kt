package fr.miage.teambuilder.models.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SportifEntity(
    @PrimaryKey val uid: String,
    val email: String? = null,
    val nom: String? = null,
    val prenom: String? = null,
    val age: Int? = null,
    val taille: Int? = null,
    val sportFavori: String? = null,
    val poste: String? = null,
    val sexe: String? = null,
    val niveau: String? = null,
    val isLookingOffer: Boolean = false,
    val userType: String? = null
)