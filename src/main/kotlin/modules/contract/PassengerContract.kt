package modules.dao

import library.DbResponse
import models.Ride
import models.User

interface PassengerDao {
    fun addNewRide(user : User, ride: Ride): DbResponse
}