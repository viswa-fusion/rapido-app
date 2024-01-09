package datalayer.sqldatabase

<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/DriverTable.kt
import database.*
import database.DbService.getUser
import database.dao.DriverDao
========
import datalayer.*
import datalayer.dao.BikeDbDao
import datalayer.dao.DriverDbDao
import datalayer.dao.LicenseDbDao
import datalayer.dao.UserDbDao
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/DriverDatabase.kt
import library.DbResponse
import library.customenum.DbTables
import modules.Driver
import java.sql.*

<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/DriverTable.kt
class DriverTable: DriverDao {
========
class DriverDatabase: DriverDbDao {
    private lateinit var userDatabase: UserDbDao
    private val licenseDatabase: LicenseDbDao = LicenseDatabase()
    private val bikeDatabase: BikeDbDao = BikeDatabase()
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/DriverDatabase.kt
    private lateinit var query: String
    private val connection: Connection = DbConnection.connection()!!
    override fun injectUser(userDatabase: UserDbDao){
        this.userDatabase =userDatabase
    }
    override fun insertDriver(userId: Int, licenseId: Int, bikeId: Int): DbResponse {
        query = "insert into ${DbTables.drivers.name} (`user_id`,`license_id`,`bike_id`) value(?,?,?)"
        connection.prepareStatement(query).use {
            it.setInt(1,userId)
            it.setInt(2,licenseId)
            it.setInt(3,bikeId)
            val result = it.executeUpdate()
            if (result == 0) {
                return DbResponse.OperationUnsuccessful("unable to add driver into $query in insert ride function")
            }
            return DbResponse.SuccessfullyCreated
        }
    }

    override fun getDriver(id: Int): Driver? {
        query = "SELECT * FROM ${DbTables.drivers.name} WHERE id = '$id'"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()

        if (resultSet.next()) {
<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/DriverTable.kt
            val user = getUser(resultSet.getInt("user_id"))
========
            val user = userDatabase.getUser(resultSet.getInt("user_id"))
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/DriverDatabase.kt
            return Driver(
                user.username,
                user.password,
                user.name,
                user.age,
                user.phone,
<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/DriverTable.kt
                DbService.getLicense(resultSet.getInt("license_id")),
                Read.getBike(resultSet.getInt("bike_id"))
========
                licenseDatabase.getLicense(resultSet.getInt("license_id")),
                bikeDatabase.getBike(resultSet.getInt("bike_id"))
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/DriverDatabase.kt
            )
        }
        return null
    }

    override fun getDriverId(userId: Int): Int {
        return DbUtilityFunction.getTablePrimaryKey("SELECT * FROM ${DbTables.drivers.name} WHERE user_id = '${userId}'")
    }
}