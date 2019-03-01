package com.co5225.j41564

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity(), SearchFragment.SearchFragmentListener {

    private lateinit var listFragment: DungeonListFragment
    var searchParameters : Array<String> = arrayOf("All", "All", "All", "All")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listFragment = listFr as DungeonListFragment
        setSupportActionBar(toolbar)
        toolbar.setBackgroundColor(Color.parseColor("#144587"))
        if (savedInstanceState!= null) {
            searchParameters = savedInstanceState.getStringArray("searchParameters")!!
        }

        getDungeonRuns(searchParameters)

    }

    override fun onSearch(searchParameter: Array<String>) {
        val searchButton = findViewById<Button>(R.id.searchButton)
        searchButton.setOnClickListener{
            listFragment.adapter.removeRuns()
            for (i in searchParameter.indices) searchParameters[i] = searchParameter[i]
            getDungeonRuns(searchParameters)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            @Suppress("UNCHECKED_CAST")
            val newSearch = data?.getSerializableExtra("search") as Array<String>
            for (i in newSearch.indices) searchParameters[i] = newSearch[i]
            listFragment.adapter.removeRuns()
            getDungeonRuns(searchParameters)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            menu!!.findItem(R.id.menu_search).isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        newSearch()
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putStringArray("searchParameters", searchParameters)
    }

    private fun newSearch() {
        val intent = Intent(this,NewSearchActivity::class.java)
        startActivityForResult(intent,0)
    }

    fun addRunFromList (list : List<DungeonRun>) {
        runOnUiThread {
            for (i in list.indices) {
                listFragment.adapter.addRun(list[i])
            }
        }
    }

    fun getDungeonRuns(searchParameters: Array<String>) {
        fetchData(urlCompiler(searchParameters))
    }

    fun urlCompiler(searchParameters : Array<String>): String{
        val baseURl = "https://raider.io/api/v1/mythic-plus/runs?season=season-bfa-2"
        var regionURL = ""
        var dungeonURL = ""
        var affixURL = ""
        when (searchParameters[0]) {
            "All" -> regionURL = "&region=world"
            "EU" -> regionURL = "&region=eu"
            "US" -> regionURL = "&region=us"
            "TW" -> regionURL = "&region=tw"
            "KR" -> regionURL = "&region=kr"
        }
        when (searchParameters[1]) {
            "All" -> dungeonURL = ""
            "AtalDazar" -> dungeonURL = "&dungeon=ataldazar"
            "Freehold" -> dungeonURL = "&dungeon=freehold"
            "Kings rest" -> dungeonURL = "&dungeon=kings-rest"
            "Shrine of the Storm" -> dungeonURL = "&dungeon=shrine-of-the-storm"
            "Siege of Boralus" -> dungeonURL = "&dungeon=siege-of-boralus"
            "Temple of Sethraliss" -> dungeonURL = "&dungeon=temple-of-sethraliss"
            "The Motherlode!!" -> dungeonURL = "&dungeon=the-motherlode"
            "The Underrot" -> dungeonURL = "&dungeon=the-underrot"
            "Tol Dagor" -> dungeonURL = "&dungeon=tol-dagor"
            "Waycrest Manor" -> dungeonURL = "&dungeon=waycrest-manor"
        }
        when (searchParameters[2]) {
            "All" -> affixURL = ""
            "Tyrannical" -> affixURL = "&affixes=tyrannical"
            "Fortified" -> affixURL = "&affixes=fortified"
        }
        return baseURl+regionURL+dungeonURL+affixURL
    }

    fun fetchData (urlString: String){
        val thread = Thread{
            try {
                val url = URL(urlString)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.requestMethod = "GET"
                connection.connect()
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    val text = if (scanner.hasNext()) scanner.next() else ""
                    val jsonPacket = JSONUnpacker(text)
                    val list = jsonPacket.getDungeonList()
                    addRunFromList(list)

                } else {
                    Toast.makeText(this, "the server has returned an error: $responseCode",Toast.LENGTH_LONG).show()
                }
            } catch (e: IOException) {
                Toast.makeText(this, "an error occurred retrieving data",Toast.LENGTH_LONG).show()
            }
        }
        thread.start()
    }


}
