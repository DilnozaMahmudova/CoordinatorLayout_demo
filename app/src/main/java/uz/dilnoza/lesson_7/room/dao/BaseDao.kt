package uz.dilnoza.lesson_7.room.dao

import androidx.room.*


interface BaseDao<T> {
    @Update
    fun update(data:T):Int

    @Delete
    fun delete(data: T):Int

    @Delete
    fun deleteAll(data:List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: T):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(data:List<T>):List<Long>
}