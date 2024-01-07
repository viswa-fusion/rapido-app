package models

import modules.UserAccess
import modules.dao.UserDao

open class User(
    val username: String,
    var password: String,
    val name: String,
    val age: Int,
    val phone: Long,
    private val access: UserDao = UserAccess()
) {
    fun displayMyRide(id: Int, table: User) {
        access.displayMyRide(id, table)
    }

}
