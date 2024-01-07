package database.dao

import library.customenum.BikeType
import models.Passenger

interface PassengerDbDao {
    fun insertPassenger(userId: Int, aadhaarId: Int, preferredVehicleType: BikeType)
    fun getPassenger(id: Int): Passenger
    fun getPassengerId(userId: Int): Int
}