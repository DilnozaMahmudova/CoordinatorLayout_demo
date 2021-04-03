package uz.dilnoza.lesson_7.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_country.view.*
import uz.dilnoza.lesson_7.R
import uz.dilnoza.lesson_7.room.entity.CountryData
import uz.dilnoza.lesson_7.utils.types.SingleBlock



class CountryDialog(context: Context, actionName:String) : AlertDialog(context) {
    private val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_country, null, false)
    private var listener: SingleBlock<CountryData>? = null
    private var countryDatas: CountryData? = null

    init {
        setView(contentView)
        setButton(BUTTON_POSITIVE, actionName) { _, _ ->
            val data = countryDatas ?: CountryData(0,"","","")
            data.name = contentView.country.text.toString()
            data.capital=contentView.capital.text.toString()
            data.area=contentView.area.text.toString()
            listener?.invoke(data)
        }
        setButton(BUTTON_NEGATIVE, "Cancel") { _, _ -> }
    }

    fun setCountryData(countryData: CountryData) = with(contentView) {
        this@CountryDialog.countryDatas = countryData
        country.setText(countryData.name)
        capital.setText(countryData.capital)
        area.setText(countryData.area)
    }


    fun setOnClickListener(block: SingleBlock<CountryData>) {
        listener = block
    }
}