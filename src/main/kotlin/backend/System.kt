package backend

import datalayer.DataBase
import library.*
import library.customenum.Location
import models.*
import uilayer.*

class System(override val userInterface: UserInterface, override val database: DataBase) : Backend{

//    private val accessDependencyFactory = AccessDependencyFactory()
    override fun bookRide(loggedUser: User, ride: Ride) : Boolean {
    return when(val response = database.addNewRide(loggedUser, ride)){
            is DbResponse.SuccessfullyCreated -> true
            else -> {
                println("${response.getResponse()} -> ${response.getResponseMessage()}")
                false
            }
        }
    }

    override fun addNewUser(user: User): DbResponse {
        return when(user){
            is Passenger -> database.addNewPassenger(user)
            is Driver -> database.addNewDriver(user)
            else -> DbResponse.SignupFailed
        }
    }

    override fun getLoggedUser(loginDetails: LoginDetails): User? {
        return when (val response = authenticate(loginDetails)){
            is AuthenticationResponse.UserLoggedIn ->  database.getLoggedUser(response)
            else -> {
                println("${response.getResponse()} -> ${response.getResponseMessage()}")
                null
            }
        }
    }
    private fun authenticate(loginDetails: LoginDetails) : AuthenticationResponse{
        return when(val response = database.isValidCredential(loginDetails)){
            is AuthenticationResponse.UserFound -> database.getUser(loginDetails.userName)
            else -> response
        }
    }
    override fun isLoggedUserHavaRide(loggedUser: User): Boolean {
        val loggedUserId = database.getLoggedUserId(loggedUser)
        return database.isLoggedUserHavaAnotherRide(loggedUserId)
    }

    override fun getMyRide(loggedUser: User): Ride? {
        val loggedUserId = database.getLoggedUserId(loggedUser)
        return  database.getRide(loggedUserId, loggedUser)
    }

    override fun getNearByAvailableRide(driverCurrentLocation: Location): List<Ride> {
        return database.getNearByAvailableRide(driverCurrentLocation)
    }

    override fun isUserNameExist(username: String): Boolean {
        return database.isUserNameExist(username)
    }

    override fun acceptRide(ride: Ride): Boolean {
        return when(val response= database.acceptRide(ride)){
            is DbResponse.SuccessfullyCreated -> true
            else->{
                println("${response.getResponse()} -> ${response.getResponseMessage()}")
                false
            }
        }
    }


//    override fun application(user: User) {
//       val loggedUser = accessDependencyFactory.inject(user)
//        val loggedUserid = DbService.getLoggedUserId(loggedUser)
//
//        // printing of welcome message
//        OutputHandler.colorCoatedMessage("welcome ${loggedUser.name} 🙂", TextColor.CYAN)
//
//        when (loggedUser) {
//            is Passenger -> {
//                while (true) {
//                    userInterface.displayPassengerMainMenu()
//                    when (InputHandler.getInt(1, 4)) {
//                        1 -> {
//                            val result: Boolean = DbService.isLoggedUserHavaAnotherRide(loggedUserid)
//                            if (!result) {
//                                val ride = BookingPage.gatherRideDataFromPassenger()
//                                val dbResponse = loggedUser.bookRide(loggedUser, ride)
//                                userInterface.displayResponse(dbResponse, TextColor.GREEN)
//                            } else OutputHandler.colorCoatedMessage(
//                                "you have another ride currently. please check MyRide Menu",
//                                TextColor.YELLOW
//                            )
//                        }
//
//                        2 -> loggedUser.displayMyRide(loggedUserid, loggedUser)
//
//                        3 -> println("feature under development")
//
//                        4 -> if (InputHandler.getString(
//                                "enter 0 to confirm logout or press any key to cancel",
//                                includeNull = true
//                            ) == "0"
//                        ) break
//                    }
//                }
//            }
//
//            is Driver -> {
//                while (true) {
//                    userInterface.displayDriverMainMenu()
//                    when (InputHandler.getInt(1, 4)) {
//                        1 -> {
//                            outer@ while (true) {
//                                val driverCurrentLocation = RidePage.gatherLocation()
//                                while (true) {
//                                    val result = DbService.getNearByAvailableRide(driverCurrentLocation)
//                                    val count = loggedUser.displayAvailableRides(result)
//                                    val input = InputHandler.getInt(
//                                        -1,
//                                        count - 1,
//                                        "enter id number to Accept Ride or( 0 to refresh) or (-1 to go back)"
//                                    )
//                                    var dbResponse: DbResponse
//                                    when (input) {
//                                        -1 -> break@outer
//                                        in 1 until count -> dbResponse = DbService.acceptRide(result[input])
//                                    }
//                                }
//                            }
//                        }
//
//                        2 -> println("feature under development")
//
//                        3 -> println("feature under development")
//
//                        4 -> if (InputHandler.getString(
//                                "enter 0 to confirm logout or press any key to cancel",
//                                includeNull = true
//                            ) == "0"
//                        ) break
//                    }
//                }
//            }
//        }
//        OutputHandler.colorCoatedMessage("successfully Logged out", TextColor.GREEN)
//    }
}

