package fr.miage.teambuilder.models.rest

import fr.miage.teambuilder.models.dao.SportifEntity
import java.util.*

class Sportif constructor(
    var uid: String? = null,
    var email: String? = null,
    var nom: String? = null,
    var prenom: String? = null,
    var age: Int? = null,
    var taille: Int? = null,
    var sportFavori: String? = null,
    var poste: String? = null,
    var sexe: String? = null,
    var niveau: String? = null,
    var lookingForOffer: Boolean? = false,
    var userType: String? = null
) {
    fun toEntity():SportifEntity{
        return SportifEntity(
            uid ?: UUID.randomUUID().toString(), email, nom, prenom, age, taille, sportFavori, poste, sexe, niveau, lookingForOffer ?: false, userType
        )
    }
}