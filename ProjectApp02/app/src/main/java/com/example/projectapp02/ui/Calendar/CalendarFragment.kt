package com.example.projectapp02.ui.Calendar

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapp02.DataDate
import com.example.projectapp02.R
import com.example.projectapp02.databinding.FragmentCalendarBinding
import com.example.projectapp02.dateadapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private lateinit var dbref: DatabaseReference
    private lateinit var datelist:ArrayList<DataDate>
    private lateinit var dateRecyclerView: RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(CalendarViewModel::class.java)

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateRecyclerView = view.findViewById(R.id.rvDate)
        dateRecyclerView.layoutManager = LinearLayoutManager(activity)
        dateRecyclerView.setHasFixedSize(true)

        datelist = arrayListOf<DataDate>()

        var calen : CalendarView = view.findViewById(R.id.calendarView)
        var edtEvent : EditText = view.findViewById(R.id.edtEvent)
        val btnsave : Button = view.findViewById(R.id.Calendar_btnsave)


        calen.setOnDateChangeListener{_, year, month, dayOfMonth ->

            // Store the value of date with
            // format in String type Variable
            // Add 1 in month because month
            // index is start with 0
            val date = "$dayOfMonth/${month + 1}/$year"

            // set this date in TextView for Display
            edtEvent.text = Editable.Factory.getInstance().newEditable(date)

            getDateData(date)




        }





    }
    private fun getDateData(date : String) {
        dateRecyclerView.visibility = View.GONE
        //tyloadingData.visibility = View.VISIBLE

        dbref = FirebaseDatabase.getInstance().getReference("Calendar")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                datelist.clear()
                if (snapshot.exists()){
                    for (dateSnap in snapshot.children){
                        if ()
                        val dateData = dateSnap.getValue(DataDate::class.java)
                        datelist.add(dateData!!)
                    }
                    val mAdapter = dateadapter(datelist)
                    dateRecyclerView.adapter = mAdapter

                    dateRecyclerView.visibility = View.VISIBLE
                    //tyloadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}