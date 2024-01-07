package database.dao

import library.AuthenticationResponse
import models.User

interface UserDbDao {
    fun insertUser(user: User) : Int
    fun getUser(username: String): AuthenticationResponse
    fun getUser(id: Int): User
    fun getUserType(response: AuthenticationResponse.UserLoggedIn): User
    fun isUserNameExist(username: String): Boolean
    fun checkPassword(userName: String, password: String): Boolean
    fun getUserId(user: User): Int
}