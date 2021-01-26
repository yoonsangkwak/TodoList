package site.yoonsang.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import site.yoonsang.todolist.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        binding.signupButton.setOnClickListener {
            signup()
        }
    }

    private fun signup() {
        auth?.createUserWithEmailAndPassword(
            binding.emailEdittext.text.toString(),
            binding.passwordEdittext.text.toString()
        )?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Firebase.auth.currentUser
                    ?.sendEmailVerification()
                    ?.addOnCompleteListener { verifyTask ->
                        if (verifyTask.isSuccessful) {
                            // Creating a user account
                            moveMainPage(task.result?.user)
                        }
                    }
            } else if (task.exception?.message.isNullOrBlank()) {
                // Show the error message
                Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
            } else {
                // Login if you have account
//                signinEmail()
            }
        }
    }

    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}