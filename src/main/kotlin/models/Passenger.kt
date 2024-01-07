package models

import library.DbResponse
import library.customenum.*
import modules.dao.PassengerDao


class Passenger(
    username: String,
    password: String,
    name: String,
    age: Int,
    phone: Long,
    val aadhaar: Aadhaar,
    var preferredVehicleType: BikeType,
    private var access: PassengerDao? = null
) : User(username, password, name, age, phone) {

    fun bookRide(passenger: Passenger, ride: Ride) : DbResponse {
        return access?.addNewRide(passenger,ride)!!
    }

    fun provideAccess(passengerDao: PassengerDao){
        access = passengerDao
    }

    override fun toString(): String {
        return "Passenger($username, $password, $name, $age, $phone, aadhaar=${aadhaar.aadhaarNo}, ${aadhaar.name}, preferredVehicleType=$preferredVehicleType)"
    }


}

