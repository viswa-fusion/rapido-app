package uilayer

import library.*
import library.customenum.RideStatus
<<<<<<< Updated upstream
import modules.Ride
=======
import library.customenum.TextColor
import models.Ride
>>>>>>> Stashed changes

internal object BookingPage {

    fun gatherRideDataFromPassenger(): Ride {
        OutputHandler.colorCoatedMessage(Constants.map, TextColor.MAGENTA)
        val pickupLocation = InputHandler.getLocation("enter pickup location (eg:GU)")
        val dropLocation = InputHandler.getLocation("enter drop location (eg:PE)")

        return Ride(pickup_location = pickupLocation, drop_location = dropLocation, status = RideStatus.BOOKED)
    }

}