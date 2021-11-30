package com.jrdn.phonebookapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val menu = findViewById<Button>(R.id.menuHelpBtn)
        menu.setOnClickListener{
            mainActivity(R.layout.activity_main)
        }
    }

    private fun mainActivity(view: Int){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}