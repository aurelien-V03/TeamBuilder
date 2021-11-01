package fr.miage.teambuilder.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.miage.teambuilder.models.dao.EquipeEntity
import fr.miage.teambuilder.models.dao.MatchDao
import fr.miage.teambuilder.models.dao.MatchEntity
import fr.miage.teambuilder.models.rest.Equipe
import fr.miage.teambuilder.models.rest.FirestoreCollections
import fr.miage.teambuilder.models.rest.Match
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MatchRepository @Inject constructor(val matchDao: MatchDao) {
    val db = Firebase.firestore

    suspend fun fetchMatch() {
        val matchs = db.collection(FirestoreCollections.MATCH.colectionName).get().await().toObjects(
            Match::class.java)
        matchDao.insertMatchEntities(matchs.map { it.toEntity() })
    }

    fun getMatchs(): Flow<List<MatchEntity>> {
        return matchDao.getMatchEntities()
    }
}