package modules

import database.DbService
import library.DbResponse
import models.Ride
import models.User
import modules.dao.PassengerDao

class PassengerAccess : PassengerDao {
    override fun addNewRide(user: User, ride: Ride): DbResponse {
        return DbService.addNewRide(user, ride)
    }
}