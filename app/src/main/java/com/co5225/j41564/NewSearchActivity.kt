package com.co5225.j41564

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_new_search.*

class NewSearchActivity : AppCompatActivity() {

    lateinit var adapter: ArrayAdapter<CharSequence>
    var list = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_search)
        val regionSpinner : Spinner = findViewById(R.id.regionSpinner)
        val dungeonSpinner : Spinner = findViewById(R.id.dungeonSpinner)
        val classSpinner : Spinner = findViewById(R.id.classSpinner)
        val affixSpinner : Spinner = findViewById(R.id.affixSpinner)
        adapter = ArrayAdapter.createFromResource(this,R.array.region_array,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        regionSpinner.adapter = adapter
        regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
            }
        }
        adapter = ArrayAdapter.createFromResource(this,R.array.dungeon_array,android.R.layout.simple_spinner_item)
        dungeonSpinner.adapter = adapter
        dungeonSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
            }
        }

        adapter = ArrayAdapter.createFromResource(this,R.array.class_array,android.R.layout.simple_spinner_item)
        classSpinner.adapter = adapter
        classSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
            }
        }

        adapter = ArrayAdapter.createFromResource(this,R.array.affix_array,android.R.layout.simple_spinner_item)
        affixSpinner.adapter = adapter
        affixSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
            }
        }
    }




}
