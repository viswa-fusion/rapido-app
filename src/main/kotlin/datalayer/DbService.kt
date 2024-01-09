package datalayer

<<<<<<< Updated upstream:src/main/kotlin/database/DbService.kt
import database.sqldatabase.*
=======
import datalayer.dao.*
>>>>>>> Stashed changes:src/main/kotlin/datalayer/DbService.kt
import library.*
import library.customenum.*
import modules.*
import modules.Driver
import java.sql.*

<<<<<<< Updated upstream:src/main/kotlin/database/DbService.kt
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
=======
class DbService(
    private val userDatabase: UserDbDao,
    private val passengerDatabase: PassengerDbDao,
    private val driverDatabase: DriverDbDao ,
    private val rideDatabase: RideDbDao,
    private val aadhaarDatabase: AadhaarDbDao,
    private val licenseDatabase: LicenseDbDao,
    private val rcBookDatabase: RcBookDbDao,
    private val bikeDatabase: BikeDbDao
)  : DataBase{
    override fun addUser(user: User): Int {
        return userDatabase.insertUser(user)
    }

    override fun getUser(userName: String): AuthenticationResponse {
        return userDatabase.getUser(userName)
    }

    override fun getUser(id: Int): User {
        return userDatabase.getUser(id)
    }

    override fun getLoggedUser(response: AuthenticationResponse.UserLoggedIn): User {
        return userDatabase.getUserType(response)
>>>>>>> Stashed changes:src/main/kotlin/datalayer/DbService.kt
    }

    //<-------------------------------------------------------------------------------------------------------->

    override fun addNewPassenger(passenger: Passenger): DbResponse {
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

<<<<<<< Updated upstream:src/main/kotlin/database/DbService.kt
    private fun addLicense(license: License): Int {
        return licenseTable.insertLicense(license)
    }

    fun getLicense(id: Int): License {
        return licenseTable.getLicense(id)
    }

    //<-------------------------------------------------------------------------------------------------------->
    private fun addBike(bike: Bike): Int {
        return bikeTable.insertBike(bike)
=======
    override fun addLicense(license: License): Int {
        return licenseDatabase.insertLicense(license)
    }

    override fun getLicense(id: Int): License {
        return licenseDatabase.getLicense(id)
    }

    //<-------------------------------------------------------------------------------------------------------->
    override fun addBike(bike: Bike): Int {
        return bikeDatabase.insertBike(bike)
>>>>>>> Stashed changes:src/main/kotlin/datalayer/DbService.kt
    }

    //<-------------------------------------------------------------------------------------------------------->
    override fun addNewDriver(driver: Driver): DbResponse {
        val userId = addUser(driver)
        val licenseId = addLicense(driver.license)
        val bikeId = addBike(driver.bike)
        return driverTable.insertDriver(userId, licenseId, bikeId)
    }

    //<-------------------------------------------------------------------------------------------------------->
<<<<<<< Updated upstream:src/main/kotlin/database/DbService.kt
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
=======
    override fun addNewRide(user: User, ride: Ride): DbResponse {
        val passengerId = getLoggedUserId(user)
        return rideDatabase.insertRide(ride, passengerId)
    }

    override fun getRide(id: Int, user: User): Ride? {
        val rideData = rideDatabase.getRide(id, user)
        return if (rideData.isNotEmpty()) {
            val passenger = getPassenger(rideData[0] as Int)
            val driver = getDriver(rideData[1] as Int)
            Ride(
                passenger,
                driver,
                rideData[2] as Location,
                rideData[3] as Location,
                rideData[4] as String?,
                rideData[5] as String?,
                rideData[6] as RideStatus,
                rideData[7] as Double
            )
        } else null
    }
    //<-------------------------------------------------------------------------------------------------------->

    override fun addAadhaar(aadhaar: Aadhaar): Int {
        return aadhaarDatabase.insertAadhaar(aadhaar)
>>>>>>> Stashed changes:src/main/kotlin/datalayer/DbService.kt
    }

    //<-------------------------------------------------------------------------------------------------------->


<<<<<<< Updated upstream:src/main/kotlin/database/DbService.kt
    fun getLoggedUserId(user: User): Int {
        val userId = Read.getUserId(user)
        return when(user){
            is Passenger -> Read.getPassengerId(userId)
            is Driver -> Read.getDriverId(userId)
=======
    override fun getLoggedUserId(user: User): Int {
        val userId = userDatabase.getUserId(user)
        return when (user) {
            is Passenger -> passengerDatabase.getPassengerId(userId)
            is Driver -> driverDatabase.getDriverId(userId)
>>>>>>> Stashed changes:src/main/kotlin/datalayer/DbService.kt
            else -> throw SQLException("not a valid user")
        }
    }

    override fun isUserNameExist(userName: String): Boolean {
//      return isUsernameExist(username,"userCredential") || isUsernameExist(username,"bikerCredential")
        return userTable.isUserNameExist(userName)
    }

<<<<<<< Updated upstream:src/main/kotlin/database/DbService.kt
    fun isValidCredential(userName: String, password: String): AuthenticationResponse {
        if (isUserNameExist(userName))
            return if (Read.checkPassword(userName, password)) {
=======
    override fun isValidCredential(loginDetails: LoginDetails): AuthenticationResponse {
        if (isUserNameExist(loginDetails.userName))
            return if (userDatabase.checkPassword(loginDetails)) {
>>>>>>> Stashed changes:src/main/kotlin/datalayer/DbService.kt
                AuthenticationResponse.UserFound
            } else AuthenticationResponse.InvalidPassword
        return AuthenticationResponse.InvalidUsername
    }

    override fun getNearByAvailableRide(currentLocation: Location): List<Ride> {
        val nearLocation = currentLocation.map
<<<<<<< Updated upstream:src/main/kotlin/database/DbService.kt
        return rideTable.getRideWithPickUpLocation(nearLocation)
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
=======
        nearLocation[currentLocation] = 0
        val rideList = rideDatabase.getRideWithPickUpLocation(nearLocation)

        rideList.forEach{
            it.total_charge = convertToCommissionRate(it.total_charge)
        }
        return rideList
    }

    override fun convertToCommissionRate(totalCharge: Double): Double {
        val commissionPercentage = 20.0
        return totalCharge - (totalCharge * (commissionPercentage / 100.0))
    }

    override fun getAadhaar(id: Int): Aadhaar {
        return aadhaarDatabase.getAadhaar(id)
    }

    override fun getDriver(id: Int): Driver? {
        return driverDatabase.getDriver(id)
    }

    override fun getBike(id: Int): Bike {
        return bikeDatabase.getBike(id)
    }

    override fun getRcBook(id: Int): RcBook {
        return rcBookDatabase.getRcBook(id)
    }

    override fun getPassenger(id: Int): Passenger {
        return  passengerDatabase.getPassenger(id)
    }

    override fun addRcBook(rcBook: RcBook): Int {
        return rcBookDatabase.insertRcBook(rcBook)
    }

    override fun isLoggedUserHavaAnotherRide(loggedUserid: Int): Boolean {
        return rideDatabase.checkUserHaveRide(loggedUserid)
    }

    override fun acceptRide(ride: Ride): DbResponse {
        return DbResponse.OperationUnsuccessful("function not created")
>>>>>>> Stashed changes:src/main/kotlin/datalayer/DbService.kt
    }
}