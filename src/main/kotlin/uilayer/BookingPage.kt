package uilayer

import library.*
import library.customenum.RideStatus
import modules.Ride

internal object BookingPage {

    fun gatherRideDataFromPassenger(): Ride {
        UiService.displayRideRotes()
        val pickupLocation = InputHandler.getLocation("enter pickup location (eg:GU)")
        val dropLocation = InputHandler.getLocation("enter drop location (eg:PE)")

        return Ride(pickup_location = pickupLocation, drop_location = dropLocation, status = RideStatus.BOOKED)
    }

}