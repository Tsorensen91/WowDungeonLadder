package com.co5225.j41564

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_layout.*
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity(), SearchFragment.SearchFragmentListener {

    private lateinit var listFragment: DungeonListFragment
    var searchParameters : Array<String> = arrayOf("All", "All", "All", "All")
    var preferenceChangedListener = SharedPreferences.OnSharedPreferenceChangeListener{sharedPreferences, key -> updateTheme()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listFragment = listFr as DungeonListFragment
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"))
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(preferenceChangedListener)
        loadList()
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

        if (item?.itemId == R.id.action_settings) {
            openSettings()
        } else {
            openSearch()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putStringArray("searchParameters", searchParameters)
        saveList()
    }

    override fun onPause() {
        super.onPause()
        saveList()
    }

    private fun saveList() {
        val fileOutPutStream = openFileOutput("searchParameters.dat", Context.MODE_PRIVATE)
        val objectOutputStream = ObjectOutputStream(fileOutPutStream)
        objectOutputStream.writeObject(searchParameters)
        objectOutputStream.close()
        fileOutPutStream.close()
    }

    private fun loadList() {
        try{
            val fileInputStream = openFileInput("searchParameters.dat")
            val objectInputStream = ObjectInputStream(fileInputStream)

            @Suppress("UNCHECKED_CAST")
            val loadedSearchParameters = objectInputStream.readObject() as? Array<String>
            if (loadedSearchParameters != null) searchParameters = loadedSearchParameters
            objectInputStream.close()
            fileInputStream.close()
        } catch (e: java.io.FileNotFoundException) {
            Toast.makeText(this, "No previous search parameters found",Toast.LENGTH_LONG).show()
        }
    }

    private fun updateTheme() {
        val preference = PreferenceManager.getDefaultSharedPreferences(this)
        val redTheme = preference.getBoolean("theme_changer",false)
        if (redTheme) {
            toolbar.setBackgroundColor(Color.parseColor("#8C1616"))
            listFragment.adapter.changeToRedTheme()
        } else {
            toolbar.setBackgroundColor(Color.parseColor("#144587"))
            listFragment.adapter.changeToBlueTheme()
        }
    }

    private fun openSettings() {
        val intent = Intent(this,SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun openSearch() {
        val intent = Intent(this,NewSearchActivity::class.java)
        startActivityForResult(intent,0)
    }

    fun addRunFromList (list : List<DungeonRun>) {
        runOnUiThread {
            updateTheme()
            for (i in list.indices) {
                listFragment.adapter.addRun(list[i])
            }

        }
    }

    fun getDungeonRuns(searchParameters: Array<String>) {
        fetchData(urlCompiler(searchParameters))
    }

    @SuppressLint("SetTextI18n")
    fun urlCompiler(searchParameters : Array<String>): String{
        val baseURl = "https://raider.io/api/v1/mythic-plus/runs?season=season-bfa-2"
        var regionURL = ""
        var dungeonURL = ""
        var affixURL = ""
        val regionBar = findViewById<TextView>(R.id.regionbar)
        when (searchParameters[0]) {
            "All" -> regionURL = "&region=world"
            "EU" -> regionURL = "&region=eu"
            "US" -> regionURL = "&region=us"
            "TW" -> regionURL = "&region=tw"
            "KR" -> regionURL = "&region=kr"
        }
        when (searchParameters[0]) {
            "All" -> regionbar?.text = "Region: World"
            "EU" -> regionBar.text = "Region: EU"
            "US" -> regionBar.text = "Region: US"
            "TW" -> regionBar.text = "Region: TW"
            "KR" -> regionBar.text = "Region: KR"
        }
        when (searchParameters[1]) {
            "All" -> dungeonURL = ""
            "AtalDazar" -> dungeonURL = "&dungeon=ataldazar"
            "Freehold" -> dungeonURL = "&dungeon=freehold"
            "Kings Rest" -> dungeonURL = "&dungeon=kings-rest"
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
                    runOnUiThread {
                        Toast.makeText(this, "The server has returned an error: $responseCode", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: IOException) {
                runOnUiThread {
                    Toast.makeText(this, "An error occurred retrieving data", Toast.LENGTH_LONG).show()
                }
            }
        }
        thread.start()
    }


}
