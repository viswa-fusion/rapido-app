package library

import backend.UtilityFunction.Companion.castToLocation
import library.OutputHandler.colorCoatedMessage
import library.customenum.*
import library.customenum.TextColor


object InputHandler {
    fun getInt(
        min: Int,
        max: Int,
        message: String = "enter your choice",
        retryMessage: String = "invalid input try again..!"
    ): Int {
        var num: Int?
        println(message)
        do {
            num = readLine()?.toIntOrNull()
        } while ((num == null || num !in min..max ).also { if (it) colorCoatedMessage(retryMessage, TextColor.RED) })
        return num!!
    }

    fun getInt(
        message: String = "enter your choice",
        retryMessage: String = "invalid input try again..!"
    ): Int {
        var num: Int?
        println(message)
        do {
            num = readLine()?.toIntOrNull()
        } while ((num == null).also { if (it) colorCoatedMessage(retryMessage, TextColor.RED) })
        return num!!
    }

    fun getInt(
        size: Int,
        message: String = "enter your choice",
        retryMessage: String = "invalid input try again..!"
    ): Int {
        var num: Int?
        println(message)
        do {
            num = readLine()?.toIntOrNull()
        } while ((num == null || num.getSize() != size).also {
                if (it) colorCoatedMessage(
                    retryMessage,
                    TextColor.RED
                )
            })
        return num!!
    }

    fun getString(
        minSize: Int,
        maxSize: Int,
        message: String = "enter your input",
        retryMessage: String = "invalid input try again..!"
    ): String {
        var string: String?
        println(message)
        do {
            string = readLine()
        } while ((string == null || string.length !in minSize..maxSize).also {
                if (it) colorCoatedMessage(
                    retryMessage,
                    TextColor.RED
                )
            })
        return string!!
    }

    fun getString(
        size: Int,
        message: String = "enter your input",
        retryMessage: String = "invalid input try again..!"
    ) : String {
        var string: String?
        println(message)
        do {
            string = readLine()
        } while ((string == null || string.length != size).also {
                if (it) colorCoatedMessage(
                    retryMessage,
                    TextColor.RED
                )
            })
        return string!!
    }

    fun getString(
        message: String,
        retryMessage: String = "invalid input try again..!",
        includeNull: Boolean = false
    ): String? {
        var string: String?
        println(message)
        return if(includeNull) {
            readLine()
        } else{
            do {
                string = readLine()
            } while ((string == null).also {
                    if (it) colorCoatedMessage(
                        retryMessage,
                        TextColor.RED
                    )
                })
            string!!
        }
    }

    fun getLong(size: Long, message: String = "enter long number", retryMessage: String = "Invalid Input..!"): Long {
        var longNum: Long?
        println(message)
        do {
            longNum = readLine()?.toLongOrNull()
        } while ((longNum == null || longNum.geSize() != size).also {
                if (it) colorCoatedMessage(
                    retryMessage,
                    TextColor.RED
                )
            })
        return longNum!!
    }

    fun getIntWithMinusOne(
        min: Int,
        max: Int,
        message: String = "enter your choice",
        retryMessage: String = "invalid input try again..!"
    ): Int {
        var num: Int?
        println(message)
        do {
            num = readLine()?.toIntOrNull()
        } while ((num == null || (num !in min..max && num != -1)).also { if (it) colorCoatedMessage(retryMessage, TextColor.RED) })
        return num!!
    }

    fun getVehicleType(message: String): BikeType {
        var preferredVehicleType: BikeType?
        do {
            preferredVehicleType = when (getString(message)?.uppercase()) {
                "SCOOTER" -> BikeType.SCOOTER
                "CLASSIC" -> BikeType.CLASSIC
                "SPORTS" -> BikeType.SPORTS
                else -> null
            }
        } while ((preferredVehicleType == null).also {
                if (it) colorCoatedMessage(
                    "Invalid Input..!",
                    TextColor.YELLOW
                )
            })

        return preferredVehicleType!!
    }

    fun getLocation(message: String = "enter your current location"): Location {
        while (true) {
            val location = castToLocation(getString(2, message))
            if (location != null) return location
            colorCoatedMessage("invalid Location ⚠️", TextColor.RED)
        }
    }

    private fun Int.getSize(): Int {
        var count = 0
        var temp = this
        while (temp > 0) {
            count++
            temp /= 10
        }
        return count
    }

    private fun Long.geSize(): Long {
        var count = 0L
        var temp = this
        while (temp > 0) {
            count++
            temp /= 10
        }
        return count
    }
}