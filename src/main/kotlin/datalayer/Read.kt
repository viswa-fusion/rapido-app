package database

import backend.UtilityFunction.Companion.castToBikeType
import backend.UtilityFunction.Companion.castToRideStatus
import library.*
import library.customenum.*
import modules.*
import modules.Driver
import java.sql.*

internal object Read {
    private lateinit var query: String
    private val connection: Connection = DbConnection.connection()!! //get database connection

    fun isUserNameExist(username: String): Boolean {
        query = "SELECT COUNT(*) FROM ${DbTables.users.name} WHERE userName = ?"
        return try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            preparedStatement.setString(1, username)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            resultSet.next()
            val count: Int = resultSet.getInt(1)
            count > 0
        } catch (e: SQLException) {
            false
        }
    }

    fun checkPassword(userName: String, password: String): Boolean {
        query = "SELECT * FROM ${DbTables.users.name} WHERE userName = '$userName'"
        val preparedStatement = connection.prepareStatement(query)
        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            val storedPassword = resultSet.getString("password")
            return password == storedPassword
        }
        return false
    }

    fun getUser(username: String): AuthenticationResponse {
        query = "SELECT * FROM ${DbTables.users.name} WHERE username = '$username'"
        val response: AuthenticationResponse
        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            val user: User
            if (resultSet.next()) {
                user = getUser(resultSet)
                val userId = resultSet.getInt("id")
                return AuthenticationResponse.UserLoggedIn(userId, user)
            }
            return AuthenticationResponse.UserNotFound
        } catch (e: SQLException) {
            response = AuthenticationResponse.UserNotFound
            response.setResponseMessage(e.message)
            return response
        }
    }

    fun getUser(id: Int): User {
        query = "SELECT * FROM ${DbTables.users.name} WHERE id = '$id'"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            return getUser(resultSet)
        }
        throw throw Exception("0 row matched in $query")
    }

    private fun getUser(resultSet: ResultSet): User {
        return User(
            resultSet.getString("userName"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getInt("age"),
            resultSet.getString("phone").toLong()
        )
    }

    fun getUserType(response: AuthenticationResponse.UserLoggedIn): User {
        val finalTable: DbTables = findUserTypeTable(response.userId, DbTables.drivers, DbTables.passengers)
        val query = "SELECT * FROM ${finalTable.name} WHERE user_id = '${response.userId}'"
        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            lateinit var userType: User
            if (resultSet.next()) {
                if (finalTable == DbTables.drivers) {
                    val license: License = DbService.getLicense(resultSet.getInt("license_id"))
                    val bike: Bike = DbService.getBike(resultSet.getInt("bike_id"))
                    userType = Driver(
                        response.user.username,
                        response.user.password,
                        response.user.name,
                        response.user.age,
                        response.user.phone,
                        license,
                        bike
                    )
                } else if (finalTable == DbTables.passengers) {
                    val aadhaar: Aadhaar = DbService.getAadhaar(resultSet.getInt("aadhaar_id"))
                    userType = Passenger(
                        response.user.username,
                        response.user.password,
                        response.user.name,
                        response.user.age,
                        response.user.phone,
                        aadhaar,
                        castToBikeType(resultSet.getString("preferred_vehicle_type"))!!
                    )
                }
                return userType
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        throw Exception("${response.getResponse()} -> ${response.getResponseMessage()}")
    }

    fun getAadhaar(aadhaarId: Int): Aadhaar {
        query = "SELECT * FROM ${DbTables.AADHAAR.name} WHERE id = '$aadhaarId'"
        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            lateinit var aadhaar: Aadhaar
            if (resultSet.next()) {
                aadhaar = Aadhaar(
                    resultSet.getString("aadhaar_no"),
                    resultSet.getString("name"),
                    resultSet.getString("renewal_date")
                )
            }
            return aadhaar
        } catch (e: SQLException) {
            throw SQLException("aadhaar not found")
        } catch (e: NullPointerException) {
            throw NullPointerException("aadhaar not found")
        }
    }

    private fun findUserTypeTable(userId: Int, vararg tables: DbTables): DbTables {
        for (table in tables) {
            connection.prepareStatement("SELECT * FROM ${table.name} WHERE user_id = ?").use { preparedStatement ->
                preparedStatement.setInt(1, userId)
                preparedStatement.executeQuery().use { resultSet ->
                    if (resultSet.next()) return table
                }
            }
        }
        throw SQLException("user type not found")
    }

    fun getPassenger(id: Int): Passenger {
        query = "SELECT * FROM ${DbTables.passengers.name} WHERE id = '$id'"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()

        if (resultSet.next()) {
            val aadhaar: Aadhaar = getAadhaar(resultSet.getInt("aadhaar_id"))
            val user = getUser(resultSet.getInt("user_id"))
            return Passenger(
                user.username,
                user.password,
                user.name,
                user.age,
                user.phone,
                aadhaar,
                castToBikeType(resultSet.getString("preferred_vehicle_type"))!!
            )
        }
        throw throw Exception("0 row matched in $query")
    }

    fun getLicense(id: Int): License {
        query = "SELECT * FROM ${DbTables.LICENSE.name} WHERE id = '$id'"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()

        if (resultSet.next()) {
            return License(
                resultSet.getString("license_no"),
                resultSet.getString("valid_from"),
                resultSet.getString("valid_till"),
                resultSet.getString("type")
            )
        }
        throw throw Exception("0 row matched in $query")
    }

    fun getDriver(id: Int): Driver? {
        query = "SELECT * FROM ${DbTables.drivers.name} WHERE id = '$id'"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()

        if (resultSet.next()) {
            val user = getUser(resultSet.getInt("user_id"))
            return Driver(
                user.username,
                user.password,
                user.name,
                user.age,
                user.phone,
                DbService.getLicense(resultSet.getInt("license_id")),
                getBike(resultSet.getInt("bike_id"))
            )
        }
        return null
    }

    fun getBike(id: Int): Bike {
        query = "SELECT * FROM ${DbTables.BIKE.name} WHERE id = '$id'"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            return Bike(
                DbService.getRcBook(resultSet.getInt("rc_book_id")),
                resultSet.getInt("used_year")
            )
        }
        throw throw Exception("0 row matched in $query")
    }

    fun getRcBook(id: Int): RcBook {
        query = "SELECT * FROM ${DbTables.RC_BOOK.name} WHERE id = '$id'"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            return RcBook(
                resultSet.getString("owner_name"),
                resultSet.getString("model"),
                castToBikeType(resultSet.getString("bike_type"))!!,
                resultSet.getString("bike_color"),
                resultSet.getString("valid_from"),
                resultSet.getString("valid_till"),
                resultSet.getString("reg_no"),
            )
        }
        throw throw Exception("0 row matched in $query")
    }



    fun getUserId(user: User): Int {
        return getTablePrimaryKey("SELECT * FROM ${DbTables.users.name} WHERE username = '${user.username}'")
    }

    fun getPassengerId(userId: Int): Int {
        return getTablePrimaryKey("SELECT * FROM ${DbTables.passengers.name} WHERE user_id = '${userId}'")
    }

    fun getTablePrimaryKey(query: String): Int {

        connection.prepareStatement(query).use { preparedStatement ->
            preparedStatement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    return resultSet.getInt("id")
                }
            }
        }
        throw Exception("0 row matched in $query")
    }


    fun getNearByAvailableRide(currentLocation: Location): List<Ride> {

        val nearLocation = currentLocation.map

        return listOf<Ride>()
    }

    fun getDriverId(userId: Int): Int {
        return getTablePrimaryKey("SELECT * FROM ${DbTables.drivers.name} WHERE user_id = '${userId}'")
    }
}