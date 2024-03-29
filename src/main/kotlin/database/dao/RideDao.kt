package database.dao

import library.DbResponse
import library.customenum.DbTables
import library.customenum.Location
import modules.Ride

interface RideDao {
    fun insertRide(ride: Ride, passengerId: Int): DbResponse
    fun getMyRide(id: Int, table: DbTables): Ride?
    fun getRideWithPickUpLocation(nearLocation: MutableMap<Location, Int>) : List<Ride>
}