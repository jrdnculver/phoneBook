package com.jrdn.phonebookapp

import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable
import java.lang.Exception

class Contact : AppCompatActivity(), Serializable {
    var firstName : String = ""
    var lastName : String = ""
    var phoneNumber : String = ""

    fun checkFirstName(first : String): String {
        if (first != "") {
            firstName = first.replaceFirstChar { first[0].uppercase() }
        }
        else{
            throw Exception("Fill in First Name")
        }
        return firstName
    }

    fun checkLastName(last : String): String {
        if (last != "") {
            lastName = last.replaceFirstChar { last[0].uppercase() }
        }
        else{
            throw Exception("Fill in Last Name")
        }
        return lastName
    }

    fun checkPhoneNumber(phone : String) : String{
        if (phone != ""){
            phoneNumber = phone
        }
        else{
            throw Exception("Fill in PhoneNumber")
        }
        return phoneNumber
    }
}

