package modules.dao

import models.Ride

interface DriverDao {
    fun displayAvailableRides(rides: List<Ride>): Int
}