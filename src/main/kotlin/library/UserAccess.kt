package library

import uilayer.UiService

class UserAccess : PassengerAccess, DriverAccess{

    fun getMap(){
        UiService.displayRideRotes()
    }

    fun bookRide(){

    }
}