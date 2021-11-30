package com.jrdn.phonebookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.Serializable

class ModifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)

        var currentAccount = getAccessToCurrentUser()

        determineTheme(currentAccount)

        var oldContact = getModifiableContact()
        var data = Sequel(this)

        var first = findViewById<TextView>(R.id.firstNameModifyTxt)
        var last = findViewById<TextView>(R.id.lastNameModifyTxt)
        var phone = findViewById<TextView>(R.id.phoneModifyTxt)

        first.text = oldContact.firstName
        last.text = oldContact.lastName
        phone.text = oldContact.phoneNumber

        val modify = findViewById<Button>(R.id.modifyModifyBtn)
        modify.setOnClickListener{
            var newContact = Contact()

            first.text = first.text.toString()
            last.text = last.text.toString()
            phone.text = phone.text.toString()

            newContact.firstName = first.text.toString()
            newContact.lastName = last.text.toString()
            newContact.phoneNumber = phone.text.toString()

            data.deleteRow(oldContact)
            data.insertNewContact(newContact, currentAccount)

            contactListActivity(R.layout.activity_view, currentAccount)
        }

        val contactList = findViewById<Button>(R.id.contactListModifyBtn)
        contactList.setOnClickListener{
            contactListActivity(R.layout.activity_view, currentAccount)
        }
    }

    private fun contactListActivity(view: Int, newAccount : NewAccount){
        val intent = Intent(this, ViewActivity::class.java)

        intent.putExtra("currentAccount", newAccount as Serializable)

        startActivity(intent)
    }

    private fun getModifiableContact() : Contact{
        return intent.getSerializableExtra("selected") as Contact
    }

    fun getAccessToCurrentUser(): NewAccount {
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