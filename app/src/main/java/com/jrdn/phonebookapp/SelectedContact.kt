package com.jrdn.phonebookapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.io.Serializable

class SelectedContact : AppCompatActivity() {
    private var firstObj = Contact()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_contact)

        val currentAccount = getAccessToCurrentUser()

        determineTheme(currentAccount)

        val selectedItem = getSelectedListViewItem()
        val first = findViewById<TextView>(R.id.firstNameSelectedTxt)
        val last = findViewById<TextView>(R.id.lastNameSelectedTxt)
        val phone = findViewById<TextView>(R.id.phoneSelectedTxt)

        first.inputType = InputType.TYPE_NULL
        last.inputType = InputType.TYPE_NULL
        phone.inputType = InputType.TYPE_NULL

        val i = 0

        firstObj = selectedItem[i]

        first.text = firstObj.firstName
        last.text = firstObj.lastName
        phone.text = firstObj.phoneNumber

        val back = findViewById<Button>(R.id.backSelectBtn)
        val forward = findViewById<Button>(R.id.forwardSelectBtn)

        back.isEnabled = false
        forward.isEnabled = false

        if (selectedItem.size > 1){
            forward.isEnabled = true
            back.isEnabled = true
        }

        back.setOnClickListener{

        }

        forward.setOnClickListener{

        }

        val call = findViewById<Button>(R.id.callSelectBtn)
        call.setOnClickListener{
            call(R.layout.activity_making_call, firstObj.phoneNumber)
        }


        val view = findViewById<Button>(R.id.contactListSelectBtn)
        view.setOnClickListener{
            contactListActivity(R.layout.activity_view, currentAccount)
        }

        val modify = findViewById<Button>(R.id.modifySelectBtn)
        modify.setOnClickListener{
            modifyActivity(R.layout.activity_modify, currentAccount)
        }

        val delete = findViewById<Button>(R.id.deleteSelectBtn)
        delete.setOnClickListener{
            val data = Sequel(this)
            data.deleteRow(firstObj)
            contactListActivity(R.layout.activity_view, currentAccount)
        }

        val home = findViewById<Button>(R.id.homeSelectBtn)
        home.setOnClickListener{
            homeActivity(R.layout.activity_main, currentAccount)
        }
    }

    private fun getSelectedListViewItem() : ArrayList<Contact> {

        return intent.getSerializableExtra("selected") as ArrayList<Contact>
    }

    private fun homeActivity(view: Int, newAccount : NewAccount){
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra("currentAccount", newAccount as Serializable)

        startActivity(intent)
    }

    private fun contactListActivity(view: Int, newAccount: NewAccount){
        val intent = Intent(this, ViewActivity::class.java)

        intent.putExtra("currentAccount", newAccount as Serializable)

        startActivity(intent)
    }

    private fun modifyActivity(view: Int, newAccount: NewAccount){
        val intent = Intent(this, ModifyActivity::class.java)

        intent.putExtra("selected",firstObj)

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

    private fun call(view: Int, number:String) {
        val dialIntent = Intent(Intent.ACTION_DIAL)
        dialIntent.data = Uri.parse("tel:$number")
        startActivity(dialIntent)
    }
}