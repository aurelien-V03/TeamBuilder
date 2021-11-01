package fr.miage.teambuilder.models.rest

import fr.miage.teambuilder.models.dao.EquipeEntity
import java.util.*
import kotlin.collections.ArrayList

class Equipe constructor(
    val uid: String? = null,
    val uidClub: String?= null,
    val nomEquipe: String? = null,
    val dateFondation: String? = null,
    val telephoneReferant: String? = null,
    val slogan: String? = null,
    val adresse: String? = null,
    val codePostal: String? = null,
    val typesSport: ArrayList<String> = arrayListOf(),
    val sportifAlreadyLiked: ArrayList<String> = arrayListOf(),
    val sportifAlreadyUnliked: ArrayList<String> = arrayListOf(),
){
    fun toEntity(): EquipeEntity{
        return EquipeEntity(
            uid = uid ?: UUID.randomUUID().toString(),
            uidClub = uidClub ?: "random name",
            nomEquipe = nomEquipe,
            dateFondation = dateFondation,
            telephoneReferant = telephoneReferant,
            slogan = slogan,
            adresse = adresse,
            codePostal = codePostal,
            typesSport = typesSport,
            sportifAlreadyLiked = sportifAlreadyLiked,
            sportifAlreadyUnliked = sportifAlreadyUnliked
        )
    }

}