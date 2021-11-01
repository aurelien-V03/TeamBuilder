package fr.miage.teambuilder.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.miage.teambuilder.models.dao.ClubDao
import fr.miage.teambuilder.models.dao.ClubEntity
import fr.miage.teambuilder.models.dao.EquipeEntity
import fr.miage.teambuilder.models.rest.Club
import fr.miage.teambuilder.models.rest.Equipe
import fr.miage.teambuilder.models.rest.FirestoreCollections
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ClubRepository @Inject constructor(val clubDao: ClubDao) {
    val db = Firebase.firestore

    suspend fun fetchClubs() {
        val clubs = db.collection(FirestoreCollections.CLUB.colectionName).get().await().toObjects(Club::class.java)
        clubDao.insertClub(clubs.map { it.toEntity() })
    }

    fun getClubs(): Flow<List<ClubEntity>> {
        return clubDao.getClub()
    }
}