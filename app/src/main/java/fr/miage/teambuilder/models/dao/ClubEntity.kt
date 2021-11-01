package fr.miage.teambuilder.models.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class ClubEntity (
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    val email: String? = null,
    val userType: String = "club",
    val nomClub: String? = null,
    val equipesUid: ArrayList<String> = arrayListOf()
)