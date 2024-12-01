package com.example.projectapp02.ui.Note

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectapp02.AddnoteActivity
import com.example.projectapp02.DataDate
import com.example.projectapp02.LoginActivity
import com.example.projectapp02.R
import com.example.projectapp02.databinding.FragmentNoteBinding
import com.example.projectapp02.dateadapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(NoteViewModel::class.java)

        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    private lateinit var dateRecyclerView: RecyclerView
    private lateinit var tyloadingData:TextView
    private lateinit var datelist:ArrayList<DataDate>
    private lateinit var dbref: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dateRecyclerView = view.findViewById(R.id.rvDate)
        dateRecyclerView.layoutManager = LinearLayoutManager(activity)
        dateRecyclerView.setHasFixedSize(true)
        tyloadingData = view.findViewById(R.id.tyloadingData)

        datelist = arrayListOf<DataDate>()

        getDateData()

        val btnaddnote: FloatingActionButton = view.findViewById(R.id.addnotebutton)

        btnaddnote.setOnClickListener{
            val intent = Intent(activity, AddnoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getDateData(){
        dateRecyclerView.visibility = View.GONE
        tyloadingData.visibility = View.VISIBLE

        dbref = FirebaseDatabase.getInstance().getReference("Calendar")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                datelist.clear()
                if (snapshot.exists()){
                    for (dateSnap in snapshot.children){
                        val dateData = dateSnap.getValue(DataDate::class.java)
                        datelist.add(dateData!!)
                    }
                    val mAdapter = dateadapter(datelist)
                    dateRecyclerView.adapter = mAdapter

                    dateRecyclerView.visibility = View.VISIBLE
                    tyloadingData.visibility = View.GONE
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