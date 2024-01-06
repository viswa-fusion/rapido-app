package modules

import library.customenum.Location
import library.customenum.RideStatus

data class Ride(
    val passenger: Passenger? = null,
    val driver: Driver? = null,
    val pickup_location: Location,
    val drop_location: Location,
    val start_time: String?=null,
    val end_time: String? = null,
    val status: RideStatus,
    val total_charge: Double = 0.0
)