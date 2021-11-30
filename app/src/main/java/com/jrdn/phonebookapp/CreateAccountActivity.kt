package com.jrdn.phonebookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.Serializable
import java.lang.Exception

class CreateAccountActivity : AppCompatActivity() {
    private var newAcc : NewAccount = NewAccount()

    private var created = false


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_account)

        val create = findViewById<Button>(R.id.createAccountBtn)

        create.setOnClickListener{
            val first = findViewById<TextView>(R.id.firstNameSelectedTxt).text.toString()
            val last = findViewById<TextView>(R.id.lastNameCreateTxt).text.toString()
            val str = findViewById<TextView>(R.id.streetCreateTxt).text.toString()
            val cit = findViewById<TextView>(R.id.cityCreateTxt).text.toString()
            val sta = findViewById<TextView>(R.id.stateCreateTxt).text.toString()
            val zi = findViewById<TextView>(R.id.zipCreateTxt).text.toString()
            val mail = findViewById<TextView>(R.id.emailCreateTxt).text.toString()
            val phone = findViewById<TextView>(R.id.phoneNumberCreateTxt).text.toString()
            val user = findViewById<TextView>(R.id.usernameCreateTxt).text.toString()
            val pass = findViewById<TextView>(R.id.passwordCreateTxt).text.toString()

            createNewAccount(first, last, str, cit, sta, zi, mail, phone, user, pass)
        }

        val menu = findViewById<Button>(R.id.menuCreateBtn)

        menu.setOnClickListener{

            firstActivity(R.layout.activity_main)

        }


    }

    private fun firstActivity(view: Int) {
        val intent = Intent(this, MainActivity::class.java)

        intent.putExtra("created", created as Serializable)

        intent.putExtra("newAccount", newAcc)

        startActivity(intent)
    }

    private fun createNewAccount(first: String, last: String, str: String, cit: String, sta: String, zi: String, mail: String, phone: String, user: String, pass: String){

        val error = findViewById<TextView>(R.id.errorMessage)

        val data = Sequel(this)
        val accounts = data.readAccounts()

        try{

            var(firstName, boolFirst) = newAcc.checkFirstName(first)

            if (boolFirst) {throw Exception("Fill in First Name")}

            var(lastName, boolLast) = newAcc.checkLastName(last)

            if (boolLast) {throw Exception("Fill in Last Name")}

            var(street, boolStreet) = newAcc.checkStreet(str)

            if (boolStreet) {throw Exception("Fill in Street Name")}

            var(city, boolCity) = newAcc.checkCity(cit)

            if (boolCity) {throw Exception("Fill in City Name")}

            var(state, errorTypeState, boolState) = newAcc.checkState(sta)

            if (boolState) { if(errorTypeState == 1) throw Exception("Fill in State Name") else if (errorTypeState == 2){throw Exception("Enter as Abbreviation Ex 'IN'")}}

            var(zip, errorTypeZip, boolZip) = newAcc.checkZip(zi)

            if (boolZip) {if(errorTypeZip == 1) throw Exception("Fill in Zip Code") else if(errorTypeZip == 2) {throw Exception("Zip must be five digits")}}

            var(email, boolEmail) = newAcc.checkEmail(mail)

            if (boolEmail) {throw Exception("Fill in Email")}

            var(phoneNumber, boolPhone) = newAcc.checkPhone(phone)

            if (boolPhone) {throw Exception("Fill in Phone Number")}

            var (username, boolUser) = newAcc.checkUserName(user, accounts)

            if (boolUser) {throw Exception("Fill in UserName")}

            var(password, boolPassword) = newAcc.checkPassword(pass)

            if (boolPassword) {throw Exception("Fill in Password")}

            newAcc.firstName = firstName
            newAcc.lastName = lastName
            newAcc.street = street
            newAcc.city = city
            newAcc.state = state
            newAcc.zip = zip
            newAcc.email = email
            newAcc.phoneNumber = phoneNumber
            newAcc.userName = username
            newAcc.password = password

            data.insertNewAccountValue(newAcc)

            created = true
            throw Exception("Account created Successfully")
        }
        catch(e: Exception){
            error.text = e.message.toString()
        }
        if (created){
               firstActivity(R.layout.activity_main)
           }

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