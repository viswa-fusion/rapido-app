package uilayer

import library.InputHandler
import library.OutputHandler
import library.Constants
import library.customenum.Location
import library.customenum.TextColor

internal object RidePage {
    fun gatherLocation(): Location {
        OutputHandler.colorCoatedMessage(Constants.map, TextColor.MAGENTA)
        return InputHandler.getLocation()
    }

}