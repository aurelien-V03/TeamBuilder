package fr.miage.teambuilder.ui.signIn

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.miage.teambuilder.enums.UserType
import fr.miage.teambuilder.repository.UserRepository
import kotlinx.coroutines.launch
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
                            user?.let {
                                getUserType(it.uid)
                            }
                        } else {
                            mldState.value = State.FailSignIn
                        }
                    }
            }
        }
    fun getUserType(userUid: String)
    {
        viewModelScope.launch {
            val userType = userRepository.getuserType(userUid)
            userRepository.addUserUid(userUid)
            if(userType == UserType.CLUB.type){
                mldState.value = State.SuccessSignIn(connectedAsSportif = false)
            }
            else{
                mldState.value = State.SuccessSignIn(connectedAsSportif = true)
            }
        }
    }



    sealed class State{
            class InputIncorrect(val isEmailCorrect:Boolean, val isPasswordCorrect: Boolean): State()
            class SuccessSignIn(val connectedAsSportif: Boolean): State()
            object FailSignIn: State()
    }
}