package fr.miage.teambuilder.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.AndroidEntryPoint
import fr.miage.teambuilder.R
import fr.miage.teambuilder.enums.UserType
import fr.miage.teambuilder.models.dao.EquipeEntity
import fr.miage.teambuilder.models.dao.SportifEntity
import fr.miage.teambuilder.ui.equipe.EquipeProfil
import fr.miage.teambuilder.ui.signIn.SignUpScreen
import fr.miage.teambuilder.ui.sportif.SportifProfilScreen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreen  : AppCompatActivity() {

    val viewModel: HomeViewModel by viewModels()

    lateinit var acceptButton: MaterialButton
    lateinit var refuseButton: MaterialButton
    lateinit var sportifProfil: CardView
    lateinit var clubProfil: CardView
    lateinit var connectedAsTextView: TextView


    // empty research
    var emptyResearch: ConstraintLayout? = null

    // sportif detail
    var textviewName: TextView? = null; var textviewAge: TextView? = null ; var textviewTaille: TextView? = null; var textviewPoste: TextView? = null; var textviewNiveau: TextView? = null

    // club detail
    var textViewNameEquipe: TextView?= null; var textViewFondationEquipe: TextView? = null;var textViewTelephoneEquipe: TextView? = null;var textViewSportsEquipe: TextView? = null

    var equipeListToDisplay: List<EquipeEntity> = mutableListOf()
    var currentEquipeDisplayed: EquipeEntity? = null

    var sportifsListToDisplay: List<SportifEntity> = mutableListOf()
    var currentSportifDisplayed: SportifEntity? = null

    var connectedAsSportif: Boolean? = null


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

        textViewNameEquipe = findViewById(R.id.textviewEquipeName)
        textViewFondationEquipe = findViewById(R.id.textviewClubDateFondation)
        textViewTelephoneEquipe = findViewById(R.id.textviewEquipePhone)
        textViewSportsEquipe = findViewById(R.id.textviewEquipeSports)

        emptyResearch = findViewById(R.id.empty_research)
        viewModel.initialisation()

        val userType = intent.getSerializableExtra("userType")

        if(userType == UserType.CLUB){
            connectedAsSportif = false
            clubProfil.visibility = View.GONE
            connectedAsTextView.text = resources.getString(R.string.home_screen_connected_as, resources.getString(R.string.home_screen_club))
            displayClubContent()

        }
        else{
            connectedAsSportif = true
            sportifProfil.visibility = View.GONE
            connectedAsTextView.text = resources.getString(R.string.home_screen_connected_as, resources.getString(R.string.home_screen_sportif))
            displaySportifContent()
        }

        acceptButton.setOnClickListener {
            onMatch(true, userType as UserType)
        }

        refuseButton.setOnClickListener {
            onMatch(false, userType as UserType)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater =  menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.action_edit_settings -> {
            connectedAsSportif?.let {
                if(it){
                    goToSportifEditSettings()
                }
                else{
                    goToEquipeSettings()
                }
            }
            true
        }
        else -> {
             super.onOptionsItemSelected(item)
        }
    }

    fun goToSportifEditSettings(){
        val intent = Intent(this, SportifProfilScreen::class.java).apply {
        }
        startActivity(intent)
    }

    fun goToEquipeSettings(){
        val intent = Intent(this, EquipeProfil::class.java).apply {
        }
        startActivity(intent)
    }
    fun onMatch(hasAccepted: Boolean, userType: UserType){
        when(userType){
            UserType.CLUB -> {
                val newList = sportifsListToDisplay.filter { it.uid != currentSportifDisplayed?.uid }
                sportifsListToDisplay = newList
                currentSportifDisplayed = sportifsListToDisplay.firstOrNull()
                updateClubContent()
            }
            UserType.SPORTIF -> {
                currentEquipeDisplayed?.let { viewModel.sportifLike(hasAccepted, it.uid) }
                val newList = equipeListToDisplay.filter { it.uid != currentEquipeDisplayed?.uid }
                equipeListToDisplay = newList
                currentEquipeDisplayed = equipeListToDisplay.firstOrNull()
                updateSportifContent()
            }
        }
    }

    // si l'utilisateur connecté est un sportif --> afficher les equipes a matcher
    fun displaySportifContent(){
        lifecycleScope.launch {
            viewModel.equipe.collect {
                equipeListToDisplay = it
                currentEquipeDisplayed = equipeListToDisplay.firstOrNull()
                updateSportifContent()
           }
        }
    }
    fun updateSportifContent(){
        if(currentEquipeDisplayed != null){
            clubProfil.visibility = View.VISIBLE
            textViewNameEquipe?.text = currentEquipeDisplayed?.nomEquipe
            textViewFondationEquipe?.text = currentEquipeDisplayed?.dateFondation
            textViewTelephoneEquipe?.text = currentEquipeDisplayed?.telephoneReferant
            textViewSportsEquipe?.text = currentEquipeDisplayed?.getSportsAsString()
        }
        else if(currentEquipeDisplayed == null && equipeListToDisplay.isEmpty()){
            clubProfil.visibility = View.GONE
            emptyResearch?.visibility = View.VISIBLE

        }
    }

    // si l'utilisateur connecté est un club --> afficher les sportifs a matcher
    fun displayClubContent(){
        lifecycleScope.launch {
             viewModel.sportifs.collect {
                sportifsListToDisplay = it
                currentSportifDisplayed = sportifsListToDisplay.firstOrNull()
                updateClubContent()
            }
        }
    }
    fun updateClubContent(){
        if(currentSportifDisplayed != null){
            sportifProfil.visibility = View.VISIBLE
            textviewName?.text = currentSportifDisplayed?.nom
            textviewAge?.text = currentSportifDisplayed?.age.toString()
            textviewNiveau?.text = currentSportifDisplayed?.niveau ?: "niveau inconnu"
            textviewPoste?.text = currentSportifDisplayed?.poste ?: "poste inconnu"
            textviewTaille?.text = currentSportifDisplayed?.taille.toString()
        }
        else if(currentSportifDisplayed == null && sportifsListToDisplay.isEmpty()){
            sportifProfil.visibility = View.GONE
            emptyResearch?.visibility = View.VISIBLE
        }

    }
}