package com.example.recyclerview
import com.google.firebase.database.*
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Fetchdata : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var itemList: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fun onDataChange(dataSnapshot: DataSnapshot) {
            val updatedItemList = mutableListOf<Item>()

            for (snapshot in dataSnapshot.children) {
                val item = snapshot.getValue(Item::class.java)
                item?.let {
                    updatedItemList.add(item)
                }
            }


            itemAdapter.notifyDataSetChanged()
        }

        recyclerView = findViewById(R.id.recyclerView)
        itemAdapter = ItemAdapter(itemList)
        recyclerView.adapter = itemAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val database = FirebaseDatabase.getInstance()
        val itemsRef = database.getReference("items")

        itemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val updatedItemList = mutableListOf<Item>()

                for (snapshot in dataSnapshot.children) {
                    val item = snapshot.getValue(Item::class.java)
                    item?.let {
                        updatedItemList.add(item)
                    }
                }


                itemAdapter = ItemAdapter(updatedItemList)
                recyclerView.adapter = itemAdapter
            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(this@Fetchdata, "Data retrieval cancelled", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

