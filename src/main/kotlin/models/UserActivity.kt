package models

import library.customenum.LogStatus

data class UserActivity(val user: User, val stats: LogStatus, val time: String)