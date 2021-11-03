package fr.miage.teambuilder.ui.sportif

import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import fr.miage.teambuilder.R
import fr.miage.teambuilder.utils.SpinnerManager
import java.util.*

@AndroidEntryPoint
class SportifProfilScreen : AppCompatActivity() {

    private val viewModel: SportifProfilViewModel by viewModels()

    lateinit var sportsSpinner: Spinner
    lateinit var posteSpinner: Spinner
    lateinit var geoSpinner: Spinner
    lateinit var sexeSpinner: Spinner
    lateinit var ageTextField: TextInputEditText
    lateinit var toggle: Switch
    lateinit var validateButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sportif_profil_screen)

        sportsSpinner = findViewById(R.id.sportSpinner)
         posteSpinner = findViewById(R.id.posteSpinner)
         geoSpinner = findViewById(R.id.geoSpinner)
         sexeSpinner = findViewById(R.id.sexeSpinner)
         ageTextField = findViewById(R.id.editTextAge)
         toggle = findViewById(R.id.switchOfferLooking)
         validateButton = findViewById(R.id.validate)

        SpinnerManager.setAdaptaterSpinner(this, R.array.sports, sportsSpinner)
        SpinnerManager.setAdaptaterSpinner(this, R.array.noPoste, posteSpinner)
        SpinnerManager.setAdaptaterSpinner(this, R.array.niveau, geoSpinner)
        SpinnerManager.setAdaptaterSpinner(this, R.array.sexes, sexeSpinner)

        viewModel.ldScreenState.observe(this, androidx.lifecycle.Observer {
            when(it){
                SportifProfilViewModel.State.SuccessUpdate -> {
                    SuccessUpdate()
                }
                SportifProfilViewModel.State.FailUpdate -> {
                    FailsUpdate()
                }
            }
        })

        validateButton.setOnClickListener {

            val sportPratique = sportsSpinner.selectedItem.toString()
            val postePratique = posteSpinner.selectedItem.toString()
            val region = geoSpinner.selectedItem.toString()
            val sexe = sexeSpinner.selectedItem.toString()
            val age = ageTextField.text.toString()
            val rechercheActive = toggle.isChecked

            viewModel.updateSportifProfil(sportPratique =  sportPratique, poste =  postePratique, region = region, genre = sexe, age = age, rechercheActive = rechercheActive)
        }
    }



    private fun SuccessUpdate(){
        Toast.makeText(this, getString(R.string.update_success), Toast.LENGTH_LONG).show()
    }

    private fun FailsUpdate(){
        Toast.makeText(this, getString(R.string.update_faile), Toast.LENGTH_LONG).show()

    }
}