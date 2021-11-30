package com.jrdn.phonebookapp

import androidx.appcompat.app.AppCompatActivity
import java.io.Serializable

class NewAccount : AppCompatActivity(), Serializable {
    var firstName: String = ""
    var lastName: String = ""
    var street: String = ""
    var city: String = ""
    var state: String = ""
    var zip: Number = 0
    var email: String = ""
    var phoneNumber: String = ""
    var userName: String = ""
    var password: String = ""
    var requirePassword = true
    var colorMode = ""

    fun checkFirstName(first: String): Pair<String, Boolean> {
        var error = false
        if (first != "") {
            firstName = first.replaceFirstChar { first[0].uppercase() }
        } else {
            error = true

        }
        return Pair(firstName, error)
    }

    fun checkLastName(last: String): Pair<String, Boolean> {
        var error = false
        if (last != "") {
            lastName = last.replaceFirstChar { last[0].uppercase() }
        } else {
            error = true
        }
        return Pair(lastName, error)
    }

    fun checkStreet(str : String): Pair<String, Boolean> {
        var error = false
        if (str != "") {
            street = str
        } else {
            error = true
        }
        return Pair(street, error)
    }

    fun checkCity(cit: String): Pair<String, Boolean> {
        var error = false
        if (cit != "") {
            city = cit.replaceFirstChar { cit[0].uppercase() }
        } else {
            error = true
        }
        return Pair(city, error)
    }

    fun checkState(sta: String): Triple<String, Int, Boolean> {
        var error = false
        var errorType = 0
        val twoLetters = Regex("[A-Z]{2}\$")
        if (sta != "") {
            if (twoLetters.containsMatchIn(sta)) {
                state = sta
            } else {
                error = true
                errorType = 2
            }
        } else {
            error = true
            errorType = 1
        }
        return Triple(state, errorType, error)
    }

    fun checkZip(zi: String): Triple<Number, Int, Boolean> {
        var error = false
        var errorType = 0
        val fiveDigits = Regex("\\b[1-9]{5}\\b")
        if (zi != "") {
            if (fiveDigits.containsMatchIn((zi))) {
                zip = zi.toInt()
            } else {
                error = true
                errorType = 2
            }
        } else {
            error = true
            errorType = 1
        }
        return Triple(zip, errorType, error)
    }

    fun checkEmail(mail: String): Pair<String, Boolean> {
        var error = false
        val emailPattern =
            Regex("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
        if (mail != "") {
            if (emailPattern.containsMatchIn(mail)) {
                email = mail
            } else {
                error = true
            }
        } else {
            error = true
        }
        return Pair(email, error)
    }

    fun checkPhone(phone: String): Pair<String, Boolean> {
        var error = false
        val tenDigits = Regex("\\b[1-9]{10}\\b")
        if (tenDigits.containsMatchIn(phone)) {
            phoneNumber = phone
        } else {
            error = true
        }
        return Pair(phoneNumber, error)
    }

    fun checkUserName(user: String, list: List<NewAccount>) : Pair<String,Boolean>{

        var error = false

        if (user == ""){
            error = true
            }

        list.forEach { account ->
            if (account.userName == user){
                throw Exception("This UserName already Exist")
            }
        }
        userName = user

        return Pair(userName,error)
    }


    fun checkPassword(pass: String) : Pair<String, Boolean>{
        var error = false
        if (pass == "") {
            error = true
        }
        val pattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$")
        val passing = pattern.containsMatchIn(pass)
        if (passing) {
            password = pass
        } else {
            error = true
        }
        return Pair(password, error)
    }
}