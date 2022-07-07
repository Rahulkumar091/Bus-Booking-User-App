package com.example.android.busadminapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.activity.BusSeats
import com.example.android.app.R
import com.example.android.app.activity.Routes
//import com.example.android.app.activity.Buses
import com.example.android.busadminapp.model.Bus

class BusAdapter(private val context:Context, private var busList:ArrayList<Bus>):RecyclerView.Adapter<BusAdapter.BusAdapterViewHolder>(){

    /*var countryFilterList : ArrayList<Bus>

    init {
        countryFilterList = ArrayList()
        countryFilterList.addAll(busList)
    }*/

    fun filtering(newFilteredList: ArrayList<Bus>) {
        busList = newFilteredList
        notifyDataSetChanged()
    }


    inner class BusAdapterViewHolder(view: View):RecyclerView.ViewHolder(view){
        val from :TextView = view.findViewById(R.id.from_list_item)
        val to:TextView  = view.findViewById(R.id.to_list_item)
        val busService:TextView  = view.findViewById(R.id.busService_list_item)
        val busNo:TextView  = view.findViewById(R.id.busNumber_list_item)
        val date:TextView  = view.findViewById(R.id.date_list_item)
        val startTime:TextView  = view.findViewById(R.id.start_time_list_item)
        val arrivalTime:TextView  = view.findViewById(R.id.arrival_time_list_item)
        val rate:TextView  = view.findViewById(R.id.rate_list_item)
        val viewRoutes:TextView  = view.findViewById(R.id.viewRoutes)
        val listContent : RelativeLayout = view.findViewById(R.id.list_Content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.bus_single_item_view,parent,false)
        return BusAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusAdapterViewHolder, position: Int) {
        val currentBus = busList[position]
        //val currentBus = countryFilterList[position]
        holder.from.text = currentBus.from
        holder.to.text = currentBus.to
        holder.busService.text  = currentBus.service
        holder.busNo.text = currentBus.busNo
        holder.date.text = currentBus.date
        holder.startTime.text = currentBus.startTime
        holder.arrivalTime.text = currentBus.arrivalTime
        holder.rate.text = currentBus.price
        holder.viewRoutes.setOnClickListener {
            val intent = Intent(context, Routes::class.java)
                .putExtra("id",currentBus.id)
                .putExtra("from",holder.from.text)
                .putExtra("to",holder.to.text)
                .putExtra("bus service",holder.busService.text)
                .putExtra("bus no.",holder.busNo.text)
                .putExtra("date",holder.date.text)
                .putExtra("start time",holder.startTime.text)
                .putExtra("arrival time",holder.arrivalTime.text)
            context.startActivity(intent)
        }

        holder.listContent.setOnClickListener {
            val intent = Intent(context, BusSeats::class.java)
                .putExtra("from",holder.from.text)
                .putExtra("to",holder.to.text)
                .putExtra("bus service",holder.busService.text)
                .putExtra("bus no.",holder.busNo.text)
                .putExtra("date",holder.date.text)
                .putExtra("start time",holder.startTime.text)
                .putExtra("arrival time",holder.arrivalTime.text)
                .putExtra("price",holder.rate.text)
                .putExtra("id",currentBus.id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return busList.size
        //return  countryFilterList.size
    }

}