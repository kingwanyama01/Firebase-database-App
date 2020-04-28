package com.king.mysimplefirebasedatabaseapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        var users:ArrayList<User> = ArrayList()
        var myAdapter = CustomAdapter(applicationContext,users)
        var progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")

        //Access the table in the database

        var my_db = FirebaseDatabase.getInstance().reference.child("Names")
        //Start retrieving data
        progress.show()
        my_db.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                //Get the data and put it on the arraylist users
                users.clear()
                for (snap in p0.children){
                    var person = snap.getValue(User::class.java)
                    users.add(person!!)
                }
                //Notify the adapter that data has changed
                myAdapter.notifyDataSetChanged()
                progress.dismiss()
            }

            override fun onCancelled(p0: DatabaseError) {
                progress.dismiss()
                Toast.makeText(applicationContext,"DB Locked",Toast.LENGTH_LONG).show()
            }
        })

        mListPeople.adapter = myAdapter
    }
}
