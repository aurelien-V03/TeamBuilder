package fr.miage.teambuilder.ui.signIn

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import fr.miage.teambuilder.R
import fr.miage.teambuilder.enums.UserType
import fr.miage.teambuilder.ui.home.HomeScreen
import fr.miage.teambuilder.ui.home.HomeViewModel

@AndroidEntryPoint
class SignInScreen : AppCompatActivity() {

    private val viewModel: SignInViewModel by viewModels()

    lateinit var emailTextField: TextInputEditText
    lateinit var passwordTextField: TextInputEditText
    lateinit var textFieldMessage: TextView
    lateinit var buttonSignIn: Button
    lateinit var buttonSignUp: Button

    lateinit var content: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in_screen)

        viewModel.ldState.observe(this) {
            when(it){
                is SignInViewModel.State.InputIncorrect -> {
                    onIncorectInput(it)
                }
                SignInViewModel.State.FailSignIn -> {
                    onFailSignIn()
                }
                is SignInViewModel.State.SuccessSignIn -> {
                    onSuccessSignIn(it.connectedAsSportif)
                }
            }
        }

        emailTextField = findViewById(R.id.editTextEmail)
        passwordTextField = findViewById(R.id.editTextPassword)
        buttonSignIn = findViewById(R.id.buttonSignIn)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        textFieldMessage = findViewById(R.id.textViewSignUpMessage)

        content = findViewById(R.id.signInContent)

        buttonSignIn.setOnClickListener {
            viewModel.signIn(emailTextField.text.toString(), passwordTextField.text.toString(), this)
        }

        buttonSignUp.setOnClickListener {
            onGoToSignUpScreen()
        }

    }

    fun onFailSignIn(){
        textFieldMessage.text =  resources.getString(R.string.sign_in_incorrect_input)
    }

    fun onSuccessSignIn(connectedAsSportif: Boolean){

        val userType = if(connectedAsSportif) UserType.SPORTIF else UserType.CLUB

        Snackbar.make(content, R.string.sign_in_success_sign_in, Snackbar.LENGTH_LONG).show()
        val intent = Intent(this, HomeScreen::class.java).apply {
            putExtra("userType", userType)
        }
        startActivity(intent)
    }

    fun onIncorectInput(input: SignInViewModel.State.InputIncorrect){
        textFieldMessage.text = resources.getString(R.string.sign_in_incorrect_input)

    }
    fun onGoToSignUpScreen(){
        val intent = Intent(this, SignUpScreen::class.java).apply {
        }
        startActivity(intent)
    }
}
