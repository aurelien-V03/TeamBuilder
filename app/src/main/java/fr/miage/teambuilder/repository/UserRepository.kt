package fr.miage.teambuilder.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.miage.teambuilder.models.rest.Club
import fr.miage.teambuilder.models.rest.FirestoreCollections
import fr.miage.teambuilder.models.rest.Sportif
import fr.miage.teambuilder.models.rest.User
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class UserRepository @Inject constructor() {

    val db = Firebase.firestore

    fun createSportif(email:String, nom:String? = null, prenom: String?= null, userType:String, uuid:String){
        val sportif = Sportif(email = email ?: "", nom = nom, prenom = prenom, userType = userType, uid = UUID.randomUUID().toString())
        db.collection(FirestoreCollections.SPORTIFS.colectionName).document(uuid).set(sportif)
        createUser(email, uuid, userType)
    }

    private fun createUser(email: String, uid: String, userType: String){
        val user = User(email = email, userType = userType, uid = uid)
        db.collection(FirestoreCollections.USER.colectionName).document(uid).set(user)
    }

    fun createClub(email:String, nom:String? = null, prenom: String?= null, userType:String, uuid:String){
        val club = Club(email = email, uid = UUID.randomUUID().toString())
        db.collection(FirestoreCollections.CLUB.colectionName).document(uuid).set(club)
        createUser(email, uuid, userType)
    }

    suspend fun getuserType(uid: String): String{
        val user = db.collection(FirestoreCollections.USER.colectionName).document(uid).get().await().toObject(User::class.java)
        return user?.userType ?: ""
    }








}

