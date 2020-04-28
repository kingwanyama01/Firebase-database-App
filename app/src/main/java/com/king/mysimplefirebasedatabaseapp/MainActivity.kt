package com.king.mysimplefirebasedatabaseapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnSave.setOnClickListener {
            //Receive data from the user
            var name = mEdtName.text.toString()
            var email = mEdtEmail.text.toString()
            var age = mEdtAge.text.toString()
            var time = System.currentTimeMillis()

            var progress = ProgressDialog(this)
            progress.setTitle("Saving")
            progress.setMessage("Please wait...")

            if (name.isEmpty() or email.isEmpty() or age.isEmpty()){
                Toast.makeText(this,"Please fill all the inputs",Toast.LENGTH_LONG).show()
            }else{
                //Proceed to save data
                //Create a child in the database
                var my_child = FirebaseDatabase.getInstance().reference.child("Names/$time")
                var data = User(name,email,age)

                //To save data, simply set the data to my_child
                progress.show()
                my_child.setValue(data).addOnCompleteListener { task->
                    progress.dismiss()
                    if (task.isSuccessful){
                        mEdtName.setText(null)
                        mEdtEmail.setText(null)
                        mEdtAge.setText(null)
                        Toast.makeText(this,"Saving successful",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Saving failed",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        mBtnView.setOnClickListener {
            startActivity(Intent(this,UsersActivity::class.java))
        }
    }
}
