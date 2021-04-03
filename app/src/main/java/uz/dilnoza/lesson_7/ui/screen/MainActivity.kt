package uz.dilnoza.lesson_7.ui.screen

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import uz.dilnoza.lesson_7.R
import uz.dilnoza.lesson_7.app.App
import uz.dilnoza.lesson_7.room.AppDatabase
import uz.dilnoza.lesson_7.room.entity.CountryData
import uz.dilnoza.lesson_7.ui.adapter.CountryAdapter
import uz.dilnoza.lesson_7.ui.dialog.CountryDialog
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private val adapter = CountryAdapter()
    private val database = AppDatabase.getDatabase(App.instance)
    private val countryDao = database.countryDao()
    private val executor = Executors.newSingleThreadExecutor()
    private var a = true
    private lateinit var sharedPref:SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        list.adapter = adapter
        title = "About Countries"
        list.layoutManager = LinearLayoutManager(this)
        sharedPref=this.getSharedPreferences("LocalStorage",Context.MODE_PRIVATE)
        editor=sharedPref.edit()
        loadData()
        editData()
        deleteData()
        floatAc.setOnClickListener {
            appBar.setExpanded(false)
            list.smoothScrollToPosition(adapter.itemCount)
        }
        shuffle.setOnClickListener {
            Snackbar.make(coordinator, "The image is changed", Snackbar.LENGTH_INDEFINITE)
                .setAction("Change") {
                    if (a) {
                        imageTopPanel.setImageResource(R.drawable.top1)
                        a = false
                    } else {
                        imageTopPanel.setImageResource(R.drawable.topp)
                        a = true
                    }
                }.show()
        }


    }

  private  val ls = listOf(
        CountryData(6, "Uzbekistan", "Tashkent", "448.9 thousand square km")
        , CountryData(1, "USA", "Washington", "9.8 million square km"),
        CountryData(2, "The UK", "London", "240 million square km "),
        CountryData(3, "China", "Beijing", "9.6 million square km"),
        CountryData(4, "Russia", "Moscow", "17.1252 million square km"),
        CountryData(5, "Germany", "Berlin", "357 022 square km")
    )

    private fun loadData() {
        if (sharedPref.getBoolean("LIST",true)) {
            executor.execute {
                countryDao.insertAll(ls)
            }
                editor.putBoolean("LIST",false)
        }

        executor.execute {
            var list = countryDao.getAll()
            runOnUiThread { adapter.submitList(list) }
        }


    }

    private fun editData() {
        adapter.setOnItemEditListener { data ->
            val dialog = CountryDialog(this, "Edit")
            dialog.setCountryData(data)
            dialog.setOnClickListener { countryData ->
                executor.execute { countryDao.update(data) }
                val ls = adapter.currentList.toMutableList()
                val index = ls.indexOf(data )
                ls[index] = countryData
                adapter.submitList(ls)
                adapter.notifyItemChanged(index)
            }
            dialog.show()

        }

    }

   private fun deleteData() {
        adapter.setOnItemDeleteListener {
            val ls = adapter.currentList.toMutableList()
            ls.remove(it)
            executor.execute {
                countryDao.delete(it)
                runOnUiThread { adapter.submitList(ls) }
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuAdd -> {
                val dialog = CountryDialog(this, "Add")
                dialog.setOnClickListener {
                    executor.execute {
                        val id = countryDao.insert(it)

                        runOnUiThread {
                            val ls = adapter.currentList.toMutableList()
                            ls.add(it)
                            adapter.submitList(ls)
                            appBar.setExpanded(false)
                            list.smoothScrollToPosition(ls.size)
                        }
                    }
                }
                dialog.show()
            }
            android.R.id.home -> finish()

        }
        return true
    }

}