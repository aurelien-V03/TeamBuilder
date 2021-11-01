package fr.miage.teambuilder.repository

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.miage.teambuilder.models.dao.AppDatabase
import fr.miage.teambuilder.models.dao.SportifDao
import fr.miage.teambuilder.models.dao.SportifEntity
import fr.miage.teambuilder.models.rest.FirestoreCollections
import fr.miage.teambuilder.models.rest.Sportif
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SportifRepository @Inject constructor(val sportifDao: SportifDao) {

    val db = Firebase.firestore

     suspend fun fetchSportifs() {
         val sportifs = db.collection(FirestoreCollections.SPORTIFS.colectionName).get().await().toObjects(Sportif::class.java)
         sportifDao.insertSportif(sportifs.map { it.toEntity() })
    }

     fun getSportifs(): Flow<List<SportifEntity>> {
       return sportifDao.getSportifs()
    }
}