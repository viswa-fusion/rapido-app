package datalayer.sqldatabase

import backend.UtilityFunction
import backend.UtilityFunction.Companion.castToLocation
<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/RideTable.kt
import database.DbConnection
import database.DbService
import database.dao.RideDao
import library.DbResponse
import library.customenum.DbTables
import library.customenum.Location
import modules.Ride
========
import datalayer.DbConnection
import datalayer.DbService
import datalayer.dao.DriverDbDao
import datalayer.dao.PassengerDbDao
import datalayer.dao.RideDbDao
import library.DbResponse
import library.customenum.DbTables
import library.customenum.Location
import library.customenum.RideStatus
import models.Driver
import models.Passenger
import models.Ride
import models.User
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/RideDatabase.kt
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/RideTable.kt
class RideTable : RideDao {
========
class RideDatabase(private val passengerDatabase: PassengerDbDao) : RideDbDao {
    private val driverDatabase: DriverDbDao = DriverDatabase()
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/RideDatabase.kt
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


    override fun getRide(id: Int, user : User): MutableList<Any> {
        return when (user) {
            is Passenger -> {
                query = "SELECT * FROM ${DbTables.rides} WHERE passenger_id ='$id'"

                getMyRide(query)
            }

            is Driver -> {
                query = "SELECT * FROM ${DbTables.rides} WHERE driver_id ='$id'"
                getMyRide(query)
            }

            else -> mutableListOf()
        }
    }

    private fun getMyRide(query: String): MutableList<Any> {

        val rideData = mutableListOf<Any>()

        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/RideTable.kt
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
========
            rideData.add(resultSet.getInt("passenger_id"))
            rideData.add(resultSet.getInt("driver_id"))
            rideData.add(castToLocation(resultSet.getString("pickup_location"))!!)
            rideData.add(castToLocation(resultSet.getString("drop_location"))!!)
            rideData.add(resultSet.getString("start_time"))
            rideData.add(resultSet.getString("end_time"))
            rideData.add(UtilityFunction.castToRideStatus(resultSet.getString("status"))!!)
            rideData.add(resultSet.getDouble("total_charge"))
//            return Ride(
//                PassengerDatabase. .getPassengerId(resultSet.getInt("passenger_id")),
//                DbService.getDriver(),
//                castToLocation(resultSet.getString("pickup_location"))!!,
//                castToLocation(resultSet.getString("drop_location"))!!,
//                resultSet.getString("start_time"),
//                resultSet.getString("end_time"),
//                UtilityFunction.castToRideStatus(resultSet.getString("status"))!!,
//                resultSet.getDouble("total_charge"),
//            )
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/RideDatabase.kt
        }
        return rideData
    }
    override fun getRideWithPickUpLocation(nearLocation: MutableMap<Location, Int>): List<Ride> {
        val listOfRide = ArrayList<Ride>()
<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/RideTable.kt
        var ride: Ride
        nearLocation.forEach{(location,_)->
            query = "SELECT * FROM ${DbTables.rides} WHERE pickup_location ='${location.name}'"
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                ride= Ride(
                    DbService.getPassenger(resultSet.getInt("passenger_id")),
                    DbService.getDriver(resultSet.getInt("driver_id")),
========

        nearLocation.forEach { (location, _) ->
            query = "SELECT * FROM ${DbTables.rides} " +
                    "WHERE pickup_location ='${location.name}' " +
                    "AND status NOT IN ('${RideStatus.COMPLETED.name}', '${RideStatus.CANCEL.name}')"
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                val ride = Ride(
                    passengerDatabase.getPassenger(resultSet.getInt("passenger_id")),
                    driverDatabase.getDriver(resultSet.getInt("driver_id")),
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/RideDatabase.kt
                    castToLocation(resultSet.getString("pickup_location"))!!,
                    castToLocation(resultSet.getString("drop_location"))!!,
                    resultSet.getString("start_time"),
                    resultSet.getString("end_time"),
                    UtilityFunction.castToRideStatus(resultSet.getString("status"))!!,
                    resultSet.getDouble("total_charge")
                )
                listOfRide.add(ride)
            }
        }
        return listOfRide
    }
}