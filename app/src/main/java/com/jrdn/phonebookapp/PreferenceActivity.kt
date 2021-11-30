package com.jrdn.phonebookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import java.io.Serializable

class PreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        var currentAccount = getAccessToCurrentUser()

        determineTheme(currentAccount)

        val data = Sequel(this)

        val switch = findViewById<Switch>(R.id.requiresPasswordPreferenceSwitch)
        switch.isChecked = currentAccount.requirePassword

        val colorful = findViewById<RadioButton>(R.id.colorfulPreferenceRadio)
        val dark = findViewById<RadioButton>(R.id.darkPreferenceRadio)
        val light = findViewById<RadioButton>(R.id.lightPreferenceMode)

        val (curAccount, theme) = determineRadioOnStartup(currentAccount, colorful, dark, light)
        currentAccount = curAccount
        theme.isChecked = true

        //determineTheme(colorful, dark, light, currentAccount)

        val executeChange = findViewById<Button>(R.id.submitPreferencesBtn)
        var insertValue : String
        executeChange.setOnClickListener{
            if (switch.isChecked != currentAccount.requirePassword){
                if (!switch.isChecked){
                    insertValue = "false"
                    currentAccount.requirePassword = false
                    data.changePasswordRequirement(insertValue, currentAccount)
                }
                else{
                    insertValue = "true"
                    currentAccount.requirePassword = true
                    data.changePasswordRequirement(insertValue, currentAccount)
                }
            }
            if (colorful.isChecked && currentAccount.colorMode != "colorful"){
                currentAccount.colorMode = "colorful"
                setTheme(R.style.Theme_Orange_Navy)
            }
            else if (dark.isChecked && currentAccount.colorMode != "dark"){
                currentAccount.colorMode = "dark"
                setTheme(R.style.Theme_Dark)
            }
            else if (light.isChecked && currentAccount.colorMode != "light"){
                currentAccount.colorMode = "light"
                setTheme(R.style.Theme_Light)
            }

            data.changeColorMode(currentAccount.colorMode, currentAccount)

            preferenceActivity(R.layout.activity_preference, currentAccount)
        }

        val contactList = findViewById<Button>(R.id.contactListPreferenceBtn)
        contactList.setOnClickListener{
            viewActivity(R.layout.activity_view, currentAccount)
        }
    }

    private fun viewActivity(view: Int, newAccount: NewAccount){
        val intent = Intent(this, ViewActivity::class.java)

        intent.putExtra("currentAccount", newAccount as Serializable)

        startActivity(intent)
    }

    private fun preferenceActivity(view: Int, newAccount: NewAccount){
        val intent = Intent(this, PreferenceActivity::class.java)

        intent.putExtra("currentAccount", newAccount as Serializable)

        startActivity(intent)
    }

    private fun getAccessToCurrentUser(): NewAccount {
        return intent.extras?.get("currentAccount") as NewAccount
    }

//    private fun determineTheme(dark : RadioButton, light : RadioButton, colorful : RadioButton, currentAccount : NewAccount) {
//        if (currentAccount.colorMode == "colorful"){
//            colorful.isChecked = true
//        }
//        else if (currentAccount.colorMode == "dark"){
//            dark.isChecked = true
//        }
//        else if (currentAccount.colorMode == "light"){
//            light.isChecked = true
//        }
//    }

    private  fun determineRadioOnStartup(currentAccount: NewAccount, colorful: RadioButton, dark: RadioButton, light: RadioButton) : Pair<NewAccount, RadioButton>{
        val theme :RadioButton
        if (currentAccount.colorMode == "colorful"){
            theme = colorful
        }
        else if (currentAccount.colorMode == "dark"){
            theme = dark
        }
        else{
            theme = light
        }

        return Pair(currentAccount, theme)
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