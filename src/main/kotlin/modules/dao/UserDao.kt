package modules.dao

import models.User

interface UserDao {
    fun displayMyRide(id: Int, table: User)
}