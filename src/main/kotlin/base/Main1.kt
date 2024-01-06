package base

import backend.UtilityFunction
import uilayer.UiService
import library.*
import library.customenum.TextColor


fun main() {
    UtilityFunction.initializeRapidoMap()
    UiService.displayWelcomeMessage()
    while (appRunStatus) {
        UiService.displayMainMenu()
        when (InputHandler.getInt(1, 3)) {
            1 -> UiService.signUp()
            2 -> UiService.signIn()
            3 -> closeApp()
        }
    }
    appRunStatus = true
}

