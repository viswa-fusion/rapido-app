package database.dao

import models.Aadhaar

interface AadhaarDbDao {
    fun insertAadhaar(aadhaar: Aadhaar) : Int
    fun getAadhaar(aadhaarId: Int): Aadhaar
}