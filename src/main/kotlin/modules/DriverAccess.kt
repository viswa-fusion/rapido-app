package modules

import models.Ride
import modules.dao.DriverDao
import uilayer.UiService

class DriverAccess : DriverDao {
    override fun displayAvailableRides(rides: List<Ride>): Int {
        return UiService.displayAvailableRides(rides)
    }
}