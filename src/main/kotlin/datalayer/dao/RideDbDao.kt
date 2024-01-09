package datalayer.dao

import library.DbResponse
import library.customenum.DbTables
import library.customenum.Location
<<<<<<<< Updated upstream:src/main/kotlin/datalayer/dao/RideDao.kt
import modules.Ride
========
import models.Ride
import models.User
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/dao/RideDbDao.kt

interface RideDao {
    fun insertRide(ride: Ride, passengerId: Int): DbResponse
    fun getRide(id: Int, user : User): MutableList<Any>
    fun getRideWithPickUpLocation(nearLocation: MutableMap<Location, Int>) : List<Ride>
}