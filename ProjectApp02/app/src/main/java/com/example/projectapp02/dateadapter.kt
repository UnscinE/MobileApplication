package com.example.projectapp02

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class dateadapter(private val datelist:ArrayList<DataDate>)
    :RecyclerView.Adapter<dateadapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dateadapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.date_listitem,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: dateadapter.ViewHolder, position: Int) {
        val currentdate = datelist[position]
        holder.tyDatedata_Title.text = currentdate.title
        holder.tyDatedata_Detail.text = currentdate.detail

    }

    override fun getItemCount(): Int {
        return datelist.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tyDatedata_Title : TextView = itemView.findViewById(R.id.tvDatedata_Title)
        val tyDatedata_Detail : TextView = itemView.findViewById(R.id.tvDatedata_Detail)

    }
}