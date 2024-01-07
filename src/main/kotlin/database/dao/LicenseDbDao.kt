package database.dao

import models.License

interface LicenseDbDao {
    fun insertLicense(license: License): Int
    fun getLicense(id: Int): License
}