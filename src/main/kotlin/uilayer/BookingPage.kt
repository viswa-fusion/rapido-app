package uilayer

import library.*
import library.customenum.Location
import library.customenum.RideStatus
import models.Ride

internal object BookingPage {

    fun gatherRideDataFromPassenger(): Ride {
        UiService.displayRideRotes()
        val pickupLocation = InputHandler.getLocation("enter pickup location (eg:GU)")
        val dropLocation = InputHandler.getLocation("enter drop location (eg:PE)")
        val totalKiloMeter = pickupLocation.getShortestDistance(dropLocation)
        val totalCharge = Location.calculateTotalCharge(totalKiloMeter ?: 0)
        return Ride(pickup_location = pickupLocation, drop_location = dropLocation, status = RideStatus.CREATED, total_charge = totalCharge)
    }

}