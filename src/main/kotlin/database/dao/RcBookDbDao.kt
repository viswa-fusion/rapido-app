package database.dao

import models.RcBook

interface RcBookDbDao {
    fun insertRcBook(rcBook: RcBook): Int
    fun getRcBook(id: Int): RcBook
}