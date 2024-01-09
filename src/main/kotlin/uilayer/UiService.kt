package uilayer

<<<<<<< Updated upstream
import base.*
=======
import backend.Backend
>>>>>>> Stashed changes
import library.*
import library.customenum.*
import library.OutputHandler.colorCoatedMessage
import modules.*

class UiService() : UserInterface {
    private lateinit var backend: Backend
    private val signUpPage = SignUpPage()
    override fun injectBackend(backend: Backend) {
        this.backend = backend
        signUpPage.injectBackend(backend)
    }

    override fun signUp() {
        displaySelectUserMenu()
<<<<<<< Updated upstream
        val response: DbResponse =
        when (InputHandler.getIntWithMinusOne(1, 2)) {
            1 -> SignUpPage.displayPassengerSignUp()
            2 -> SignUpPage.displayDriverSignUp()
            else-> DbResponse.SignupFailed
        }
        if (response.getResponse() == 200) {
            displayResponse(response, TextColor.GREEN)
            if (InputHandler.getString("enter 0 to login or press any key to exit", includeNull = true) == "0") signIn()
        }
    }

    fun signIn() {
        var response: AuthenticationResponse
        var dbResponse: DbResponse
        var loggedUser: User
=======
        val user: User? = when (InputHandler.getIntWithMinusOne(1, 2)) {
            1 -> signUpPage.displayPassengerSignUp()
            2 -> signUpPage.displayDriverSignUp()
            else -> null
        }
        if (user != null) {
            val response = backend.addNewUser(user)
            displayResults(response, TextColor.GREEN)
            if (InputHandler.getString("enter 0 to login or press any key to exit", includeNull = true) == "0")
                signIn()
            println("thanks for choosing rapido 😁")
        }
    }

    override fun signIn() {
        var loggedUser: User?
>>>>>>> Stashed changes
        while (true) {
            val loginDetails = SignInPage.displaySignInPage()
            loggedUser = backend.getLoggedUser(loginDetails)
            if (loggedUser != null) {
                loggedIn(loggedUser)
                break
            } else displayResults("user not found", TextColor.YELLOW)
        }
//        colorCoatedMessage("successfully Logged out", TextColor.GREEN)
    }

    private fun loggedIn(loggedUser: User) {
//        val accessDependencyFactory = AccessDependencyFactory()
//        val loggedUser = accessDependencyFactory.inject(user)
//        val loggedUserid = DbService.getLoggedUserId(loggedUser)

        // printing of welcome message
        colorCoatedMessage("welcome ${loggedUser.name} 🙂", TextColor.CYAN)

        when (loggedUser) {

            is Passenger -> {
                var result: Boolean
                while (true) {
                    displayPassengerMainMenu()
                    when (InputHandler.getInt(1, 4)) {
                        1 -> {
                            result = backend.isLoggedUserHavaRide(loggedUser)
                            if (!result) {
                                val ride = BookingPage.gatherRideDataFromPassenger()
                                result = backend.bookRide(loggedUser, ride)
                                if (result) displayResults("successfully created", TextColor.GREEN)
                                else displayResults("unfortunately can't book ride now", TextColor.RED)
                            } else
                                colorCoatedMessage(
                                    "you have another ride currently. please check MyRide Menu",
                                    TextColor.YELLOW
                                )
                        }

                        2 -> {
                            val ride: Ride? = backend.getMyRide(loggedUser)
                            if (ride != null) displayMyRide(ride)
                            else colorCoatedMessage("no Ride Available..❕", TextColor.YELLOW)
                        }

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
                while (true) {
                    displayDriverMainMenu()
                    when (InputHandler.getInt(1, 4)) {
                        1 -> {
                            outer@ while (true) {
                                val driverCurrentLocation = RidePage.gatherLocation()
                                while (true) {
                                    val result = backend.getNearByAvailableRide(driverCurrentLocation)
                                    val count = displayAvailableRides(result)
                                    val input = InputHandler.getInt(
                                        -1,
                                        count - 1,
                                        "enter id number to Accept Ride or( 0 to refresh) or (-1 to go back)"
                                    )
                                    when (input) {
                                        -1 -> break@outer
                                        in 1 until count -> {
                                            val result = backend.acceptRide(result[input])
                                            if (result) colorCoatedMessage(
                                                "successfully Accept the ride",
                                                TextColor.GREEN
                                            )else colorCoatedMessage("unable to accept the ride", TextColor.RED)
                                        }
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
<<<<<<< Updated upstream

        val loggedUserid = DbService.getLoggedUserId(loggedUser)
        colorCoatedMessage("welcome ${loggedUser.name} 🙂", TextColor.CYAN) // printing of welcome message

        when (loggedUser) {
            is Passenger -> {
                while (true) {
                    displayPassengerMainMenu()
                    when (InputHandler.getInt(1, 4)) {
                        1 -> {
                            val ride = BookingPage.gatherRideDataFromPassenger()
                            dbResponse = loggedUser.bookRide(loggedUser, ride)
                            displayResponse(dbResponse, TextColor.GREEN)
                        }

                        2 -> loggedUser.displayMyRide(loggedUserid, DbTables.passengers)

                        4 -> if (InputHandler.getString(
                                "enter 0 to confirm logout or press any key to cancel",
                                includeNull = true
                            ) == "0"
                        ) break
                    }
                }
            }

            is Driver -> {
                while (true) {
                    displayDriverMainMenu()
                    when (InputHandler.getInt(1, 4)) {
                        1 -> {
                            val driverCurrentLocation = RidePage.gatherLocation()
                            val result = DbService.getNearByAvailableRide(driverCurrentLocation)
                            displayRides(result)
                        }

                        4 -> if (InputHandler.getString(
                                "enter 0 to confirm logout or press any key to cancel",
                                includeNull = true
                            ) == "0"
                        ) break
                    }
                }
            }
        }
=======
>>>>>>> Stashed changes
        colorCoatedMessage("successfully Logged out", TextColor.GREEN)
    }

    override fun displayMainMenu() {
        colorCoatedMessage(
            """1 -> Sign_Up
            |2 -> Sign_In
            |3 -> Exit""".trimMargin("|"), TextColor.PEACH
        )
    }

    override fun displaySelectUserMenu() {
        colorCoatedMessage(
            """1 -> Passenger
            |2 -> Driver""".trimMargin("|"), TextColor.PEACH
        )
    }

<<<<<<< Updated upstream
    private fun displayPassengerMainMenu() {
=======
    override fun displayPassengerMainMenu() {
>>>>>>> Stashed changes
        colorCoatedMessage(
            """1 -> Book Ride
                |2 -> My Ride
                |3 -> View Profile
                |4 -> Logout
            """.trimMargin("|"), TextColor.PEACH
        )
    }

<<<<<<< Updated upstream
    private fun displayDriverMainMenu() {
=======
    override fun displayDriverMainMenu() {
>>>>>>> Stashed changes
        colorCoatedMessage(
            """1 -> Check Available Ride
                |2 -> My Ride
                |3 -> View Profile
                |4 -> Logout
            """.trimMargin("|"), TextColor.PEACH
        )
    }

    override fun displayRideRotes() {
        colorCoatedMessage(Constants.map, TextColor.MAGENTA)
    }

    override fun displayWelcomeMessage() {
        colorCoatedMessage(Constants.welcomeMessage, TextColor.BLUE)
    }

<<<<<<< Updated upstream
    fun displayMyRide(id: Int, table: DbTables) {
        //println(DbService.getMyRide(id, table))
=======
    override fun displayMyRide(id: Int, user: User) {
        when (user) {
            is Passenger -> {
                println(backend.getMyRide(user))
            }

            is Driver -> {
                println("under development")
            }
        }
>>>>>>> Stashed changes
    }

    override fun displayResults(ob: Any, textColor: TextColor) {
        when (ob) {
            is DbResponse -> {
                colorCoatedMessage("${ob.getResponse()} -> ${ob.getResponseMessage()}", textColor)
            }

            is AuthenticationResponse -> {
                colorCoatedMessage("${ob.getResponse()} -> ${ob.getResponseMessage()}", textColor)
            }
        }
    }
<<<<<<< Updated upstream
    fun displayRides(rides : List<Ride>){
=======

    override fun displayAvailableRides(rides: List<Ride>): Int {
        var id = 1
>>>>>>> Stashed changes
        colorCoatedMessage(
            """
                +----------------------------+
                | Available Ride             |
                +---------------------------------------------------------------------------------------+
                | Passenger Name             | Pickup Location      | Drop Location        | Price      |
                +----------------------------+----------------------+----------------------+------------+
            """.trimIndent(), TextColor.PEACH
        )
        rides.forEach{
            colorCoatedMessage("""
        | %-26s | %-20s | %-20s | ${TextColor.GREEN}₹%-9.2f${TextColor.PEACH} |                                                   
        |____________________________|______________________|______________________|____________|
    """.trimIndent().format(it.passenger?.username, it.pickup_location, it.drop_location, it.total_charge), TextColor.PEACH
            )
        }
    }

    override fun displayMyRide(ride: Ride) {
        colorCoatedMessage(
            """_______________________________________________________________________________________________
               |passenger = ${ride.passenger?.username} |   driver = ${ride.driver?.username ?: "not assigned"}|     
               -----------------------------------------+-----------------------------------------------------  
               | Ride = ${ride.pickup_location} -> ${ride.drop_location}                                     |
               -----------------------------------------+----------------------------------------------------- 
               | RideStatus = ${ride.status}            |    price = ${ride.total_charge}                    |
               -----------------------------------------+----------------------------------------------------- 
            """.trimIndent(), TextColor.PEACH
        )
    }
}