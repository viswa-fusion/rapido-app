package uilayer

import database.DbService
import library.*
<<<<<<< Updated upstream
import library.customenum.TextColor
import modules.User
=======
import models.LoginDetails
>>>>>>> Stashed changes

internal object SignInPage {
    fun displaySignInPage(): LoginDetails {
        while (true) {
            val userName = InputHandler.getString(3, 12, "Enter username")
            val password = InputHandler.getString(8, 15, "Enter password")
            return LoginDetails(userName, password)
//            return Authenticator.authenticate(userName, password)
        }
    }
}