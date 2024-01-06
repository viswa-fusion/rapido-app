package modules

import library.customenum.BikeType

data class RcBook(
    val ownerName: String,
    var model: String,
    var bikeType: BikeType,
    val bikeColor: String,
    val validFrom: String,
    var validTill: String,
    val regNo: String
)
