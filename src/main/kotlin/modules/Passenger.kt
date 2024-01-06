package modules

import database.DbService
import library.DbResponse
import library.customenum.*


class Passenger(
    username: String,
    password: String,
    name: String,
    age: Int,
    phone: Long,
    val aadhaar: Aadhaar,
    var preferredVehicleType: BikeType
) : User(username, password, name, age, phone) {

    fun bookRide(passenger: Passenger, ride: Ride) : DbResponse {
        return DbService.addNewRide(passenger, ride)
    }

    override fun toString(): String {
        return "Passenger($username $password $name $age $phone aadhaar=${aadhaar.aadhaarNo} ${aadhaar.name}, preferredVehicleType=$preferredVehicleType)"
    }

}

