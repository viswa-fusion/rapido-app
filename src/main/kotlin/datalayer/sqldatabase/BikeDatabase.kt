package datalayer.sqldatabase

<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/BikeTable.kt
import database.DbConnection
import database.DbService
import database.dao.BikeDao
========
import datalayer.DbConnection
import datalayer.DbService
import datalayer.dao.BikeDbDao
import datalayer.dao.RcBookDbDao
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/BikeDatabase.kt
import library.customenum.DbTables
import modules.Bike
import java.sql.*

<<<<<<<< Updated upstream:src/main/kotlin/datalayer/sqldatabase/BikeTable.kt
class BikeTable :BikeDao {
========
class BikeDatabase :BikeDbDao {
    private val rcBookDatabase: RcBookDbDao = RcBookDatabase()
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/sqldatabase/BikeDatabase.kt
    private lateinit var query: String
    private val connection: Connection = DbConnection.connection()!!

    override fun insertBike(bike: Bike): Int {
        query = "insert into ${DbTables.BIKE.name} (`rc_book_id`,`used_year`)value(?,?)"
        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS).use {
            val rcBookId: Int= rcBookDatabase.insertRcBook(bike.rcBook)
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
    override fun getBike(id: Int): Bike {
        query = "SELECT * FROM ${DbTables.BIKE.name} WHERE id = '$id'"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            return Bike(
                rcBookDatabase.getRcBook(resultSet.getInt("rc_book_id")),
                resultSet.getInt("used_year")
            )
        }
        throw throw Exception("0 row matched in $query")
    }

}