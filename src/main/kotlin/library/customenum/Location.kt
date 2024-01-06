package library.customenum


enum class Location {
    GU, VA, MU, PE, TM, CH, PA, PM, CIA;

    var map = mutableMapOf<Location, Int>()
    private set

    fun addDestination(nearByLocation: Location, distanceBetween: Int) {
        map[nearByLocation] = distanceBetween
        nearByLocation.map[this] = distanceBetween
    }

    fun getShortestDistance(destination: Location): Int?{
        val currentLocation = mutableListOf(this)
        val checkedLocation = mutableListOf<Location>()
        val measuredLocation = mutableMapOf<Location, Int>()

        measuredLocation[this] = 0

        while (currentLocation.isNotEmpty()){
            val current = currentLocation.removeAt(0)

            if(current == destination) return measuredLocation[current]
            if(checkedLocation.contains(current)) continue

            checkedLocation.add(current)

            for ((nearLocation,distance) in current.map){
                val newMeasuredDistance = measuredLocation[current]!!+distance
                if(newMeasuredDistance < measuredLocation.getOrDefault(nearLocation,Int.MAX_VALUE)){
                        measuredLocation[nearLocation] = newMeasuredDistance
                    currentLocation.add(nearLocation)
                }
            }
        }
        return null
    }
//    fun getNearLocation(): List<Location> {
//        val currentLocation = this
//        var currentDistance = 0
//        val nearLocations = listOf<Location>()
//
//        for ((nearLocation,distance) in currentLocation.map){
//
//        }
//    }
}

//fun Location.distanceBetween(location: Location): Int {
//    var totalKilometer = 0
//    if (this.ordinal < location.ordinal) {
//        for (i in this.ordinal + 1..location.ordinal) {
//
//        }
//    } else {
//
//    }
//    return totalKilometer
//}

private fun Location.distanceFrom(location: Location): Int {
    return when {
        ((this == Location.GU && location == Location.VA) || (this == Location.VA && location == Location.GU)) -> 10

        ((this == Location.VA && location == Location.MU) || (this == Location.MU && location == Location.VA)) -> 2

        ((this == Location.VA && location == Location.PE) || (this == Location.PE && location == Location.VA)) -> 7

        ((this == Location.PE && location == Location.TM) || (this == Location.TM && location == Location.PE)) -> 2

        ((this == Location.MU && location == Location.TM) || (this == Location.TM && location == Location.MU)) -> 8

        ((this == Location.TM && location == Location.CH) || (this == Location.CH && location == Location.TM)) -> 3

        ((this == Location.CH && location == Location.PA) || (this == Location.PA && location == Location.CH)) -> 6

        ((this == Location.PA && location == Location.PM) || (this == Location.PM && location == Location.PA)) -> 3

        ((this == Location.PA && location == Location.CIA) || (this == Location.CIA && location == Location.PA)) -> 4

        else -> 0
    }
}
//
//                           |--------> Mudichor(MU) <-------|                                 |-----------> Pammal(PM)
// Guduvancheri(GU) --> Vandalur(VA)                   Tambaram(TM) --> Chrompet(CH) --> Pallavaram(PA) --> InternationalAirport(CIA)
//                           |------> Perungalathur(PE) <----|
//