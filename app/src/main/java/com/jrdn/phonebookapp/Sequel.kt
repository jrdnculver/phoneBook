package com.jrdn.phonebookapp

import android.content.Context
import  android.database.sqlite.SQLiteDatabase

class Sequel(context: Context){

    private val db : SQLiteDatabase = context.openOrCreateDatabase("PhoneContacts", Context.MODE_PRIVATE, null)
    private val retrievedAccounts = mutableListOf<NewAccount>()
    private val retrievedContacts = mutableListOf<Contact>()

    fun saveNewAccountTable(){
        val query = ("CREATE TABLE IF NOT EXISTS" + " myAccounts" + "("
                + "accountID" + " INTEGER, " +
                "firstName" + " TEXT, " +
                "lastName" + " TEXT, " +
                "street" + " TEXT, " +
                "city" + " TEXT, " +
                "state" + " TEXT, " +
                "zip" + " INTEGER, " +
                "email" + " TEXT, " +
                "phone" + " TEXT, " +
                "userName" + " TEXT, " +
                "password" + " TEXT," +
                "requiresPassword" + " TEXT," +
                "colorMode" + " TEXT" +
                ")")
        db.execSQL(query)
    }

    fun saveNewContactTable(){
        val query = ("CREATE TABLE IF NOT EXISTS" + " myContacts" + "("
                + "contactID" + " INTEGER, " +
                "userName" + " TEXT, " +
                "firstName" + " TEXT, " +
                "lastName" + " TEXT, " +
                "phone" + " TEXT)")

        db.execSQL(query)
    }

    fun insertNewAccountValue(newAccount: NewAccount){
       val list = readAccounts()
        val contactID : Int = list.size
        val query = ("INSERT INTO myAccounts (accountID, firstName, lastName, street, city, " +
                "state, zip, email, phone, userName, password, requiresPassword, colorMode) Values(${contactID},'${newAccount.firstName}','${newAccount.lastName}'," +
                "'${newAccount.street}','${newAccount.city}','${newAccount.state}',${newAccount.zip},'${newAccount.email}'," +
                "${newAccount.phoneNumber},'${newAccount.userName}','${newAccount.password}','${newAccount.requirePassword}'," +
                "'${newAccount.colorMode}')")
        db.execSQL(query)
    }

    fun insertNewContact(contact: Contact, newAccount: NewAccount){
        val list = readContacts(newAccount)
        val contactID : Int = list.size
        val query = ("INSERT INTO myContacts (contactID, userName, firstName, lastName, phone) Values(${contactID},'${newAccount.userName}','${contact.firstName}','${contact.lastName}'," +
                "'${contact.phoneNumber}')")
        db.execSQL(query)
    }

    fun readAccounts() : List<NewAccount>{
        val myAccounts = db.rawQuery("Select * FROM myAccounts", null)
        if (myAccounts.moveToFirst()) {
            do {
                val contact = NewAccount()
                contact.firstName = myAccounts.getString(myAccounts.getColumnIndex("firstName"))
                contact.lastName = myAccounts.getString(myAccounts.getColumnIndex("lastName"))
                contact.street = myAccounts.getString(myAccounts.getColumnIndex("street"))
                contact.city = myAccounts.getString(myAccounts.getColumnIndex("city"))
                contact.state = myAccounts.getString(myAccounts.getColumnIndex("state"))
                val zip = myAccounts.getString(myAccounts.getColumnIndex("zip"))
                contact.zip = zip.toInt()
                contact.email = myAccounts.getString(myAccounts.getColumnIndex("email"))
                contact.phoneNumber = myAccounts.getString(myAccounts.getColumnIndex("phone"))
                contact.userName = myAccounts.getString(myAccounts.getColumnIndex("userName"))
                contact.password = myAccounts.getString(myAccounts.getColumnIndex("password"))
                val require = myAccounts.getString(myAccounts.getColumnIndex("requiresPassword"))
                contact.requirePassword = require.toBoolean()
                contact.colorMode = myAccounts.getString(myAccounts.getColumnIndex("colorMode"))
                retrievedAccounts.add(contact)
            } while (myAccounts.moveToNext())
        }
        myAccounts.close()
        return retrievedAccounts
    }

    fun readContacts(newAccount: NewAccount) : List<Contact>{

        val myContacts = db.rawQuery("Select * FROM myContacts WHERE userName = '${newAccount.userName}'", null)
        if(myContacts.moveToFirst()) {
            do {
                val contact = Contact()
                newAccount.userName = myContacts.getString((myContacts.getColumnIndex("userName")))
                contact.firstName = myContacts.getString((myContacts.getColumnIndex("firstName")))
                contact.lastName = myContacts.getString((myContacts.getColumnIndex("lastName")))
                contact.phoneNumber = myContacts.getString((myContacts.getColumnIndex("phone")))


                retrievedContacts.add(contact)
            } while (myContacts.moveToNext())
        }
        myContacts.close()
        return retrievedContacts
    }

    fun deleteRow(contact: Contact){
        val deleteContactRow = ("DELETE FROM myContacts WHERE phone = '${contact.phoneNumber}'")

        db.execSQL(deleteContactRow)
    }

    fun changePasswordRequirement(insertRequirement : String, newAccount: NewAccount){

        val query = ("UPDATE myAccounts SET requiresPassword = '${insertRequirement}' WHERE userName = '${newAccount.userName}'")
        db.execSQL(query)
    }

    fun changeColorMode(insertColorMode : String, newAccount: NewAccount){
        val query = ("UPDATE myAccounts SET colorMode = '${insertColorMode}' WHERE userName = '${newAccount.userName}'")
        db.execSQL(query)
    }

    fun deleteAccountTable(){
        val query = ("Drop TABLE myAccounts")
        db.execSQL(query)
    }

    fun deleteContactTable(){
        val query = ("Drop TABLE myContacts")
        db.execSQL(query)
    }
}