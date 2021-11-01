package fr.miage.teambuilder.models.rest

import fr.miage.teambuilder.models.dao.ClubEntity
import java.util.*
import kotlin.collections.ArrayList

data class Club constructor
    (
    var uid: String? = null,
    var email: String? = null,
    var userType: String = "club",
    var nomClub: String? = null,
    var equipesUid: ArrayList<String> = arrayListOf()
) {
    fun toEntity(): ClubEntity {
        return ClubEntity(
            email = email,
            userType = userType,
            nomClub = nomClub,
            equipesUid = equipesUid,
            uid = uid ?: UUID.randomUUID().toString()
        )
    }
}