package fr.miage.teambuilder.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.miage.teambuilder.models.dao.SportifEntity
import fr.miage.teambuilder.repository.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val sportifRepository: SportifRepository,
    val clubRepository: ClubRepository,
    val equipeRepository: EquipeRepository,
    val matchRepository: MatchRepository,
    val userRepository: UserRepository
    ): ViewModel() {

    val userUid = userRepository.getUserUid()

    val sportifs = sportifRepository.getSportifs()
    val equipe = equipeRepository.getEquipes().map { it.filter { it.sportifAlreadyLiked.contains(userUid) == false &&  it.sportifAlreadyUnliked.contains(userUid) == false } }

    fun initialisation(){
        viewModelScope.launch {
            sportifRepository.fetchSportifs()
            clubRepository.fetchClubs()
            equipeRepository.fetchEquipes()
            matchRepository.fetchMatch()
        }
    }


    fun sportifLike(hasAccepted: Boolean,uidEquipe: String){
        val userUid = userRepository.getUserUid()
        viewModelScope.launch {
            equipeRepository.sportifLike(hasAccepted, userUid ,uidEquipe)
        }
    }


}