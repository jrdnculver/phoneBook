package com.jrdn.phonebookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.Serializable
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var currentAccount = NewAccount()

        val data = Sequel(this)
        val deleteThis = false
        if (deleteThis){
            data.deleteAccountTable()
            data.deleteContactTable()
        }

        data.saveNewContactTable()
        data.saveNewAccountTable()

        val signup = findViewById<Button>(R.id.signUpBtn)
        signup.setOnClickListener{
            secondActivity(R.layout.activity_create_account)
        }

        val login = findViewById<Button>(R.id.loginBtn)
        login.setOnClickListener{
            try{
                val username = findViewById<TextView>(R.id.usernameLoginTxt).text.toString()
                val password = findViewById<TextView>(R.id.passwordLoginTxt).text.toString()

                val access = successfulLogin(data, username, password)
                currentAccount = linkLoginToAccount(username, password, currentAccount)
                println(currentAccount)
                if (access){
                    currentAccountAccess(R.layout.activity_view, currentAccount)
                }
                else if (!currentAccount.requirePassword){
                    currentAccountAccess(R.layout.activity_view, currentAccount)
                }
                else{
                    throw Exception("Incorrect Login Credentials")
                }
            }
            catch(e: Exception){
                val errors = findViewById<TextView>(R.id.errorLoginTxt)
                errors.text = e.message.toString()
            }
        }

        val help = findViewById<Button>(R.id.helpBtn)
        help.setOnClickListener{
            helpActivity(R.layout.activity_help)
        }

        fillPrimaryText()
        
    }

    private fun secondActivity(view: Int){
        val intent = Intent(this, CreateAccountActivity::class.java)
        startActivity(intent)
    }

    private fun helpActivity(view: Int){
        val intent = Intent(this, HelpActivity::class.java)
        startActivity(intent)
    }

    private fun fillPrimaryText(){
        val created = intent.getBooleanExtra("created", false)
        if (created){
            val createdAccount = intent.extras?.get("newAccount") as NewAccount

            val userLogin = findViewById<TextView>(R.id.usernameLoginTxt)
            val passwordLogin = findViewById<TextView>(R.id.passwordLoginTxt)

            userLogin.text = createdAccount.userName
            passwordLogin.text = createdAccount.password
        }

    }

    private fun linkLoginToAccount(username: String, password: String, newAccount: NewAccount) : NewAccount{
        val data = Sequel(this)
        val list = data.readAccounts()
        var matchFound : NewAccount

        for (li in list) if (username == li.userName && password == li.password) {
            matchFound = li
            return matchFound

        } else if (username == li.userName && !li.requirePassword) {
            matchFound = li
            return matchFound
        }

        return newAccount
    }
    
    private fun successfulLogin(sequel: Sequel, username : String, password : String) : Boolean{
        val accounts = sequel.readAccounts()
        var access = false

        for (account in accounts) {
            if(account.userName == username && account.password == password){
                access = true
            }
        }
        return access
    }

    private fun currentAccountAccess(view: Int, currentAccount : NewAccount){
        val intent = Intent(this, ViewActivity::class.java)

        intent.putExtra("currentAccount", currentAccount as Serializable)

        startActivity(intent)
    }

    fun determineTheme(newAccount: NewAccount){
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