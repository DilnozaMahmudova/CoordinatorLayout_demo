package uz.dilnoza.lesson_7.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*
import uz.dilnoza.lesson_7.R
import uz.dilnoza.lesson_7.room.entity.CountryData
import uz.dilnoza.lesson_7.utils.extension.bindItem
import uz.dilnoza.lesson_7.utils.extension.inflate
import uz.dilnoza.lesson_7.utils.types.SingleBlock


class CountryAdapter:androidx.recyclerview.widget.ListAdapter<CountryData, CountryAdapter.ViewHolder>(CountryData.ITEM_CALLBACK){
    private var listenerEdit: SingleBlock<CountryData>? = null
    private var listenerDelete: SingleBlock<CountryData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=ViewHolder(parent.inflate(R.layout.item))

    override fun onBindViewHolder(holder: ViewHolder, position: Int)=holder.bind()
    fun setOnItemEditListener(block: SingleBlock<CountryData>) {
        listenerEdit = block
    }

    fun setOnItemDeleteListener(block: SingleBlock<CountryData>) {
        listenerDelete = block
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init{
            itemView.apply {
                menu_more.setOnClickListener {
                    val menu = PopupMenu(context,it)
                    menu.inflate(R.menu.menu_more)
                    menu.setOnMenuItemClickListener {
                        when(it.itemId){
                            R.id.menuDelete -> listenerDelete?.invoke(getItem(adapterPosition))
                            R.id.menuEdit -> listenerEdit?.invoke(getItem(adapterPosition))
                        }
                        true
                    }
                    menu.show()
                }
            }
        }
        fun bind() = bindItem {
            val d = getItem(adapterPosition)
            country.text =d.name
            capital.text = d.capital
            area.text=d.area
        }
    }
}
