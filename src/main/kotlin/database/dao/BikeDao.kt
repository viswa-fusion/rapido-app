package database.dao

import modules.Bike

interface BikeDao {
    fun insertBike(bike: Bike): Int
    fun getBike(id: Int): Bike
}