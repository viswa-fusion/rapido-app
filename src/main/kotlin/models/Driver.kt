package models

import modules.DriverAccess
import modules.dao.DriverDao

class Driver(
    username: String,
    password: String,
    name: String,
    age: Int,
    phone: Long,
    val license: License,
    val bike: Bike,
    private var access: DriverDao? = null
) : User(username, password, name, age, phone) {

    fun displayAvailableRides(rides: List<Ride>): Int {
        return access?.displayAvailableRides(rides)!!
    }

    fun provideAccess(driverDao: DriverDao) {
        access = driverDao
    }
}
