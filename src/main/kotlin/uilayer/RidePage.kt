package uilayer

import library.InputHandler
import library.customenum.Location

internal object RidePage {
    fun gatherLocation(): Location {
        UiService.displayRideRotes()
        return InputHandler.getLocation()
    }

}