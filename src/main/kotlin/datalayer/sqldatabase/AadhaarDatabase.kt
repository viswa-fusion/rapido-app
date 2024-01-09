package datalayer.sqldatabase

<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/AadhaarTable.kt
import database.Create
import database.DbConnection
import database.Read
import database.dao.AadhaarDao
========
import datalayer.DbConnection
import datalayer.dao.AadhaarDbDao
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/AadhaarDatabase.kt
import library.customenum.*
import modules.Aadhaar
import java.sql.*

class AadhaarTable: AadhaarDao {
    private lateinit var query: String
    private val connection: Connection = DbConnection.connection()!!

    override fun insertAadhaar(aadhaar: Aadhaar) : Int{
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

    override fun getAadhaar(aadhaarId: Int): Aadhaar {
        query = "SELECT * FROM ${DbTables.AADHAAR.name} WHERE id = '$aadhaarId'"
        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            lateinit var aadhaar: Aadhaar
            if (resultSet.next()) {
                aadhaar = Aadhaar(
                    resultSet.getString("aadhaar_no"),
                    resultSet.getString("name"),
                    resultSet.getString("renewal_date")
                )
            }
            return aadhaar
        } catch (e: SQLException) {
            throw SQLException("aadhaar not found")
        } catch (e: NullPointerException) {
            throw NullPointerException("aadhaar not found")
        }
    }
}