package com.co5225.j41564


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner


class SearchFragment : Fragment() {

    private var searchParameterListener : SearchFragmentListener? = null
    private lateinit var adapter: ArrayAdapter<CharSequence>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //creates spinners and saves selected items in string list and passes through on button click.
        val searchParameterArray : Array<String> = arrayOf("All", "All", "All")
        val regionSpinner : Spinner = activity!!.findViewById(R.id.regionSpinner)
        val dungeonSpinner : Spinner = activity!!.findViewById(R.id.dungeonSpinner)
        val affixSpinner : Spinner = activity!!.findViewById(R.id.affixSpinner)
        adapter = ArrayAdapter.createFromResource(activity!!,R.array.region_array,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        regionSpinner.adapter = adapter
        regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                searchParameterArray[0] = selectedItem
            }
        }
        adapter = ArrayAdapter.createFromResource(activity!!,R.array.dungeon_array,android.R.layout.simple_spinner_item)
        dungeonSpinner.adapter = adapter
        dungeonSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                searchParameterArray[1] = selectedItem
            }
        }

        adapter = ArrayAdapter.createFromResource(activity!!,R.array.affix_array,android.R.layout.simple_spinner_item)
        affixSpinner.adapter = adapter
        affixSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                searchParameterArray[2] = selectedItem
            }
        }
        searchParameterListener?.onSearch(searchParameterArray)

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SearchFragmentListener) {
            searchParameterListener = context
        }
    }

    interface SearchFragmentListener {
        fun onSearch(searchParameter: Array<String>)
    }




}
