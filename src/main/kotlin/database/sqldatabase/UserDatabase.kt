package database.sqldatabase

import database.*
import library.AuthenticationResponse
import database.dao.UserDbDao
import library.customenum.DbTables
import models.*
import modules.UserAccess
import java.sql.*

class UserDatabase : UserDbDao {
    private lateinit var query: String
    private val connection: Connection = DbConnection.connection()!!

    override fun insertUser(user: User): Int {
        query = "insert into ${DbTables.users.name} (`username`,`password`,`name`,`age`,`phone`)value(?,?,?,?,?)"
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

    override fun getUser(username: String): AuthenticationResponse {
        query = "SELECT * FROM ${DbTables.users.name} WHERE username = '$username'"
        val response: AuthenticationResponse
        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            val user: User
            if (resultSet.next()) {
                user = getUser(resultSet)
                val userId = resultSet.getInt("id")
                return AuthenticationResponse.UserLoggedIn(userId, user)
            }
            return AuthenticationResponse.UserNotFound
        } catch (e: SQLException) {
            response = AuthenticationResponse.UserNotFound
            response.setResponseMessage(e.message)
            return response
        }
    }

    override fun getUserType(response: AuthenticationResponse.UserLoggedIn): User {
        val finalTable: DbTables = findUserTypeTable(response.userId, DbTables.drivers, DbTables.passengers)
        query = "SELECT * FROM ${finalTable.name} WHERE user_id = '${response.userId}'"
        try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            val resultSet: ResultSet = preparedStatement.executeQuery()

            if (resultSet.next()) {
                 if (finalTable == DbTables.drivers) {
                    return DbService.getDriver(resultSet.getInt("id"))!!
                } else if (finalTable == DbTables.passengers) {
                    return DbService.getPassenger(resultSet.getInt("id"))
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        throw Exception("${response.getResponse()} -> ${response.getResponseMessage()}")
    }

    override fun getUser(id: Int): User {
        query = "SELECT * FROM ${DbTables.users.name} WHERE id = '$id'"
        val preparedStatement: PreparedStatement = connection.prepareStatement(query)
        val resultSet: ResultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            return getUser(resultSet)
        }
        throw throw Exception("0 row matched in $query")
    }

    private fun getUser(resultSet: ResultSet): User {
        return User(
            resultSet.getString("userName"),
            resultSet.getString("password"),
            resultSet.getString("name"),
            resultSet.getInt("age"),
            resultSet.getString("phone").toLong(),
            UserAccess()
        )
    }

    private fun findUserTypeTable(userId: Int, vararg tables: DbTables): DbTables {
        for (table in tables) {
            connection.prepareStatement("SELECT * FROM ${table.name} WHERE user_id = ?").use { preparedStatement ->
                preparedStatement.setInt(1, userId)
                preparedStatement.executeQuery().use { resultSet ->
                    if (resultSet.next()) return table
                }
            }
        }
        throw SQLException("user type not found")
    }

    override fun isUserNameExist(username: String): Boolean {
        query = "SELECT COUNT(*) FROM ${DbTables.users.name} WHERE userName = ?"
        return try {
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            preparedStatement.setString(1, username)
            val resultSet: ResultSet = preparedStatement.executeQuery()
            resultSet.next()
            val count: Int = resultSet.getInt(1)
            count > 0
        } catch (e: SQLException) {
            false
        }
    }

    override fun checkPassword(userName: String, password: String): Boolean {
        query = "SELECT * FROM ${DbTables.users.name} WHERE userName = '$userName'"
        val preparedStatement = connection.prepareStatement(query)
        val resultSet = preparedStatement.executeQuery()
        if (resultSet.next()) {
            val storedPassword = resultSet.getString("password")
            return password == storedPassword
        }
        return false
    }

    override fun getUserId(user: User): Int {
        return DbUtilityFunction.getTablePrimaryKey("SELECT * FROM ${DbTables.users.name} WHERE username = '${user.username}'")
    }
}