package uilayer

import backend.System
import library.*
import library.customenum.*
import database.*
import library.OutputHandler.colorCoatedMessage
import models.*

object UiService {
    fun signUp() {
        displaySelectUserMenu()
        val response: DbResponse =
            when (InputHandler.getIntWithMinusOne(1, 2)) {
                1 -> SignUpPage.displayPassengerSignUp()
                2 -> SignUpPage.displayDriverSignUp()
                else -> DbResponse.SignupFailed
            }
        if (response.getResponse() == 200) {
            displayResponse(response, TextColor.GREEN)
            if (InputHandler.getString("enter 0 to login or press any key to exit", includeNull = true) == "0") signIn()
        }
    }

    fun signIn() {
        var response: AuthenticationResponse
        val loggedUser: User
        while (true) {
            response = SignInPage.displaySignInPage()

            if (response is AuthenticationResponse.UserLoggedIn) {
                loggedUser = DbService.getLoggedUser(response)
                break
            } else {
                displayResponse(response, TextColor.YELLOW)
            }
        }
        System.application(loggedUser)
        colorCoatedMessage("successfully Logged out", TextColor.GREEN)
    }


    fun displayMainMenu() {
        colorCoatedMessage(
            """1 -> Sign_Up
            |2 -> Sign_In
            |3 -> Exit""".trimMargin("|"), TextColor.PEACH
        )
    }

    private fun displaySelectUserMenu() {
        colorCoatedMessage(
            """1 -> Passenger
            |2 -> Driver""".trimMargin("|"), TextColor.PEACH
        )
    }

    fun displayPassengerMainMenu() {
        colorCoatedMessage(
            """1 -> Book Ride
                |2 -> My Ride
                |3 -> View Profile
                |4 -> Logout
            """.trimMargin("|"), TextColor.PEACH
        )
    }

    fun displayDriverMainMenu() {
        colorCoatedMessage(
            """1 -> Check Available Ride
                |2 -> My Ride
                |3 -> View Profile
                |4 -> Logout
            """.trimMargin("|"), TextColor.PEACH
        )
    }

    fun displayRideRotes() {
        colorCoatedMessage(UtilContent.map, TextColor.MAGENTA)
    }

    fun displayWelcomeMessage() {
        colorCoatedMessage(UtilContent.welcomeMessage, TextColor.BLUE)
    }

    fun displayMyRide(id: Int, user: User) {
        when (user) {
            is Passenger -> {
                println(DbService.getMyRide(id, DbTables.passengers))
            }

            is Driver -> {println("under development")}
        }
    }

    fun displayResponse(ob: Any, textColor: TextColor) {
        when (ob) {
            is DbResponse -> {
                colorCoatedMessage("${ob.getResponse()} -> ${ob.getResponseMessage()}", textColor)
            }

            is AuthenticationResponse -> {
                colorCoatedMessage("${ob.getResponse()} -> ${ob.getResponseMessage()}", textColor)
            }
        }
    }

    fun displayAvailableRides(rides: List<Ride>): Int {
        var id = 1
        colorCoatedMessage(
            """
                +---------------------------------+
                |         Available Ride          |
                +---------------------------------+----------------------------------------------------------+
                | Id | Passenger Name             | Pickup Location      | Drop Location        | Price      |
                +----+----------------------------+----------------------+----------------------+------------+
            """.trimIndent(), TextColor.PEACH
        )
        rides.forEach {
            colorCoatedMessage(
                """
        | %-2s | %-26s | %-20s | %-20s | ${TextColor.GREEN}₹%-9.2f${TextColor.PEACH} |                                                   
        |____|____________________________|______________________|______________________|____________|
    """.trimIndent().format(id++, it.passenger?.username, it.pickup_location, it.drop_location, it.total_charge),
                TextColor.PEACH
            )
        }
        return id
    }
}