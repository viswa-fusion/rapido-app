package uilayer

import backend.castToLocation
import library.InputHandler
import library.OutputHandler
import library.customenum.Location
import library.customenum.TextColor

internal object RidePage {
    fun gatherLocation(): Location {
        UiService.displayRideRotes()
        while (true) {
            val location = castToLocation(InputHandler.getString(2, "enter your current location"))
            if (location != null) return location
            OutputHandler.colorCoatedMessage("invalid Location ⚠️", TextColor.RED)
        }
    }

}