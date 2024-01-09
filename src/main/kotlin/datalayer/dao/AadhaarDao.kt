package datalayer.dao

import modules.Aadhaar

interface AadhaarDao {
    fun insertAadhaar(aadhaar: Aadhaar) : Int
    fun getAadhaar(aadhaarId: Int): Aadhaar
}