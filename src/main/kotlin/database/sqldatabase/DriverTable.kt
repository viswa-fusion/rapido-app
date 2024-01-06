package database.sqldatabase

import database.*
import database.DbService.getUser
import database.dao.DriverDao
import library.DbResponse
import library.customenum.DbTables
import modules.Driver
import java.sql.*

class DriverTable: DriverDao {
    private lateinit var query: String
    private val connection: Connection = DbConnection.connection()!!

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
            val user = getUser(resultSet.getInt("user_id"))
            return Driver(
                user.username,
                user.password,
                user.name,
                user.age,
                user.phone,
                DbService.getLicense(resultSet.getInt("license_id")),
                Read.getBike(resultSet.getInt("bike_id"))
            )
        }
        return null
    }

    override fun getDriverId(userId: Int): Int {
        return DbUtilityFunction.getTablePrimaryKey("SELECT * FROM ${DbTables.drivers.name} WHERE user_id = '${userId}'")
    }
}