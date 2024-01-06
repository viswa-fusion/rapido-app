package database.dao

import library.AuthenticationResponse
import modules.Driver
import modules.User

interface UserDao {
    fun insertUser(user: User) : Int
    fun getUser(username: String): AuthenticationResponse
    fun getUser(id: Int): User
    fun getUserType(response: AuthenticationResponse.UserLoggedIn): User
    fun isUserNameExist(username: String): Boolean
}