package database.sqldatabase

import backend.UtilityFunction
import backend.UtilityFunction.Companion.castToLocation
import database.DbConnection
import database.DbService
import database.dao.RideDao
import library.DbResponse
import library.customenum.DbTables
import library.customenum.Location
import modules.Ride
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class RideTable : RideDao {
    private lateinit var query: String
    private val connection: Connection = DbConnection.connection()!!

    override fun insertRide(ride: Ride, passengerId: Int): DbResponse {
        query =
            "insert into ${DbTables.rides.name} (`passenger_id`,`pickup_location`,`drop_location`,`status`,`total_charge`)value(?,?,?,?,?)"
        connection.prepareStatement(query).use {
            it.setInt(1, passengerId)
            it.setString(2, ride.pickup_location.toString())
            it.setString(3, ride.drop_location.toString())
            it.setString(4, ride.status.toString())
            it.setDouble(5, ride.total_charge)
            val result = it.executeUpdate()
            if (result == 0) {
                return DbResponse.OperationUnsuccessful(
                    "unable to add $ride into ${DbTables.users.name} in insert ride function"
                )
            }
            return DbResponse.SuccessfullyCreated
        }
    }


    override fun getMyRide(id: Int, table: DbTables): Ride? {
        return when (table) {
            DbTables.passengers -> {
                query = "SELECT * FROM ${DbTables.rides} WHERE passenger_id ='$id'"
                getMyRide(query)
            }

            DbTables.drivers -> {
                query = "SELECT * FROM ${DbTables.rides} WHERE driver_id ='$id'"
                getMyRide(query)
            }

            else -> null
        }
    }

    private fun getMyRide(query: String): Ride? {
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            return Ride(
                DbService.getPassenger(resultSet.getInt("passenger_id")),
                DbService.getDriver(resultSet.getInt("driver_id")),
                 castToLocation(resultSet.getString("pickup_location"))!!,
                castToLocation(resultSet.getString("drop_location"))!!,
                resultSet.getString("start_time"),
                resultSet.getString("end_time"),
                UtilityFunction.castToRideStatus(resultSet.getString("status"))!!,
                resultSet.getDouble("total_charge"),
            )
        }
        return null
    }
    override fun getRideWithPickUpLocation(nearLocation: MutableMap<Location, Int>): List<Ride> {

        val listOfRide = ArrayList<Ride>()
        var ride: Ride
        nearLocation.forEach{(location,_)->
            query = "SELECT * FROM ${DbTables.rides} WHERE pickup_location ='${location.name}'"
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                ride= Ride(
                    DbService.getPassenger(resultSet.getInt("passenger_id")),
                    DbService.getDriver(resultSet.getInt("driver_id")),
                    castToLocation(resultSet.getString("pickup_location"))!!,
                    castToLocation(resultSet.getString("drop_location"))!!,
                    resultSet.getString("start_time"),
                    resultSet.getString("end_time"),
                    UtilityFunction.castToRideStatus(resultSet.getString("status"))!!,
                    resultSet.getDouble("total_charge"),
                )
                listOfRide.add(ride)
            }
        }
        return listOfRide
    }
}