package com.co5225.j41564


import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class NewSearchActivity : AppCompatActivity(), SearchFragment.SearchFragmentListener {
    override fun onSearch(searchParameter: Array<String>) {
        val searchButton = findViewById<Button>(R.id.searchButton)
        searchButton.setOnClickListener{
            val intent = Intent()
            intent.putExtra("search", searchParameter)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_search)
    }







}
