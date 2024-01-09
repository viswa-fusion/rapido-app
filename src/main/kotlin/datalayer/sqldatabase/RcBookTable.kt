package datalayer.sqldatabase

import backend.UtilityFunction
<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/RcBookTable.kt
import database.DbConnection
import database.dao.RcBookDao
========
import datalayer.DbConnection
import datalayer.dao.RcBookDbDao
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/RcBookDatabase.kt
import library.customenum.DbTables
import modules.RcBook
import java.sql.*

class RcBookTable :RcBookDao {
    private lateinit var query: String
    private val connection: Connection = DbConnection.connection()!!

    override fun insertRcBook(rcBook: RcBook): Int {
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

    override fun getRcBook(id: Int): RcBook {
        query = "SELECT * FROM ${DbTables.RC_BOOK.name} WHERE id = '$id'"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            return RcBook(
                resultSet.getString("owner_name"),
                resultSet.getString("model"),
                UtilityFunction.castToBikeType(resultSet.getString("bike_type"))!!,
                resultSet.getString("bike_color"),
                resultSet.getString("valid_from"),
                resultSet.getString("valid_till"),
                resultSet.getString("reg_no"),
            )
        }
        throw throw Exception("0 row matched in $query")
    }
}