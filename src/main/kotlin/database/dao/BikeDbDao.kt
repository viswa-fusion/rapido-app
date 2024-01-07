package database.dao

import models.Bike

interface BikeDbDao {
    fun insertBike(bike: Bike): Int
    fun getBike(id: Int): Bike
}