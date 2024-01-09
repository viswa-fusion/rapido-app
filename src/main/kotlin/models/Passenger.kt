package models

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
    override fun toString(): String {
        return "Passenger($username, $password, $name, $age, $phone, aadhaar=${aadhaar.aadhaarNo}, ${aadhaar.name}, preferredVehicleType=$preferredVehicleType)"
    }

}

