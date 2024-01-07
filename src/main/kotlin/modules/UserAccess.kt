package modules

import models.User
import modules.dao.UserDao
import uilayer.UiService

class UserAccess : UserDao{
    override fun displayMyRide(id: Int, table: User) {
        UiService.displayMyRide(id, table)
    }
}