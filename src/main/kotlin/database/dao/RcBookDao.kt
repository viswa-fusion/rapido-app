package database.dao

import modules.RcBook

interface RcBookDao {
    fun insertRcBook(rcBook: RcBook): Int
    fun getRcBook(id: Int): RcBook
}