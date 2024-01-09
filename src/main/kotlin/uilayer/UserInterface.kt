package uilayer

import backend.Backend
import library.customenum.TextColor
import models.Ride
import models.User

interface UserInterface {
    fun signUp()
    fun signIn()
    fun displayMainMenu()
    fun displaySelectUserMenu()
    fun displayPassengerMainMenu()
    fun displayDriverMainMenu()
    fun displayRideRotes()
    fun displayWelcomeMessage()
    fun displayMyRide(id: Int, user: User)
    fun displayResults(ob: Any, textColor: TextColor)
    fun displayAvailableRides(rides: List<Ride>): Int
    fun injectBackend(backend: Backend)
    fun displayMyRide(ride : Ride)
}