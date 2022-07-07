package com.example.android.app.activity

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.android.app.ForgetPasswordActivity
import com.example.android.app.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var loginEmail : EditText
    private lateinit var loginPassword : EditText
    private lateinit var loginRegisterBtn : TextView
    private lateinit var loginLoginBtn : Button
    private lateinit var resetPassword : TextView
    private lateinit var back_arrow : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Remove Action Bar
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        loginEmail = findViewById(R.id.login_email)
        loginPassword = findViewById(R.id.login_password)
        loginRegisterBtn = findViewById(R.id.login_register_btn)
        loginLoginBtn = findViewById(R.id.login_login_btn)
        resetPassword = findViewById(R.id.reset_password)
        back_arrow = findViewById(R.id.back_arrow)

        back_arrow.setOnClickListener {
            onBackPressed()
        }


        loginRegisterBtn.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            //finish()
        }

        resetPassword.setOnClickListener{
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }

        //Logged In
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        logIn()

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to close this app?")
        builder.setPositiveButton("Yes",{ dialogInterface : DialogInterface, i:Int ->
            finish()
        })
        builder.setNegativeButton("No",{ dialogInterface : DialogInterface, i:Int ->
        })
        builder.create()
        builder.show()
    }

    private fun logIn(){
        loginLoginBtn.setOnClickListener {
            var email: String = loginEmail.text.toString()
            var password: String = loginPassword.text.toString()

            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this@LoginActivity, "Please fill all the fields", Toast.LENGTH_LONG).show()
            }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            }else if(password.length<6){
                Toast.makeText(this, "Password must be atleast 6", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, OnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_LONG).show()
                        val intent = Intent(this,HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}



