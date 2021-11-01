package fr.miage.teambuilder.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.miage.teambuilder.models.dao.EquipeDao
import fr.miage.teambuilder.models.dao.EquipeEntity
import fr.miage.teambuilder.models.dao.SportifEntity
import fr.miage.teambuilder.models.rest.Equipe
import fr.miage.teambuilder.models.rest.FirestoreCollections
import fr.miage.teambuilder.models.rest.Sportif
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EquipeRepository @Inject constructor(val equipeDao: EquipeDao) {

    val db = Firebase.firestore

    suspend fun fetchEquipes() {
        val equipes = db.collection(FirestoreCollections.EQUIPE.colectionName).get().await().toObjects(
            Equipe::class.java)
        equipeDao.insertEquipes(equipes.map { it.toEntity() })

    }

    fun getEquipes(): Flow<List<EquipeEntity>> {
        return equipeDao.getEquipes()
    }
}