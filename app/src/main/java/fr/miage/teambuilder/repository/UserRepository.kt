package fr.miage.teambuilder.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.miage.teambuilder.models.rest.Club
import fr.miage.teambuilder.models.rest.FirestoreCollections
import fr.miage.teambuilder.models.rest.Sportif
import java.util.*
import javax.inject.Inject

class UserRepository @Inject constructor() {

    val db = Firebase.firestore

    fun createSportif(email:String? = null, nom:String? = null, prenom: String?= null, userType:String? = null, uuid:String){
        val sportif = Sportif(email = email ?: "", nom = nom, prenom = prenom, userType = userType, uid = UUID.randomUUID().toString())
        db.collection(FirestoreCollections.SPORTIFS.colectionName).document(uuid).set(sportif)
    }

    fun createClub(email:String? = null, nom:String? = null, prenom: String?= null, userType:String? = null, uuid:String){
        val club = Club(email = email, uid = UUID.randomUUID().toString())
        db.collection(FirestoreCollections.CLUB.colectionName).document(uuid).set(club)
    }





}

