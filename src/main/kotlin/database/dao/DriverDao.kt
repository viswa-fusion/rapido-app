package database.dao

import library.DbResponse
import modules.Driver

interface DriverDao {
    fun insertDriver(userId: Int, licenseId: Int, bikeId: Int): DbResponse
    fun getDriver(id: Int): Driver?
    fun getDriverId(userId: Int): Int
}