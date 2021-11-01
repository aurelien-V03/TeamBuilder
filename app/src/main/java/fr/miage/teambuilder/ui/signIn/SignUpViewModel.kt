package fr.miage.teambuilder.ui.signIn

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.miage.teambuilder.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(val userRepository: UserRepository): ViewModel() {
    private lateinit var auth: FirebaseAuth

    private val mldState: MutableLiveData<State> = MutableLiveData()
    val ldState: LiveData<State> = mldState

    fun signUp(email:String, nom:String, prenom:String, password: String, confirmPassword: String, userType: String, activity: Activity){
        val isEmailValid = !email.isNullOrBlank()
        val isNomValid = !nom.isNullOrBlank()
        val isPrenomValid = !prenom.isNullOrBlank()
        val isPasswordValid = (!password.isNullOrBlank()) && (password == confirmPassword)

        if(isEmailValid && isNomValid && isPrenomValid && isPasswordValid){
            auth = Firebase.auth
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        when(userType){
                            "Club" -> {
                                userRepository.createClub(email = email, nom = nom, prenom = prenom, userType = userType, uuid = user?.uid ?: "")

                            }
                            "Sportif" -> {
                                userRepository.createSportif(email = email, nom = nom, prenom = prenom, userType = userType, uuid = user?.uid ?: "")
                            }
                        }
                        mldState.value = State.SuccessSignUp
                    } else {
                        mldState.value = State.FailSignUp(task.exception?.message ?: "Error")
                    }
                }
        }
        else{
            mldState.value = State.IncorrectInput(isEmailValid, isNomValid, isPrenomValid, isPasswordValid)
        }
    }

    sealed class State {
        object SuccessSignUp: State()
        class FailSignUp(val message: String): State()
        class IncorrectInput(val isEmailCorrect: Boolean, val samePassword: Boolean, val isNameCorrect: Boolean, val isPrenomCorrect: Boolean): State()
    }

}