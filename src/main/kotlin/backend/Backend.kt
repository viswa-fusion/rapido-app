package backend

import datalayer.DataBase
import library.DbResponse
import library.customenum.Location
import models.LoginDetails
import models.Ride
import models.User
import uilayer.UserInterface

interface Backend {
    val userInterface : UserInterface
    val database : DataBase
    fun bookRide(loggedUser: User, ride: Ride): Boolean
    fun addNewUser(user: User) : DbResponse
    fun getLoggedUser(loginDetails: LoginDetails): User?
    fun isLoggedUserHavaRide(loggedUser: User): Boolean
    fun getMyRide(loggedUser: User): Ride?
    fun getNearByAvailableRide(driverCurrentLocation: Location): List<Ride>
    fun isUserNameExist(username: String): Boolean
    fun acceptRide(ride: Ride): Boolean

}