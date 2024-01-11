package usecases

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.auth

class LoginEmailPasswordUsecase {
    suspend operator fun invoke(email: String, password: String): AuthResult {
        val firebaseAuth = Firebase.auth
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }
}