package database.sqldatabase


import backend.UtilityFunction
import database.DbConnection
import database.Read
import database.dao.PassengerDao
import library.customenum.BikeType
import library.customenum.DbTables
import modules.Aadhaar
import modules.Passenger
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class PassengerTable : PassengerDao {
    private lateinit var query: String
    private val connection: Connection = DbConnection.connection()!!

    override fun insertPassenger(userId: Int, aadhaarId: Int, preferredVehicleType: BikeType) {
        query = "insert into ${DbTables.passengers.name} (`user_id`,`aadhaar_id`,`preferred_vehicle_type`)value(?,?,?)"
        connection.prepareStatement(query).use {
            it.setInt(1, userId)
            it.setInt(2, aadhaarId)
            it.setString(3, preferredVehicleType.toString())
            val result = it.executeUpdate()
            if (result == 0) {
                throw SQLException("insert passenger failed, no rows affected.")
            }
        }
    }

    override fun getPassenger(id: Int): Passenger {
        query = "SELECT * FROM ${DbTables.passengers.name} WHERE id = '$id'"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()

        if (resultSet.next()) {
            val aadhaar: Aadhaar = Read.getAadhaar(resultSet.getInt("aadhaar_id"))
            val user = Read.getUser(resultSet.getInt("user_id"))
            return Passenger(
                user.username,
                user.password,
                user.name,
                user.age,
                user.phone,
                aadhaar,
                UtilityFunction.castToBikeType(resultSet.getString("preferred_vehicle_type"))!!
            )
        }
        throw throw Exception("0 row matched in $query")
    }

    override fun getPassengerId(userId: Int): Int {
        return DbUtilityFunction.getTablePrimaryKey("SELECT * FROM ${DbTables.passengers.name} WHERE user_id = '${userId}'")
    }

}