package com.example.projectapp02

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.projectapp02.databinding.ActivityLoginBinding
import com.example.projectapp02.ui.Calendar.CalendarFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    var edtEmail: EditText? = null
    var edtPass: EditText? = null
    var btnLogin: Button? = null
    var btnRegis: Button? = null
    private lateinit var binding: ActivityLoginBinding
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        var mAuth: FirebaseAuth? = null
        val TAG: String = "main activity"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

        mAuth = FirebaseAuth.getInstance()
        if (mAuth!!.currentUser != null){
            Log.d(TAG,"Continue with: "+mAuth!!.currentUser!!.email)

            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val  email = edtEmail?.text.toString().trim(){it <=' '}
            val password = edtPass?.text.toString().trim(){it <= ' '}
            fragmentManager = supportFragmentManager
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        btnLogin?.setOnClickListener {
            val email = edtEmail?.text.toString().trim { it <= ' ' }
            val password = edtPass?.text.toString().trim { it <= ' ' }
// ทําการตรวจสอบก่อนว่ามีข้อมูลหรือไม่ ก่อนที5จะไปตรวจสอบค่า
            if (email.isEmpty()) {
                Toast.makeText(this,"Please enter your email address.",Toast.LENGTH_LONG).show()
                Log.d(TAG, "Email was empty!")
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                Toast.makeText(this,"Please enter your password.",Toast.LENGTH_LONG).show()
                Log.d(TAG, "Password was empty!")
                return@setOnClickListener
            }

//ทําการตรวจสอบค่าที5กรอกกับค่าจาก Firebase Authentication
            mAuth!!.signInWithEmailAndPassword(email,
                password).addOnCompleteListener { task ->

//กรณีที5ไม่ผ่านการตรวจสอบ

                if (!task.isSuccessful) {

//ตรวจสอบความยาวของ password ว่าน้อยกว่า 6 ไหม
                    if (password.length < 6) {

                        edtPass?.error = "Please check your password.Password must have minimum 6 characters."
                        Log.d(TAG, "Enter password less than 6 characters.")
                    } else {
                        Toast.makeText(this,"Authentication Failed: " +
                                task.exception!!.message,Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Authentication Failed: " +
                                task.exception!!.message)
                    }
                } else { //กรณีที5อีเมลและรหัสถูกต้อง
                    Toast.makeText(this,"Sign in successfully!",Toast.LENGTH_LONG).show()
                    Log.d(TAG, "Sign in successfully!")
                    binding = ActivityLoginBinding.inflate(layoutInflater)
                    setContentView(binding.root)

                    val  email = edtEmail?.text.toString().trim(){it <=' '}
                    val password = edtPass?.text.toString().trim(){it <= ' '}

                    fragmentManager = supportFragmentManager
                    var intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        btnRegis?.setOnClickListener {
            var intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    fun init(){
        edtEmail = findViewById(R.id.edtEmail)
        edtPass = findViewById(R.id.edtPass)
        btnLogin = findViewById(R.id.Login_btnLogin)
        btnRegis = findViewById(R.id.Login_btnRegis)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}