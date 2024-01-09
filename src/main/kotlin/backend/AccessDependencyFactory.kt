//package backend
//
//import models.*
//import modules.*
//
//class AccessDependencyFactory {
//    fun inject(user : User):User{
//        val passengerAccess =PassengerAccess()
//        val driverAccess = DriverAccess()
//        when(user){
//            is Passenger -> user.provideAccess(passengerAccess)
//            is Driver -> user.provideAccess(driverAccess)
//        }
//        return user
//    }
//}