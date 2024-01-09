package base

import backend.*
import datalayer.DataBase
import datalayer.DbService
import datalayer.dao.*
import datalayer.sqldatabase.*
import uilayer.UiService
import library.*
import library.customenum.TextColor
import uilayer.UserInterface

var appRunStatus = true
fun main() {

    val passengerDatabase: PassengerDbDao = PassengerDatabase()
    val driverDatabase: DriverDbDao = DriverDatabase()
    val userDatabase: UserDbDao = UserDatabase(passengerDatabase,driverDatabase)
    val rideDatabase: RideDbDao = RideDatabase(passengerDatabase)
    val aadhaarDatabase: AadhaarDbDao = AadhaarDatabase()
    val licenseDatabase: LicenseDbDao = LicenseDatabase()
    val rcBookDatabase: RcBookDbDao = RcBookDatabase()
    val bikeDatabase: BikeDbDao = BikeDatabase()
    val userInterface: UserInterface = UiService() // user interface


    passengerDatabase.injectUser(userDatabase)
    driverDatabase.injectUser(userDatabase)

    // database
    val database: DataBase = DbService(
        userDatabase,
        passengerDatabase,
        driverDatabase,
        rideDatabase,
        aadhaarDatabase,
        licenseDatabase,
        rcBookDatabase,
        bikeDatabase
    )
    val backend: Backend = System(userInterface, database) // backend
    userInterface.injectBackend(backend) // inject backend to ui


    UtilityFunction.initializeRapidoMap()
    userInterface.displayWelcomeMessage()
    while (appRunStatus) {
        userInterface.displayMainMenu()
        when (InputHandler.getInt(1, 3)) {
            1 -> userInterface.signUp()
            2 -> userInterface.signIn()
            3 -> closeApp()
        }
    }
    appRunStatus = true
}

fun closeApp() {
    appRunStatus = false
    OutputHandler.colorCoatedMessage("thanks for visiting See you again \uD83D\uDE01", TextColor.GREEN)
}