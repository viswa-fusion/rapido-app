package backend

import library.customenum.BikeType
import library.customenum.Location
import library.customenum.RideStatus

class UtilityFunction private constructor(){
    companion object{
        fun initializeRapidoMap(){
            Location.GU.addDestination(Location.VA, 10)
            Location.VA.addDestination(Location.MU, 2)
            Location.VA.addDestination(Location.PE, 7)
            Location.MU.addDestination(Location.TM, 2)
            Location.PE.addDestination(Location.TM, 8)
            Location.TM.addDestination(Location.CH, 3)
            Location.CH.addDestination(Location.PA, 6)
            Location.PA.addDestination(Location.PM, 3)
            Location.PA.addDestination(Location.CIA, 4)
        }

        fun castToLocation(s:String):Location?{
            return when (s.uppercase()){
                "GU" -> Location.GU
                "VA" -> Location.VA
                "MU" -> Location.MU
                "PE" -> Location.PE
                "TM" -> Location.TM
                "CH" -> Location.CH
                "PA" -> Location.PA
                "PM" -> Location.PM
                "CIA" -> Location.CIA
                else -> null
            }
        }

        fun castToRideStatus(status: String): RideStatus?{
            return when(status){
                RideStatus.BOOKED.name -> RideStatus.BOOKED
                RideStatus.COMPLETED.name -> RideStatus.COMPLETED
                RideStatus.CANCEL.name -> RideStatus.CANCEL
                RideStatus.RIDE_START.name -> RideStatus.RIDE_START
                RideStatus.RIDE_END.name -> RideStatus.RIDE_END
                RideStatus.PAYMENT_PENDING.name -> RideStatus.PAYMENT_PENDING
                else -> null
            }
        }

        fun castToBikeType(s:String) : BikeType?{
            return when(s) {
                "SCOOTER" -> BikeType.SCOOTER
                "CLASSIC" -> BikeType.CLASSIC
                "SPORTS"  -> BikeType.SPORTS
                else -> null
            }
        }
    }
}
