package uz.dilnoza.lesson_7.room.dao

import androidx.room.Dao
import androidx.room.Query
import uz.dilnoza.lesson_7.room.entity.CountryData
@Dao
interface CountryDao:BaseDao<CountryData> {
    @Query("Select * From CountryData")
    fun getAll():List<CountryData>
}