package com.jrdn.phonebookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.Serializable

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val currentAccount = getAccessToCurrentUser()

        determineTheme(currentAccount)

        val contact = Contact()
        val data = Sequel(this)
        var isCreated : Boolean
        isCreated = false

        val create = findViewById<Button>(R.id.createAddBtn)
        create.setOnClickListener{
            val first = findViewById<TextView>(R.id.firstNameAddTxt).text.toString()
            val last = findViewById<TextView>(R.id.lastNameAddTxt).text.toString()
            val phone = findViewById<TextView>(R.id.phoneNumberAddTxt).text.toString()

            contact.firstName = contact.checkFirstName(first)
            contact.lastName = contact.checkLastName(last)
            contact.phoneNumber = contact.checkPhoneNumber(phone)
            data.insertNewContact(contact, currentAccount)

            isCreated = true

            if (isCreated){

                viewActivity(R.layout.activity_view, currentAccount)

            }
        }


        val contactList = findViewById<Button>(R.id.homeAddBtn)
        contactList.setOnClickListener{

            viewActivity(R.layout.activity_view, currentAccount)

        }
    }

    private fun viewActivity(view: Int, newAccount : NewAccount){
        val intent = Intent(this, ViewActivity::class.java)

        intent.putExtra("currentAccount", newAccount as Serializable)

        startActivity(intent)
    }

    private fun getAccessToCurrentUser(): NewAccount {

        return intent.extras?.get("currentAccount") as NewAccount
    }

    private fun determineTheme(newAccount: NewAccount){
        if (newAccount.colorMode == "colorful" ){
            setTheme(R.style.Theme_Orange_Navy)
        }
        else if (newAccount.colorMode == "dark"){
            setTheme(R.style.Theme_Dark)
        }
        else{
            setTheme(R.style.Theme_Light)
        }
    }
}