package modules

import uilayer.UiService
import library.customenum.DbTables

open class User(
    val username: String,
    var password: String,
    val name: String,
    val age: Int,
    val phone: Long,
){
    fun displayMyRide(id: Int, table: DbTables) {
        UiService.displayMyRide(id, table)
    }
}
