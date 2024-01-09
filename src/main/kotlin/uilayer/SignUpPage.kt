package uilayer

import backend.Backend
import library.*
import library.OutputHandler.colorCoatedMessage
import library.customenum.*
<<<<<<< Updated upstream
import modules.*
=======
import models.*
>>>>>>> Stashed changes

internal class SignUpPage() {
    private lateinit var backend: Backend
    private var age: Int? = null
    lateinit var user: User
    lateinit var aadhaar: Aadhaar

    fun injectBackend(backend: Backend) {
        this.backend = backend

    }
    private fun displayUserSignup(): User {
        lateinit var username: String
        lateinit var password: String
        lateinit var name: String
        var response: Boolean
        while (true) {
            username = InputHandler.getString(3, 12, "Enter username")
            response = backend.isUserNameExist(username) // check userName already exist in database
            if (LocalRegex.USERNAME_PATTERN.code.matches(username)) {
                if (response) colorCoatedMessage("User name already exist", TextColor.YELLOW) else break
            } else colorCoatedMessage("Invalid Format Note:(cannot start with numbers, size 3-12, no space))", TextColor.YELLOW)
        }
        while (true) {
            password = InputHandler.getString(8, 15, "Enter your password")
            if (LocalRegex.PASSWORD_PATTERN.code.matches(password)) break else colorCoatedMessage(
                "Invalid Format (must contain(one uppercase, one lowercase, one specialCharacter,size 8-15)",
                TextColor.YELLOW
            )
        }
        while (true) {
            name = InputHandler.getString(3, 50, "Enter your full name")
            if (LocalRegex.NAME_PATTERN.code.matches(name)) break else colorCoatedMessage(
                "Invalid Format Note:(with surname eg:'jack sparrow')",
                TextColor.YELLOW
            )
        }
        val phone: Long = InputHandler.getLong(10, "Enter Mobile Number", "Enter valid mobile number..!")
        return User(username, password, name, age!!, phone)
    }

    fun displayPassengerSignUp(): User {
        age = InputHandler.getInt(15, 80, "Enter Your Age (15 - 80 only eligible)")
        user = displayUserSignup()
        aadhaar = gatherAadhaarData()
        val preferredVehicleType =
            InputHandler.getVehicleType("choose your preferred Vehicle type(SCOOTER/CLASSIC/SPORTS)")
        return Passenger(user.username, user.password, user.name, user.age, user.phone, aadhaar, preferredVehicleType)
    }


    fun displayDriverSignUp(): User {
        age = InputHandler.getInt(21, 40, "Enter Your Age (21 - 40 only eligible)")
        user= displayUserSignup()
        val license: License = gatherLicenseData()
        val bike = gatherBikeData()
        return Driver(user.username, user.password, user.name, user.age, user.phone, license, bike)
    }
    private fun gatherLicenseData(): License {
        var licenseNo: String
        var validFrom: String
        var validTill: String

        while (true) {
            licenseNo = InputHandler.getString(6, 12, "Enter License number").uppercase()
            if (LocalRegex.LICENSE_PATTERN.code.matches(licenseNo)) break
            else colorCoatedMessage("Invalid Format", TextColor.YELLOW)
        }
        while (true) {
            validFrom = InputHandler.getString(10, 10, "enter licensed date format(DD/MM/YYY)")
            if (LocalRegex.DATE_PATTERN.code.matches(validFrom)) break else colorCoatedMessage(
                "Invalid Format",
                TextColor.YELLOW
            )
        }
        while (true) {
            validTill = InputHandler.getString(10, 10, "enter license renewal date format(DD/MM/YYY)")
            if (LocalRegex.DATE_PATTERN.code.matches(validTill)) break else colorCoatedMessage(
                "Invalid Format",
                TextColor.YELLOW
            )
        }
        val type: String = InputHandler.getString("enter license type(gear/non-gear vehicle)")!!
        return License(licenseNo, validFrom, validTill, type)
    }
    private fun gatherAadhaarData(): Aadhaar {
        var aadhaarNo: String
        while (true) {
            aadhaarNo = InputHandler.getString(14,14, "enter your aadhaar number format('XXXX-XXXX-XXXX')")
            if (LocalRegex.AADHAAR_NUMBER_PATTERN.code.matches(aadhaarNo)) break else colorCoatedMessage(
                "Invalid Format",
                TextColor.YELLOW
            )
        }
        val name: String = InputHandler.getString(3, 50, "Enter name as in your Aadhaar", "Invalid input..!")
        lateinit var renewalDate: String
        while (true) {
            renewalDate = InputHandler.getString(10, 10, "enter aadhaar renewal date format(DD/MM/YYY)")
            if (LocalRegex.DATE_PATTERN.code.matches(renewalDate)) break else colorCoatedMessage(
                "Invalid Format",
                TextColor.YELLOW
            )
        }
        return Aadhaar(aadhaarNo, name, renewalDate)
    }

    fun gatherBikeData(): Bike{
        val rcBook = gatherRcBookData()
        val usedYear = InputHandler.getInt(2,"enter your vehicle used year")
        return Bike(rcBook, usedYear)
    }

    private fun gatherRcBookData(): RcBook{
        var ownerName: String
        var validFrom: String
        var validTill: String
        while (true) {
            ownerName = InputHandler.getString(3, 50, "Enter RC-Book owner name")
            if (LocalRegex.NAME_PATTERN.code.matches(ownerName)) break else colorCoatedMessage(
                "Invalid Format Note:(with surname eg:'jack sparrow')",
                TextColor.YELLOW
            )
        }
        val model: String = InputHandler.getString("enter bike model name")!!
        val bikeType: BikeType = InputHandler.getVehicleType("choose your Vehicle type(SCOOTER/CLASSIC/SPORTS)")
        val bikeColor: String = InputHandler.getString("choose your Vehicle color")!!
        while (true) {
            validFrom = InputHandler.getString(10, "enter date of get RC-Book format(DD/MM/YYY)")
            if (LocalRegex.DATE_PATTERN.code.matches(validFrom)) break else colorCoatedMessage(
                "Invalid Format",
                TextColor.YELLOW
            )
        }
        while (true) {
            validTill = InputHandler.getString(10, "enter RC-Book renewal date format(DD/MM/YYY)")
            if (LocalRegex.DATE_PATTERN.code.matches(validTill)) break else colorCoatedMessage(
                "Invalid Format",
                TextColor.YELLOW
            )
        }
        val regNo: String = InputHandler.getString("choose your Vehicle registration number")!!

        return RcBook(ownerName, model, bikeType, bikeColor, validFrom, validTill, regNo)
    }
}