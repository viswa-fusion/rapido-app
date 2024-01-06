package base

import uilayer.UiService
import library.*

fun main() {
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
