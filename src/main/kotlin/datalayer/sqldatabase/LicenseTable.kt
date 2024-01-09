package datalayer.sqldatabase

<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/LicenseTable.kt
import database.DbConnection
import database.dao.LicenseDao
========
import datalayer.DbConnection
import datalayer.dao.LicenseDbDao
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/LicenseDatabase.kt
import library.customenum.DbTables
import modules.License
import java.sql.*

class LicenseTable: LicenseDao {
    private lateinit var query: String
    private val connection: Connection = DbConnection.connection()!!

    override fun insertLicense(license: License): Int {
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

    override fun getLicense(id: Int): License {
        query = "SELECT * FROM ${DbTables.LICENSE.name} WHERE id = '$id'"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()

        if (resultSet.next()) {
            return License(
                resultSet.getString("license_no"),
                resultSet.getString("valid_from"),
                resultSet.getString("valid_till"),
                resultSet.getString("type")
            )
        }
        throw throw Exception("0 row matched in $query")
    }
}