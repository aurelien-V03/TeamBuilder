package fr.miage.teambuilder.ui.home

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import fr.miage.teambuilder.R
import fr.miage.teambuilder.enums.UserType
import fr.miage.teambuilder.models.dao.ClubEntity
import fr.miage.teambuilder.models.dao.SportifEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import org.w3c.dom.Text

@AndroidEntryPoint
class HomeScreen  : AppCompatActivity() {

    val viewModel: HomeViewModel by viewModels()

    lateinit var acceptButton: MaterialButton
    lateinit var refuseButton: MaterialButton
    lateinit var sportifProfil: CardView
    lateinit var clubProfil: LinearLayout
    lateinit var connectedAsTextView: TextView

    // sportif detail
    var textviewName: TextView? = null; var textviewAge: TextView? = null ; var textviewTaille: TextView? = null; var textviewPoste: TextView? = null; var textviewNiveau: TextView? = null
    // club detail

    var clubListToDisplay: List<ClubEntity> = mutableListOf()
    var currentClubDisplayed: ClubEntity? = null

    var sportifsListToDisplay: List<SportifEntity> = mutableListOf()
    var currentSportifDisplayed: SportifEntity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.club_sportif_profil)

        acceptButton = findViewById(R.id.buttonAccept)
        refuseButton = findViewById(R.id.buttonRefuse)
        sportifProfil = findViewById(R.id.sportifDetail)
        clubProfil = findViewById(R.id.equipeDetail)
        connectedAsTextView = findViewById(R.id.connectedAs)

        textviewName = findViewById(R.id.textviewName)
        textviewAge = findViewById(R.id.textviewAge)
        textviewTaille = findViewById(R.id.textviewTaille)
        textviewPoste = findViewById(R.id.textviewPoste)
        textviewNiveau = findViewById(R.id.textviewRegion)

        viewModel.initialisation()

        val userType = intent.getSerializableExtra("userType")

        if(userType == UserType.CLUB){
            sportifProfil.visibility = View.VISIBLE
            clubProfil.visibility = View.GONE
            connectedAsTextView.text = resources.getString(R.string.home_screen_connected_as, resources.getString(R.string.home_screen_club))
            displayClubContent()

        }
        else{
            sportifProfil.visibility = View.GONE
            clubProfil.visibility = View.VISIBLE
            connectedAsTextView.text = resources.getString(R.string.home_screen_connected_as, resources.getString(R.string.home_screen_sportif))
            displaySportifContent()
        }


        acceptButton.setOnClickListener {
            when(userType){
                UserType.CLUB -> {
                    sportifsListToDisplay = sportifsListToDisplay.filter { it.uid != currentSportifDisplayed?.uid }
                    currentSportifDisplayed = sportifsListToDisplay.firstOrNull()
                    updateClubContent()
                }
                UserType.SPORTIF -> {

                }
            }
        }

        refuseButton.setOnClickListener {

        }


    }

    // si l'utilisateur connecté est un sportif --> afficher les clubs a matcher
    fun displaySportifContent(){
        lifecycleScope.launch {
           viewModel.club.collect {
               clubListToDisplay = it
               updateSportifContent()
            }
        }
    }
    fun updateSportifContent(){

    }

    // si l'utilisateur connecté est un club --> afficher les sportifs a matcher
    fun displayClubContent(){
        lifecycleScope.launch {
            viewModel.sportifs.collect {
                sportifsListToDisplay = it
                currentSportifDisplayed = sportifsListToDisplay.first()
                updateClubContent()
            }
        }
    }
    fun updateClubContent(){
        if(currentSportifDisplayed != null){
            textviewName?.text = currentSportifDisplayed?.nom
            textviewAge?.text = currentSportifDisplayed?.age.toString()
            textviewNiveau?.text = currentSportifDisplayed?.niveau ?: "niveau inconnu"
            textviewPoste?.text = currentSportifDisplayed?.poste ?: "poste inconnu"
            textviewTaille?.text = currentSportifDisplayed?.taille.toString()
        }
        else{
            sportifProfil.visibility = View.INVISIBLE
        }

    }
}