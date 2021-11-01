package fr.miage.teambuilder.models.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class EquipeEntity(
    @PrimaryKey val uid:String,
    val uidClub: String = UUID.randomUUID().toString(),
    val nomEquipe: String? = null,
    val dateFondation: String? = null,
    val telephoneReferant: String? = null,
    val slogan: String? = null,
    val adresse: String? = null,
    val codePostal: String? = null,
    val typesSport: ArrayList<String> = arrayListOf(),
    val sportifAlreadyLiked: ArrayList<String> = arrayListOf(),
    val sportifAlreadyUnliked: ArrayList<String> = arrayListOf()
)