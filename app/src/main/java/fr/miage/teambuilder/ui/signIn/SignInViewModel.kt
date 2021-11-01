package fr.miage.teambuilder.ui.signIn

import android.app.Activity
import android.content.Context
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
class SignInViewModel @Inject constructor(val userRepository: UserRepository): ViewModel() {

        private val mldState: MutableLiveData<State> = MutableLiveData()
         val ldState: LiveData<State> = mldState

        private lateinit var auth: FirebaseAuth


        fun signIn(email: String, password:String, activity: Activity){

            if(email.isNullOrBlank() || password.isNullOrBlank()){
                this.mldState.value = State.InputIncorrect(email.length > 0,password.length > 0)
            }
            else{
                auth = Firebase.auth
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            mldState.value = State.SuccessSignIn

                        } else {
                            mldState.value = State.FailSignIn
                        }
                    }

            }
        }

        fun signUp(){

        }

    sealed class State{
            class InputIncorrect(val isEmailCorrect:Boolean, val isPasswordCorrect: Boolean): State()
            object SuccessSignIn: State()
            object FailSignIn: State()
    }
}