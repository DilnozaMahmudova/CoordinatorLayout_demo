package uz.dilnoza.lesson_7.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.dilnoza.lesson_7.room.dao.CountryDao
import uz.dilnoza.lesson_7.room.entity.CountryData


@Database(entities = [CountryData::class],version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun countryDao():CountryDao
    companion object{
        @Volatile
        private var INSTANS:AppDatabase?=null
        fun getDatabase(context: Context):AppDatabase{
            val tempInstance= INSTANS
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance=Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"app_database").build()
                INSTANS=instance
                return instance
            }
        }
    }
}