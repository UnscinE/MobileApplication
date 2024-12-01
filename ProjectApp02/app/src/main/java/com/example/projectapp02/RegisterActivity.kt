package com.example.projectapp02

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projectapp02.ui.Account.AccountFragment
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity()  {
    var mAuth: FirebaseAuth? = null
    var edtEmail : EditText? = null
    var edtPass : EditText? = null
    var edtConPass : EditText? = null
    var btnRegis : Button? = null
    var btnCancel : Button? = null
    private val TAG: String = "Register Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_regis)
        init()
        btnRegis?.setOnClickListener {
            val email = edtEmail?.text.toString().trim { it <= ' ' }
            val password = edtPass?.text.toString().trim { it <= ' ' }

//ทําการตรวจสอบก่อนว่ามีข้อมูลหรือไม่
            if (email.isEmpty()) {
                Toast.makeText(this,"Please enter your email address.",
                    Toast.LENGTH_LONG).show()
                Log.d(TAG, "Email was empty!")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this,"Please enter your password.",Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Password was empty!")
                return@setOnClickListener
            }
//กรณีที5มีข้อมูล จะทําการตรวจสอบเงื5อนไขอื5น ๆ ก่อนทําการ create user
            mAuth!!.createUserWithEmailAndPassword(email,
                password).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    if (password.length < 6) { // ตรวจสอบความยาวของ password
                        Toast.makeText(this,"Password too short! Please enter minimum 6 characters.",Toast.LENGTH_LONG).show()
                                Log.d(TAG, "Enter password less than 6 characters.")
                    } else {
                        Toast.makeText(this,"Authentication Failed: " +
                                task.exception!!.message,Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Authentication Failed: " +
                                task.exception!!.message)
                    }
                } else {
                    Toast.makeText(this,"Create account successfully!",Toast.LENGTH_LONG).show()
                            Log.d(TAG, "Create account successfully!")
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }
        btnCancel?.setOnClickListener{
            var intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
    fun init(){
        edtEmail = findViewById(R.id.Regis_edtEmail)
        edtPass = findViewById(R.id.Regis_edtPass)
        edtConPass = findViewById(R.id.Regis_edtConPass)
        btnRegis = findViewById(R.id.Regis_btnRegis)
        btnCancel = findViewById(R.id.Regis_btnCancel)
    }
}