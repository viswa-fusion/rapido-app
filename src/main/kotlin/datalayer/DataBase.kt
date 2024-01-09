package datalayer

import library.AuthenticationResponse
import library.DbResponse
import library.customenum.DbTables
import library.customenum.Location
import models.*

interface DataBase {
    fun addUser(user: User): Int
    fun getUser(userName: String): AuthenticationResponse
    fun getUser(id: Int): User
    fun getLoggedUser(response: AuthenticationResponse.UserLoggedIn): User
    fun addNewPassenger(passenger: Passenger): DbResponse
    fun addLicense(license: License): Int
    fun getLicense(id: Int): License
    fun addBike(bike: Bike): Int
    fun addNewDriver(driver: Driver): DbResponse
    fun addNewRide(user : User, ride: Ride): DbResponse
    fun getRide(id: Int, user: User): Ride?
    fun addAadhaar(aadhaar: Aadhaar): Int
    fun getLoggedUserId(user: User): Int
    fun isUserNameExist(userName: String): Boolean
    fun isValidCredential(loginDetails: LoginDetails): AuthenticationResponse
    fun getNearByAvailableRide(currentLocation: Location): List<Ride>
    fun convertToCommissionRate(totalCharge: Double):Double
    fun getAadhaar(id: Int): Aadhaar
    fun getDriver(id: Int): Driver?
    fun getBike(id: Int): Bike
    fun getRcBook(id: Int): RcBook
    fun getPassenger(id: Int): Passenger
    fun addRcBook(rcBook: RcBook): Int
    fun isLoggedUserHavaAnotherRide(loggedUserid: Int): Boolean
    fun acceptRide(ride: Ride): DbResponse
}