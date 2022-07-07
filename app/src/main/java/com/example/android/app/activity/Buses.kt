package com.example.android.app.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.app.R
import com.example.android.busadminapp.adapter.BusAdapter
import com.example.android.busadminapp.model.Bus
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Buses : AppCompatActivity() {

    //private lateinit var addBus : FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    private lateinit var busList : ArrayList<Bus>

    private lateinit var busAdapter: BusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buses)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Buses List"

        busList = ArrayList()

        //addBus = findViewById(R.id.addBus)
        recyclerView = findViewById(R.id.recyclerView)

        busAdapter = BusAdapter(this,busList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = busAdapter

        /*addBus.setOnClickListener {
            val intent = Intent(this, AddBus::class.java)
            startActivity(intent)
            finish()
        }*/

        getData()

    }

    //Searching

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_buses,menu)
        var searchItem=menu?.findItem(R.id.searchBtn)
        var searchView=searchItem?.actionView as SearchView;
        //searchView.setImeOptions(EditorInfo.IME_ACTION_DONE)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                busAdapter.getFilter().filter(newText)
                return false
            }
        })
        return true
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_buses,menu)

        val item = menu?.findItem(R.id.searchBtn)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Search here..."
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                Filtering(p0)
                return true
            }

        })


        return true
    }

    private fun Filtering(p0: String?) {
        val filteredList = ArrayList<Bus>()
        for(i in busList){
            if (i.from.toLowerCase().contains(p0.toString().toLowerCase()) || i.to.toLowerCase().contains(p0.toString().toLowerCase())){
                filteredList.add(i)
            }
        }
        busAdapter.filtering(filteredList)
    }

    fun getData(){
        val db =  Firebase.firestore

        db.collection("Buses").orderBy("From", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val result = it.documents

                for ( values in result ){
                    val id = values.id
                    val from = values["From"].toString()
                    val to = values["To"].toString()
                    val busService = values["Bus Service"].toString()
                    val busNo = values["Bus Number"].toString()
                    val date = values["Date"].toString()
                    val startingTime = values["Starting Time"].toString()
                    val arrivalTime = values["Arrival Time"].toString()
                    val price = values["Price"].toString()

                    busList.add(
                        Bus(id,from,to,busService,busNo,date,startingTime,arrivalTime,price)
                    )
                }

                busAdapter.notifyDataSetChanged()

            }.addOnFailureListener {
                Toast.makeText(this,"$it", Toast.LENGTH_SHORT).show()
            }
    }

}