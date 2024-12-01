package com.example.projectapp02

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.util.AttributeSet
import android.view.autofill.AutofillValue
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.example.projectapp02.extra.DatePickerFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.sql.Time
import java.time.Clock

class AddnoteActivity:AppCompatActivity() {
    private lateinit var dbRef:DatabaseReference
    private lateinit var pickdate: Button
    private lateinit var picktime: Button
    private lateinit var savebtn: Button
    private lateinit var cancelbtn: Button

    private lateinit var edtdate:EditText
    private lateinit var edttime:EditText
    private lateinit var edtinfo:EditText
    private lateinit var edttitle:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnote)

        pickdate = findViewById(R.id.btndatepick)
        picktime = findViewById(R.id.btntimepick)
        savebtn = findViewById(R.id.addnotesave)
        cancelbtn = findViewById(R.id.addnotecancel)

        edtdate = findViewById(R.id.edt_Date)
        edttime = findViewById(R.id.edttime)
        edtinfo = findViewById(R.id.edt_detail)
        edttitle = findViewById(R.id.edt_title)


        dbRef = FirebaseDatabase.getInstance().getReference("Calendar")

        var c = Calendar.getInstance()
        edtdate?.setOnClickListener{
            DatePickerDialog(this,DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->
                edtdate!!.setText("$dayOfMonth/$month/$year")},
                c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show()
        }
        pickdate?.setOnClickListener {
            DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    edtdate?.setText("$dayOfMonth/${month + 1}/$year")
                },
                c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        picktime?.setOnClickListener{
            TimePickerDialog(this,TimePickerDialog.OnTimeSetListener{view, hourOfDay, minute ->
                 edttime?.setText("$hourOfDay : $minute")
                                                                    
            },c.get(Calendar.HOUR),c.get(Calendar.MINUTE),true).show()
        }




        savebtn?.setOnClickListener {
            saveDatedata()
        }

    }
    private fun saveDatedata(){
        val datesaveid = dbRef.push().key!!
        val datatitle = edttitle?.text.toString()
        val datadate = edtdate?.text.toString()
        val datatime = edttime?.text.toString()
        val detail = edtinfo?.text.toString()

        val datasave = DataDate(datesaveid,datadate,datatime,datatitle,detail)

        dbRef.child(datesaveid).setValue(datasave).addOnCompleteListener{
            Toast.makeText(this,"Complete",Toast.LENGTH_LONG).show()





        }.addOnFailureListener{err ->
            Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
        }
    }





}

