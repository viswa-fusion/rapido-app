package database.dao

import library.DbResponse
import models.Driver

interface DriverDbDao {
    fun insertDriver(userId: Int, licenseId: Int, bikeId: Int): DbResponse
    fun getDriver(id: Int): Driver?
    fun getDriverId(userId: Int): Int
}