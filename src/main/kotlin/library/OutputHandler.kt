package library

import library.customenum.TextColor

object OutputHandler {

    fun colorCoatedMessage(message: String, color: TextColor) {
        println("$color$message${TextColor.RESET}")
    }

}