package fr.miage.teambuilder.ui.home

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import fr.miage.teambuilder.R
import fr.miage.teambuilder.enums.UserType
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreen  : AppCompatActivity() {

    val viewModel: HomeViewModel by viewModels()

    lateinit var acceptButton: MaterialButton
    lateinit var refuseButton: MaterialButton
    lateinit var sportifProfil: LinearLayout
    lateinit var clubProfil: LinearLayout

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.club_sportif_profil)

        acceptButton = findViewById(R.id.buttonAccept)
        refuseButton = findViewById(R.id.buttonRefuse)
        sportifProfil = findViewById(R.id.sportifDetail)
        clubProfil = findViewById(R.id.equipeDetail)

       viewModel.initialisation()

        val userType = intent.getSerializableExtra("userType")
        if(userType == UserType.CLUB){
            sportifProfil.visibility = View.GONE
            clubProfil.visibility = View.VISIBLE
        }
        else{
            sportifProfil.visibility = View.VISIBLE
            clubProfil.visibility = View.GONE
        }


        acceptButton.setOnClickListener {

        }

        refuseButton.setOnClickListener {

        }


    }
}