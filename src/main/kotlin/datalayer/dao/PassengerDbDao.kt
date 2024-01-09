package datalayer.dao

import library.customenum.BikeType
import modules.Passenger

interface PassengerDao {
    fun insertPassenger(userId: Int, aadhaarId: Int, preferredVehicleType: BikeType)
    fun getPassenger(id: Int): Passenger
    fun getPassengerId(userId: Int): Int
    fun injectUser(userDatabase: UserDbDao)
}