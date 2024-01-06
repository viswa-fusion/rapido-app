package backend

import library.InputHandler



fun main() {
    UtilityFunction.initializeRapidoMap()
   while(true){
       println(InputHandler.getLocation().getShortestDistance(InputHandler.getLocation()))
   }
//
//    val variable = 42
//
//    // Define the size of the printing space using width specifier
//    val formattedString = "Variable value: %20d".format(variable)
//
//    // Print the formatted string
//    println(formattedString)
}




////
////fun main() {
////    Location.GU.addConnection(Location.VA, 5)
////    Location.VA.addConnection(Location.MU, 10)
////    Location.VA.addConnection(Location.PE, 8)
////    Location.PE.addConnection(Location.TM, 3)
////    Location.MU.addConnection(Location.TM, 6)
////    Location.GU.addConnection(Location.PA, 6)
//////    Location.TM.addConnection(Location.CH, 4)
//////    Location.CH.addConnection(Location.PA, 7)
//////    Location.PA.addConnection(Location.CIA, 15)
//////    Location.PA.addConnection(Location.PM, 9)
////
////    val distance = Location.GU.findShortestPath(Location.TM)
////    if (distance != null) {
////        println("Distance between Guduvancheri and InternationalAirport: $distance")
////    } else {
////
////        println("No path found.")
////    }
////
////}
////
////
//import library.customenum.LocalRegex
//import library.customenum.Location
//////
//fun main(){
//// println(Location.values()[2])
////
////    for(value in Location.values()){
////        println(value.name)
////    }
//    UtilityFunction.initializeRapidoMap()
//    while(true){
//        //println(LocalRegex.NAME_PATTERN.code.matches(readln())
////        val n:Long= InputHandler.getLong(10)
////        println(n)
////        println(InputHandler.getString(2,7))
////        regexTest(LocalRegex.AADHAAR_NUMBER_PATTERN)
////        println(InputHandler.getInt(2,4))
////        println("GU, VA, MU, PE, TM, CH, PA, PM, CIA")
////          val s= readln()
////          val d = readln()
////          println(castToLocation(s).getShortestDistance(castToLocation(d)))
//    }
//}
//fun castToLocation(s:String):Location?{
//    return when (s.uppercase()){
//        "GU" -> Location.GU
//        "VA" -> Location.VA
//        "MU" -> Location.MU
//        "PE" -> Location.PE
//        "TM" -> Location.TM
//        "CH" -> Location.CH
//        "PA" -> Location.PA
//        "PM" -> Location.PM
//        "CIA" -> Location.CIA
//        else -> null
//    }
//}
//fun regexTest(regex:LocalRegex){
//    println(regex.code.matches(readln()))
//}

