package database

import database.sqldatabase.*
import library.*
import library.customenum.*
import modules.*
import modules.Driver
import java.sql.*

object DbService {
    private val userTable = UserTable()
    private val passengerTable = PassengerTable()
    private val driverTable = DriverTable()
    private val rideTable = RideTable()
    private val aadhaarTable = AadhaarTable()
    private val licenseTable = LicenseTable()
    private val rcBookTable = RcBookTable()
    private val bikeTable = BikeTable()

    private fun addUser(user: User): Int {
        return userTable.insertUser(user)
    }

    fun getUser(userName: String): AuthenticationResponse {
        return userTable.getUser(userName)
    }

    fun getUser(id: Int): User {
        return userTable.getUser(id)
    }

    fun getLoggedUser(response: AuthenticationResponse.UserLoggedIn): User {
        return userTable.getUserType(response)
    }

    //<-------------------------------------------------------------------------------------------------------->

    fun addNewPassenger(passenger: Passenger): DbResponse {
        return try {
            val userId = addUser(passenger)
            val aadhaarId = addAadhaar(passenger.aadhaar)
            passengerTable.insertPassenger(userId, aadhaarId, passenger.preferredVehicleType)
            DbResponse.SuccessfullyCreated
        } catch (e: SQLException) {
            DbResponse.OperationUnsuccessful(e.message)
        }
    }

    //<-------------------------------------------------------------------------------------------------------->

    private fun addLicense(license: License): Int {
        return licenseTable.insertLicense(license)
    }

    fun getLicense(id: Int): License {
        return licenseTable.getLicense(id)
    }

    //<-------------------------------------------------------------------------------------------------------->
    private fun addBike(bike: Bike): Int {
        return bikeTable.insertBike(bike)
    }

    //<-------------------------------------------------------------------------------------------------------->
    fun addNewDriver(driver: Driver): DbResponse {
        val userId = addUser(driver)
        val licenseId = addLicense(driver.license)
        val bikeId = addBike(driver.bike)
        return driverTable.insertDriver(userId, licenseId, bikeId)
    }

    //<-------------------------------------------------------------------------------------------------------->
    fun addNewRide(user : User, ride: Ride): DbResponse {
        val passengerId =getLoggedUserId(user)
        return rideTable.insertRide(ride, passengerId)
    }

    fun getMyRide(id: Int, table: DbTables): Ride {
        return rideTable.getMyRide(id, table)!!
    }
    //<-------------------------------------------------------------------------------------------------------->

    private fun addAadhaar(aadhaar: Aadhaar): Int{
        return aadhaarTable.insertAadhaar(aadhaar)
    }

    //<-------------------------------------------------------------------------------------------------------->


    fun getLoggedUserId(user: User): Int {
        val userId = Read.getUserId(user)
        return when(user){
            is Passenger -> Read.getPassengerId(userId)
            is Driver -> Read.getDriverId(userId)
            else -> throw SQLException("not a valid user")
        }
    }

    fun isUserNameExist(userName: String): Boolean {
//      return isUsernameExist(username,"userCredential") || isUsernameExist(username,"bikerCredential")
        return userTable.isUserNameExist(userName)
    }

    fun isValidCredential(userName: String, password: String): AuthenticationResponse {
        if (isUserNameExist(userName))
            return if (Read.checkPassword(userName, password)) {
                AuthenticationResponse.UserFound
            } else AuthenticationResponse.InvalidPassword
        return AuthenticationResponse.InvalidUsername
    }

    fun getNearByAvailableRide(RadiusInKiloMeter: Location): List<Ride> {

        return Read.getNearByAvailableRide(RadiusInKiloMeter)
    }

    fun getAadhaar(id: Int): Aadhaar {
        return Read.getAadhaar(id)
    }

    fun getDriver(id: Int): Driver? {
        return Read.getDriver(id)
    }

    fun getBike(id: Int): Bike {
        return Read.getBike(id)
    }

    fun getRcBook(id: Int): RcBook {
        return Read.getRcBook(id)
    }

    fun insertRcBook(rcBook: RcBook): Int {
        return Create.insertRcBook(rcBook)
    }

    fun getPassenger(id: Int): Passenger {
        return Read.getPassenger(id)
    }

    fun addRcBook(rcBook: RcBook): Int {
        return rcBookTable.insertRcBook(rcBook)
    }
}