package com.jrdn.phonebookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.core.content.ContextCompat
import java.io.Serializable

class ViewActivity : AppCompatActivity() {
    private var selectedContactList : ArrayList<Contact> = ArrayList()
    private var selectedContact : Contact = Contact()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        val currentAccount = getAccessToCurrentUser()

        determineTheme(currentAccount)

        val viewListView = findViewById<ListView>(R.id.contactListViewView)
        val data = Sequel(this)
        var list = data.readContacts(currentAccount)
        list = list.sortedBy { it.firstName }
        val contactList = mutableListOf<String>()

        if (list.isNotEmpty()){
            for (contacts in list){
                contactList.add("${contacts.firstName} ${contacts.lastName}")
            }
        }
        else{
            contactList.add("Contact List is Empty")
        }

        val contactAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactList)

        viewListView.adapter = contactAdapter

        viewListView.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem : String = parent.getItemAtPosition(position) as String
            val breakSelectedItem = selectedItem.split(" ")
            val first = breakSelectedItem[0]
            val last = breakSelectedItem[1]

//            var contacts = data.readContacts()

            for (contact in list){
                if (first == contact.firstName && last == contact.lastName){
                    selectedContact = contact
                    selectedContactList.add(selectedContact)
                }
            }
            selectedActivity(R.layout.activity_selected_contact, currentAccount)
        }

        val add = findViewById<Button>(R.id.addViewBtn)
        add.setOnClickListener{
            addActivity(R.layout.activity_add, currentAccount)
        }

        val menu = findViewById<Button>(R.id.menuViewBtn)
        menu.setOnClickListener{
            mainActivity(R.layout.activity_main, currentAccount)
        }

        val preferences = findViewById<Button>(R.id.preferenceViewBtn)
        preferences.setOnClickListener{

            preferenceActivity(R.layout.activity_preference, currentAccount)
        }


    }

    private fun selectedActivity(view: Int, newAccount: NewAccount) {
        val intent = Intent(this, SelectedContact::class.java)

        intent.putExtra("selected",selectedContactList)

        intent.putExtra("currentAccount", newAccount as Serializable)

        startActivity(intent)
    }

    private fun addActivity(view: Int, newAccount: NewAccount) {
        val intent = Intent(this, AddActivity::class.java)

        intent.putExtra("currentAccount", newAccount as Serializable)

        startActivity(intent)
    }

    private fun mainActivity(view:Int, newAccount: NewAccount){
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra("currentAccount", newAccount as Serializable)

        startActivity(intent)
    }

    private fun preferenceActivity(view:Int, newAccount: NewAccount){
        val intent = Intent(this, PreferenceActivity::class.java)

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