package datalayer.dao

import library.AuthenticationResponse
<<<<<<<< Updated upstream:src/main/kotlin/datalayer/dao/UserDao.kt
import modules.Driver
import modules.User
========
import models.LoginDetails
import models.User
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/dao/UserDbDao.kt

interface UserDao {
    fun insertUser(user: User) : Int
    fun getUser(username: String): AuthenticationResponse
    fun getUser(id: Int): User
    fun getUserType(response: AuthenticationResponse.UserLoggedIn): User
    fun isUserNameExist(username: String): Boolean
<<<<<<<< Updated upstream:src/main/kotlin/datalayer/dao/UserDao.kt
========
    fun checkPassword(loginDetails: LoginDetails): Boolean
    fun getUserId(user: User): Int
>>>>>>>> Stashed changes:src/main/kotlin/datalayer/dao/UserDbDao.kt
}