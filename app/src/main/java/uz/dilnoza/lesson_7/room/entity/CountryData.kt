package uz.dilnoza.lesson_7.room.entity

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
data class CountryData(
    @PrimaryKey(autoGenerate = true)
    var id:Long=0,
    var name:String,
    var capital:String,
    var area:String
){
    companion object{
        val ITEM_CALLBACK=object :DiffUtil.ItemCallback<CountryData>(){
            override fun areItemsTheSame(oldItem: CountryData, newItem: CountryData)=oldItem.id==newItem.id

            override fun areContentsTheSame(oldItem: CountryData, newItem: CountryData)=oldItem.name==newItem.name

        }
    }
}