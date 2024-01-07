package database

import database.dao.UserDbDao
import database.sqldatabase.*
import library.*
import library.customenum.*
import models.*
import models.Driver
import java.sql.*

object DbService {
    private val userDatabase :UserDbDao = UserDatabase()
    private val passengerDatabase = PassengerDatabase()
    private val driverDatabase = DriverDatabase()
    private val rideDatabase = RideDatabase()
    private val aadhaarDatabase = AadhaarDatabase()
    private val licenseDatabase = LicenseDatabase()
    private val rcBookDatabase = RcBookDatabase()
    private val bikeDatabase = BikeDatabase()

    private fun addUser(user: User): Int {
        return userDatabase.insertUser(user)
    }

    fun getUser(userName: String): AuthenticationResponse {
        return userDatabase.getUser(userName)
    }

    fun getUser(id: Int): User {
        return userDatabase.getUser(id)
    }

    fun getLoggedUser(response: AuthenticationResponse.UserLoggedIn): User {
        return userDatabase.getUserType(response)
    }

    //<-------------------------------------------------------------------------------------------------------->

    fun addNewPassenger(passenger: Passenger): DbResponse {
        return try {
            val userId = addUser(passenger)
            val aadhaarId = addAadhaar(passenger.aadhaar)
            passengerDatabase.insertPassenger(userId, aadhaarId, passenger.preferredVehicleType)
            DbResponse.SuccessfullyCreated
        } catch (e: SQLException) {
            DbResponse.OperationUnsuccessful(e.message)
        }
    }

    //<-------------------------------------------------------------------------------------------------------->

    private fun addLicense(license: License): Int {
        return licenseDatabase.insertLicense(license)
    }

    fun getLicense(id: Int): License {
        return licenseDatabase.getLicense(id)
    }

    //<-------------------------------------------------------------------------------------------------------->
    private fun addBike(bike: Bike): Int {
        return bikeDatabase.insertBike(bike)
    }

    //<-------------------------------------------------------------------------------------------------------->
    fun addNewDriver(driver: Driver): DbResponse {
        val userId = addUser(driver)
        val licenseId = addLicense(driver.license)
        val bikeId = addBike(driver.bike)
        return driverDatabase.insertDriver(userId, licenseId, bikeId)
    }

    //<-------------------------------------------------------------------------------------------------------->
    fun addNewRide(user : User, ride: Ride): DbResponse {
        val passengerId =getLoggedUserId(user)
        return rideDatabase.insertRide(ride, passengerId)
    }

    fun getMyRide(id: Int, table: DbTables): Ride {
        return rideDatabase.getMyRide(id, table)!!
    }
    //<-------------------------------------------------------------------------------------------------------->

    private fun addAadhaar(aadhaar: Aadhaar): Int{
        return aadhaarDatabase.insertAadhaar(aadhaar)
    }

    //<-------------------------------------------------------------------------------------------------------->


    fun getLoggedUserId(user: User): Int {
        val userId = userDatabase.getUserId(user)
        return when(user){
            is Passenger -> passengerDatabase.getPassengerId(userId)
            is Driver -> driverDatabase.getDriverId(userId)
            else -> throw SQLException("not a valid user")
        }
    }

    fun isUserNameExist(userName: String): Boolean {
//      return isUsernameExist(username,"userCredential") || isUsernameExist(username,"bikerCredential")
        return userDatabase.isUserNameExist(userName)
    }

    fun isValidCredential(userName: String, password: String): AuthenticationResponse {
        if (isUserNameExist(userName))
            return if (userDatabase.checkPassword(userName, password)) {
                AuthenticationResponse.UserFound
            } else AuthenticationResponse.InvalidPassword
        return AuthenticationResponse.InvalidUsername
    }

    fun getNearByAvailableRide(currentLocation: Location): List<Ride> {
        val nearLocation = currentLocation.map
        nearLocation[currentLocation] = 0
        val result = rideDatabase.getRideWithPickUpLocation(nearLocation)
        result.forEach {
            it.total_charge = convertToCommissionRate(it.total_charge)
        }
        return result
    }
    private fun convertToCommissionRate(totalCharge: Double):Double{
        val commissionPercentage = 20.0
       return totalCharge - (totalCharge * (commissionPercentage/ 100.0))
    }
    fun getAadhaar(id: Int): Aadhaar {
        return aadhaarDatabase.getAadhaar(id)
    }

    fun getDriver(id: Int): Driver? {
        return driverDatabase.getDriver(id)
    }

    fun getBike(id: Int): Bike {
        return bikeDatabase.getBike(id)
    }

    fun getRcBook(id: Int): RcBook {
        return rcBookDatabase.getRcBook(id)
    }

    fun insertRcBook(rcBook: RcBook): Int {
        return rcBookDatabase.insertRcBook(rcBook)
    }

    fun getPassenger(id: Int): Passenger {
        return passengerDatabase.getPassenger(id)
    }

    fun addRcBook(rcBook: RcBook): Int {
        return rcBookDatabase.insertRcBook(rcBook)
    }

    fun isLoggedUserHavaAnotherRide(loggedUserid: Int): Boolean {
        return rideDatabase.checkUserHaveRide(loggedUserid)
    }

    fun acceptRide(ride: Ride): DbResponse {
        return DbResponse.OperationUnsuccessful("function not created")
    }
}