package fr.miage.teambuilder.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.miage.teambuilder.models.dao.SportifEntity
import fr.miage.teambuilder.repository.ClubRepository
import fr.miage.teambuilder.repository.EquipeRepository
import fr.miage.teambuilder.repository.MatchRepository
import fr.miage.teambuilder.repository.SportifRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val sportifRepository: SportifRepository,
    val clubRepository: ClubRepository,
    val equipeRepository: EquipeRepository,
    val matchRepository: MatchRepository
    ): ViewModel() {


    val sportifs = sportifRepository.getSportifs()
    val club = clubRepository.getClubs()

   init {

   }

    fun initialisation(){
        viewModelScope.launch {
            sportifRepository.fetchSportifs()
            clubRepository.fetchClubs()
            equipeRepository.fetchEquipes()
            matchRepository.fetchMatch()
        }
    }


    fun acceptMatch(){

    }

    fun refuseMatch(){

    }
}