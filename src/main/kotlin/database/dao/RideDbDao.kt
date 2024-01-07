package database.dao

import library.DbResponse
import library.customenum.DbTables
import library.customenum.Location
import models.Ride

interface RideDbDao {
    fun insertRide(ride: Ride, passengerId: Int): DbResponse
    fun getMyRide(id: Int, table: DbTables): Ride?
    fun getRideWithPickUpLocation(nearLocation: MutableMap<Location, Int>) : List<Ride>
    fun checkUserHaveRide(loggedUserid: Int): Boolean
    fun updateRide(rideId: Int, ride: Ride, passengerId: Int, DriverId: Int?): DbResponse
}