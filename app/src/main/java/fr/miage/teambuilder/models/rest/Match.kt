package fr.miage.teambuilder.models.rest

import fr.miage.teambuilder.models.dao.MatchEntity
import java.util.*

class Match constructor(
    val uid: String?= null,
    val uidSportif: String? = null,
    val uidEquipe: String? = null
) {
    fun toEntity(): MatchEntity{
        return MatchEntity(
            uid = uid ?: UUID.randomUUID().toString(),
            uidSportif = uidSportif,
            uidEquipe = uidEquipe
        )
    }
}