package datalayer.dao

import modules.License

interface LicenseDao {
    fun insertLicense(license: License): Int
    fun getLicense(id: Int): License
}