package database

import library.*
import library.customenum.*
import modules.*
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

internal object Create {
    private lateinit var query: String
    private val connection: Connection = DbConnection.connection()!! //get database connection

    fun insertUser(user: User) : Int{
        query ="insert into ${DbTables.users.name} (`username`,`password`,`name`,`age`,`phone`)value(?,?,?,?,?)"
        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).use {
            it.setString(1, user.username)
            it.setString(2, user.password)
            it.setString(3, user.name)
            it.setInt(4, user.age)
            it.setString(5, user.phone.toString())
            val result = it.executeUpdate()

            if (result == 0) {
                throw SQLException("Insert user failed, no rows affected.")
            }
            val generatedKeys = it.generatedKeys
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1)
            } else {
                throw SQLException("Insert user failed, no ID obtained.")
            }
        }
    }

    fun insertPassenger(userId: Int, aadhaarId: Int, preferredVehicleType: BikeType){
        query = "insert into ${DbTables.passengers.name} (`user_id`,`aadhaar_id`,`preferred_vehicle_type`)value(?,?,?)"
        connection.prepareStatement(query).use {
            it.setInt(1,userId)
            it.setInt(2,aadhaarId)
            it.setString(3,preferredVehicleType.toString())
            val result = it.executeUpdate()
            if (result == 0) {
                throw SQLException("insert passenger failed, no rows affected.")
            }
        }
    }

    fun insertDriver(userId: Int, licenseId: Int, bikeId: Int): DbResponse {
        query = "insert into ${DbTables.drivers.name} (`user_id`,`license_id`,`bike_id`) value(?,?,?)"
        connection.prepareStatement(query).use {
            it.setInt(1,userId)
            it.setInt(2,licenseId)
            it.setInt(3,bikeId)
            val result = it.executeUpdate()
            if (result == 0) {
                return DbResponse.OperationUnsuccessful("unable to add driver into $query in insert ride function")
            }
            return DbResponse.SuccessfullyCreated
        }
    }

    fun insertAadhaar(aadhaar: Aadhaar) : Int{
        query = "insert into ${DbTables.AADHAAR.name} (`aadhaar_no`,`name`,`renewal_date`) value(?,?,?)"
        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).use{
            it.setString(1,aadhaar.aadhaarNo)
            it.setString(2,aadhaar.name)
            it.setString(3,aadhaar.renewalDate)
            val result = it.executeUpdate()
            if (result == 0) {
                throw SQLException("Insert aadhaar failed, no rows affected.")
            }
            val generatedKeys = it.generatedKeys
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1)
            } else {
                throw SQLException("Insert aadhaar failed, no ID obtained.")
            }
        }
    }

    fun insertLicense(license: License): Int {
        query = "insert into ${DbTables.LICENSE.name} (`license_no`,`valid_from`,`valid_till`,`type`)value(?,?,?,?)"
        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).use {
            it.setString(1,license.licenseNo)
            it.setString(2,license.validFrom)
            it.setString(3,license.validTill)
            it.setString(4,license.type)
            val result = it.executeUpdate()
            if (result == 0) {
                throw SQLException("Insert Licence failed, no rows affected.")
            }
            val generatedKeys = it.generatedKeys
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1)
            } else {
                throw SQLException("Insert License failed, no ID obtained.")
            }
        }
    }

    fun insertBike(bike: Bike): Int {
        query = "insert into ${DbTables.BIKE.name} (`rc_book_id`,`used_year`)value(?,?)"
        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).use {
            val rcBookId: Int= DbService.insertRcBook(bike.rcBook)
            it.setInt(1,rcBookId)
            it.setInt(2,bike.usedYear)
            val result = it.executeUpdate()
            if (result == 0) {
                throw SQLException("Insert Bike failed, no rows affected.")
            }
            val generatedKeys = it.generatedKeys
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1)
            } else {
                throw SQLException("Insert Bike failed, no ID obtained.")
            }
        }
    }

    fun insertRcBook(rcBook: RcBook): Int {
        query = "insert into ${DbTables.RC_BOOK.name} (`owner_name`,`model`,`bike_type`,`bike_color`,`valid_from`,`valid_till`,`reg_no`)value(?,?,?,?,?,?,?)"
        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).use {
            it.setString(1,rcBook.ownerName)
            it.setString(2,rcBook.model)
            it.setString(3,rcBook.bikeType.toString())
            it.setString(4,rcBook.bikeColor)
            it.setString(5,rcBook.validFrom)
            it.setString(6,rcBook.validTill)
            it.setString(7,rcBook.regNo)
            val result = it.executeUpdate()
            if (result == 0) {
                throw SQLException("Insert Bike failed, no rows affected.")
            }
            val generatedKeys = it.generatedKeys
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1)
            } else {
                throw SQLException("Insert Bike failed, no ID obtained.")
            }
        }
    }

}
