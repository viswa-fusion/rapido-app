package backend

import database.DbService
import library.*
import library.customenum.TextColor
import models.*
import modules.DriverAccess
import modules.PassengerAccess
import uilayer.*

object System {

    fun application(loggedUser: User) {
        val loggedUserid = DbService.getLoggedUserId(loggedUser)

        // printing of welcome message
        OutputHandler.colorCoatedMessage("welcome ${loggedUser.name} 🙂", TextColor.CYAN)

        when (loggedUser) {
            is Passenger -> {
                loggedUser.provideAccess(PassengerAccess())
                while (true) {
                    UiService.displayPassengerMainMenu()
                    when (InputHandler.getInt(1, 4)) {
                        1 -> {
                            val result: Boolean = DbService.isLoggedUserHavaAnotherRide(loggedUserid)
                            if (!result) {
                                val ride = BookingPage.gatherRideDataFromPassenger()
                                val dbResponse = loggedUser.bookRide(loggedUser, ride)
                                UiService.displayResponse(dbResponse, TextColor.GREEN)
                            } else OutputHandler.colorCoatedMessage(
                                "you have another ride currently. please check MyRide Menu",
                                TextColor.YELLOW
                            )
                        }

                        2 -> loggedUser.displayMyRide(loggedUserid, loggedUser)

                        3 -> println("feature under development")

                        4 -> if (InputHandler.getString(
                                "enter 0 to confirm logout or press any key to cancel",
                                includeNull = true
                            ) == "0"
                        ) break
                    }
                }
            }

            is Driver -> {
                loggedUser.provideAccess(DriverAccess())
                while (true) {
                    UiService.displayDriverMainMenu()
                    when (InputHandler.getInt(1, 4)) {
                        1 -> {
                            outer@ while (true) {
                                val driverCurrentLocation = RidePage.gatherLocation()
                                while (true) {
                                    val result = DbService.getNearByAvailableRide(driverCurrentLocation)
                                    val count = loggedUser.displayAvailableRides(result)
                                    val input = InputHandler.getInt(
                                        -1,
                                        count - 1,
                                        "enter id number to Accept Ride or( 0 to refresh) or (-1 to go back)"
                                    )
                                    var dbResponse: DbResponse
                                    when (input) {
                                        -1 -> break@outer
                                        in 1 until count -> dbResponse = DbService.acceptRide(result[input])
                                    }
                                }
                            }
                        }

                        2 -> println("feature under development")

                        3 -> println("feature under development")

                        4 -> if (InputHandler.getString(
                                "enter 0 to confirm logout or press any key to cancel",
                                includeNull = true
                            ) == "0"
                        ) break
                    }
                }
            }
        }
        OutputHandler.colorCoatedMessage("successfully Logged out", TextColor.GREEN)
    }
}

